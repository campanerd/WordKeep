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
    editarDeck = async function(id) {
    const novoNome = prompt("Digite o novo nome do deck:");
    
    
    if (!novoNome) return;

    try {
    
        const response = await fetch("http://192.168.1.42:8080/decks", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                id: id,      
                name: novoNome 
            })
        });

        if (response.ok) {
            carregarDecks(); 
        } else {
            const erroTxt = await response.text();
            alert("Erro ao editar: " + erroTxt);
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
        alert("Não foi possível conectar ao servidor.");
    }
};




    deletarDeck = async function(id) {
    const confirmar = confirm("Deseja excluir este deck?");
    if (!confirmar) return;

    try {
        const response = await fetch(`http://192.168.1.42:8080/decks/${id}`, {
            method: "DELETE"
        });

        if (response.ok) {
            carregarDecks(); 
        } else {
            alert("Erro ao excluir o deck.");
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
    }
};


carregarDecks();