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

async function carregarDecks() {
    const response = await fetch("http://192.168.1.42:8080/decks");
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

async function deletarDeck(id){
    if (confirm("Tem certeza que deseja excluir este deck?")) {
        await fetch(`http://192.168.42.1:8080/decks/${id}`, {
            method: "DELETE"
        });
        carregarDecks();
    }
    
}
async function editarDeck(id){
    const novoNome = prompt("Digite o novo nome do deck:");
    if (novoNome) {
        await fetch(`http://192.168.42.1:8080/decks/${id}`, {
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
}
carregarDecks();