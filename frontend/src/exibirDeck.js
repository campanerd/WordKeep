import { API_URL } from "./config.js";

let todosDecks = [];
let indiceAtual = 1;

const cacheWords = {};

const btnAnterior = document.getElementById("btnAnterior");
const btnProximo = document.getElementById("btnProximo");
const deckList = document.getElementById("deckList");

function atualizarBotoes() {

    const desabilitar = todosDecks.length <= 1;

    btnAnterior.disabled = desabilitar;
    btnProximo.disabled = desabilitar;
}

async function buscarWords(deckId) {

    // usa cache
    if (cacheWords[deckId]) {
        return cacheWords[deckId];
    }

    try {

        const response =
            await fetch(`${API_URL}/decks/${deckId}/words`);

        if (!response.ok) {
            throw new Error("Erro ao buscar palavras");
        }

        const words = await response.json();

        cacheWords[deckId] = words;

        return words;

    } catch (error) {

        console.error(error);

        return [];
    }
}

async function carregarDecks() {

    try {

        const response =
            await fetch(`${API_URL}/decks`);

        if (!response.ok) {
            throw new Error("Erro ao carregar decks");
        }

        todosDecks = await response.json();

        if (todosDecks.length > 0) {
            indiceAtual = Math.min(1, todosDecks.length - 1);
        }

        atualizarBotoes();

        await renderizarDecks();

    } catch (error) {

        console.error(error);
    }
}

async function renderizarDecks() {

    if (todosDecks.length === 0) return;

    deckList.style.pointerEvents = "none";

    const anterior =
        (indiceAtual - 1 + todosDecks.length) % todosDecks.length;

    const proximo =
        (indiceAtual + 1) % todosDecks.length;

    const indices = [anterior, indiceAtual, proximo];

    // busca tudo em paralelo
    const decksRenderizados = await Promise.all(

        indices.map(async (i) => {

            const deck = todosDecks[i];

            const words = await buscarWords(deck.id);

            return {
                deck,
                words,
                ativo: i === indiceAtual
            };
        })
    );

    // cria HTML sem apagar antes
    const htmlNovo = decksRenderizados.map(({ deck, words, ativo }) => {

        const palavrasHTML = words.map(word => `
            <li class="palavra">
                ${word.word} - ${word.translation}
            </li>
        `).join("");

        return `
            <div class="deck-card ${ativo ? "ativo" : ""}">

                <h2>${deck.name}</h2>

                <ul class="lista-palavras">
                    ${palavrasHTML}
                </ul>

            </div>
        `;
    }).join("");

    // troca DOM suavemente
    requestAnimationFrame(() => {

        deckList.innerHTML = htmlNovo;

        deckList.style.pointerEvents = "auto";
    });
}

btnAnterior.addEventListener("click", async () => {

    if (todosDecks.length <= 1) return;

    indiceAtual =
        (indiceAtual - 1 + todosDecks.length) % todosDecks.length;

    await renderizarDecks();
});

btnProximo.addEventListener("click", async () => {

    if (todosDecks.length <= 1) return;

    indiceAtual =
        (indiceAtual + 1) % todosDecks.length;

    await renderizarDecks();
});

carregarDecks();