import { API_URL } from "./config.js";

const button = document.getElementById("sendButton");

button.addEventListener("click", salvarPalavra);

async function carregarDecks() {

    const response = await fetch(`${API_URL}/decks`);

    const decks = await response.json();

    const deckList = document.getElementById("deckList");

    deckList.innerHTML = "";

    decks.forEach(deck => {

        const option = document.createElement("option");

        option.value = deck.id;
        option.textContent = deck.name;

        deckList.appendChild(option);

    });

}

async function carregarPalavras() {

    const lista = document.getElementById("lista");

    if (!lista) return;

    const response = await fetch(`${API_URL}/words`);

    const words = await response.json();

    lista.innerHTML = "";

    words.forEach(word => {

        lista.innerHTML += `
            <li>
                ${word.word} - ${word.translation}

                <div class="edit-delete">

                    <button onclick="editarPalavra(${word.id})">
                        Editar
                    </button>

                    <button onclick="excluirPalavra(${word.id})">
                        Excluir
                    </button>

                </div>

            </li>
        `;

    });

}

async function salvarPalavra() {

    const word = document.getElementById("word").value;

    const translation = document.getElementById("translation").value;

    const deckId = document.getElementById("deckSelect").value;

    if (!deckId) {

        alert("Selecione um deck.");

        return;

    }

    const payload = {

        word: word,
        translation: translation,
        sourceLanguage: "en",
        targetLanguage: "pt",
        deckId: deckId

    };

    const response = await fetch(`${API_URL}/words`, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(payload)

    });

    if (response.ok) {

        alert("Palavra salva com sucesso!");

        carregarPalavras();

    }

}

carregarDecks();
carregarPalavras();