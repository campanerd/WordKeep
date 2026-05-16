import { API_URL } from "./config.js";

const button = document.getElementById("sendButton");

button.addEventListener("click", async () => {

    const word = document.getElementById("word").value;
    const translation = document.getElementById("translation").value;

    await fetch(`${API_URL}/words`, {

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

    carregarPalavras();

});

async function carregarPalavras(){

    const response = await fetch(`${API_URL}/words`);

    const words = await response.json();

    const lista = document.getElementById("lista");

    lista.innerHTML = "";

    words.forEach(word => {

        lista.innerHTML += `
            <li>
                ${word.word} - ${word.translation}
            </li>
        `;

    });

}

carregarPalavras();