import { API_URL } from "./config.js";

let todosDecks = [];
let indiceAtual = 1;

const btnAnterior = document.getElementById("btnAnterior");
const btnProximo = document.getElementById("btnProximo");

function atualizarBotoes() {
    btnAnterior.disabled = todosDecks.length <= 1;
    btnProximo.disabled = todosDecks.length <= 1;
}

async function carregarDecks() {
    const response = await fetch(`${API_URL}/decks`);
    todosDecks = await response.json();
    if (todosDecks.length > 0) {
        indiceAtual = Math.min(1, todosDecks.length - 1);
    }
    atualizarBotoes();
    await renderizarDecks();
}

async function renderizarDecks() {
    const deckList = document.getElementById("deckList");
    deckList.classList.add("animando");
    await new Promise(r => setTimeout(r, 250));

    deckList.innerHTML = "";

    const anterior = (indiceAtual - 1 + todosDecks.length) % todosDecks.length;
    const proximo = (indiceAtual + 1) % todosDecks.length;
    const indices = [anterior, indiceAtual, proximo];

    for (const i of indices) {
        const deck = todosDecks[i];
        const wordsResponse = await fetch(`${API_URL}/decks/${deck.id}/words`);
        const words = await wordsResponse.json();

        let palavrasHTML = "";
        words.forEach(word => {
            palavrasHTML += `<li class="palavra">${word.word} - ${word.translation}</li>`;
        });

        const ativo = i === indiceAtual ? "ativo" : "";

        deckList.innerHTML += `
            <div class="deck-card ${ativo}">
                <h2>${deck.name}</h2>
                <ul class="lista-palavras">${palavrasHTML}</ul>
            </div>
        `;
    }

    void deckList.offsetWidth;
    deckList.classList.remove("animando");
}

btnAnterior.addEventListener("click", async () => {
    indiceAtual = (indiceAtual - 1 + todosDecks.length) % todosDecks.length;
    await renderizarDecks();
});

btnProximo.addEventListener("click", async () => {
    indiceAtual = (indiceAtual + 1) % todosDecks.length;
    await renderizarDecks();
});

carregarDecks();
