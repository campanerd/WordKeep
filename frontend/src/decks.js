import { API_URL } from "./config.js";

const button = document.getElementById("sendButton");

button.addEventListener("click", async () => {

    const name = document.getElementById("deck").value;

    await fetch(`${API_URL}/decks`, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
           name: name
        })

    });

    carregarDecks();

});

async function carregarDecks() {
    const response = await fetch(`${API_URL}/decks`);
    const decks = await response.json();
    const deckList = document.getElementById("deckList");

    deckList.innerHTML = "";

    decks.forEach(deck => {
        deckList.innerHTML += `
            <li style="display: flex; justify-content: space-between; align-items: center;">
                <span>${deck.name}</span>
                <div class="acoes">
                    <button class="editdelete" style="background-color: #87CEEB ; margin: 10px;" onclick="editarDeck(${deck.id})">Editar</button>
                    <button class="editdelete" style="background-color: #CD5C5C;" onclick="deletarDeck(${deck.id})">Excluir</button>
                </div>
            </li>
        `;
    });
}
    window.editarPalavra = async (id) =>  {

    const novoNome = prompt("Novo nome:");

    if (!novoNome) return;

    await fetch(`${API_URL}/decks`, {

        method: "PUT",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            id: id,
            name: novoNome
        })

    });

    carregarDecks();
}

  window.deletarDeck = async (id) =>  {

    const confirmar = confirm("Deseja excluir esse deck?");

    if (!confirmar) return;

    await fetch(`${API_URL}/decks/${id}`, {

        method: "DELETE"

    });

    carregarDecks();

}


carregarDecks();