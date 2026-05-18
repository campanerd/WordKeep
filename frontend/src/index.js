async function carregarDecks() {
    const response = await fetch("http://192.168.1.42:8080/decks");
    const decks = await response.json();
    const deckList = document.getElementById("deckList");

    deckList.innerHTML = "";

    decks.forEach(deck => {
        deckList.innerHTML += `
            <li style="display: flex; justify-content: space-between; align-items: center;">
                <span>${deck.name}</span>
            </li>
        `;
    });
}
carregarDecks();