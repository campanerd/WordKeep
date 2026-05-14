const button = document.getElementById("sendButton");

// =========================
// CRIAR DECK
// =========================

if (button) {

    button.addEventListener("click", async () => {

        const deck = document.getElementById("deck").value;

        // impede criar deck vazio
        if (!deck) return;

        await fetch("http://localhost:8080/decks", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                name: deck
            })

        });

        // limpa input
        document.getElementById("deck").value = "";

        carregarDecks();

    });

}

// =========================
// LISTAR DECKS
// =========================

async function carregarDecks() {

    const lista = document.getElementById("deckList");

    // evita erro em páginas sem lista
    if (!lista) return;

    const response = await fetch("http://localhost:8080/decks");

    const decks = await response.json();

    lista.innerHTML = "";

    decks.forEach(deck => {

        lista.innerHTML += `

            <li class="deck-item">

                <span class="deck-name">
                    ${deck.name}
                </span>

            </li>

        `;

    });

}

// =========================
// INICIAR
// =========================

carregarDecks();