const button = document.getElementById("sendButton");

button.addEventListener("click", async () => {

    const deck = document.getElementById("dexk").value;

    await fetch("http://localhost:8080/decks", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            name: name,
        })

    });

    carregarPalavras();

});
