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

async function carregarDecks(){

    const response = await fetch(`${API_URL}/decks`);

    const decks = await response.json();

    const deckList = document.getElementById("deckList");

    deckList.innerHTML = "";

    decks.forEach(deck => {

        deckList.innerHTML += `
            <li>
                ${deck.name}
            </li>
        `;

    });

}
carregarDecks();