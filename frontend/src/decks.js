const button = document.getElementById("sendButton");

button.addEventListener("click", async () => {

    const name = document.getElementById("deck").value;

    await fetch("http://192.168.1.42:8080/decks", {

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

    const response = await fetch("http://192.168.1.42:8080/decks");

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

    lista.innerHTML = "";

    words.forEach(word => {

        lista.innerHTML += `
            <li>
                ${word.word} - ${word.translation}
            </li>
        `;

    });

carregarDecks();