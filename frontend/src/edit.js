const button = document.getElementById("sendButton");

if (button) {

    button.addEventListener("click", async () => {

        const word = document.getElementById("word").value;
        const translation = document.getElementById("translation").value;

        await fetch("http://192.168.1.42:8080/words", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                word: word,
                translation: translation,
                sourceLanguage: "en",
                targetLanguage: "pt"
            })

        });

        
        document.getElementById("word").value = "";
        document.getElementById("translation").value = "";

        carregarPalavras();

    });

}



async function carregarPalavras() {

    const lista = document.getElementById("lista");

    if (!lista) return;

    const response = await fetch("http://192.168.1.42:8080/words");

    const words = await response.json();

    lista.innerHTML = "";

    words.forEach(word => {

        lista.innerHTML += `
            <li>
                ${word.word} - ${word.translation}

                <div class = "edit-delete">
                <button onclick="editarPalavra(${word.id})">
                    Editar
                </button>

                <button onclick="excluirPalavra(${word.id})">
                    Excluir
                </button>
            </li>
            </div>
        `;

    });

}


async function editarPalavra(id) {

    const novaPalavra = prompt("Nova palavra:");
    const novaTraducao = prompt("Nova tradução:");

    if (!novaPalavra || !novaTraducao) return;

    await fetch("http://192.168.1.42:8080/words", {

        method: "PUT",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            id: id,
            word: novaPalavra,
            translation: novaTraducao
        })

    });

    carregarPalavras();

}


async function excluirPalavra(id) {

    const confirmar = confirm("Deseja excluir essa palavra?");

    if (!confirmar) return;

    await fetch(`http://192.168.1.42:8080/words/${id}`, {

        method: "DELETE"

    });

    carregarPalavras();

}


carregarPalavras();