 window.carregarDecks = async () => {

    const response = await fetch("http://192.168.1.42:8080/decks");

    const decks = await response.json();

    const deckList = document.getElementById("deckList");

    deckList.innerHTML = "";

    for (const deck of decks) {

        const wordsResponse = await fetch(
            `http://192.168.1.42:8080/decks/${deck.id}/words`
        );

        const words = await wordsResponse.json();

        let palavrasHTML = "";

        words.forEach(word => {
            palavrasHTML += `
                <li class="palavra">
                    ${word.word} -
                    ${word.translation}
                </li>
            `;
        });

        deckList.innerHTML += `
            <div class="deck-card">

                <h2>${deck.name}</h2>

                <ul class="lista-palavras">
                    ${palavrasHTML}
                </ul>

            </div>
        `;
    }
}

carregarDecks();