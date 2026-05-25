import { API_URL } from "../config.js";

/**
 * Wrapper central de requisições.
 * Trata erros (o backend retorna a mensagem em texto puro via TratadorDeErros),
 * respostas 204 (sem corpo) e respostas que são string pura (ex: /translate).
 */
async function request(path, options = {}) {
    let res;
    try {
        res = await fetch(`${API_URL}${path}`, {
            headers: { "Content-Type": "application/json" },
            ...options,
        });
    } catch {
        throw new Error("Não foi possível conectar ao servidor. O backend está rodando?");
    }

    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg || `Erro ${res.status}`);
    }

    if (res.status === 204) return null;

    const text = await res.text();
    if (!text) return null;
    try {
        return JSON.parse(text);
    } catch {
        return text; // string pura (ex: tradução)
    }
}

export const api = {
    // Decks
    listarDecks: () => request("/decks"),
    criarDeck: (name) =>
        request("/decks", { method: "POST", body: JSON.stringify({ name }) }),
    atualizarDeck: (id, name) =>
        request("/decks", { method: "PUT", body: JSON.stringify({ id, name }) }),
    excluirDeck: (id) => request(`/decks/${id}`, { method: "DELETE" }),
    listarPalavrasDoDeck: (id) => request(`/decks/${id}/words`),

    // Words
    listarWords: () => request("/words"),
    criarWord: (word, deckId) =>
        request("/words", { method: "POST", body: JSON.stringify({ word, deckId }) }),
    atualizarWord: (id, word) =>
        request("/words", { method: "PUT", body: JSON.stringify({ id, word }) }),
    excluirWord: (id) => request(`/words/${id}`, { method: "DELETE" }),
};
