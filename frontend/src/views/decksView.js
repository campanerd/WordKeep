import { api } from "../api/api.js";
import { app, escapeHtml, spinner, renderError } from "../utils/dom.js";
import { toast } from "../components/toast.js";
import { confirmDialog, promptDialog } from "../components/modal.js";

export async function renderDecks({ navigate }) {
    spinner();
    let decks, words;
    try {
        [decks, words] = await Promise.all([api.listarDecks(), api.listarWords()]);
    } catch (e) {
        return renderError(e.message, () => renderDecks({ navigate }));
    }


    const contagem = {};
    for (const w of words) {
        for (const id of w.deckIds || []) contagem[id] = (contagem[id] || 0) + 1;
    }

    const grid = decks.length
        ? `<div class="deck-grid">${decks.map((d) => `
            <div class="deck-card" data-id="${d.id}">
                <div class="deck-name">${escapeHtml(d.name)}</div>
                <div class="deck-meta">
                    <span class="badge">${contagem[d.id] || 0} palavra${(contagem[d.id] || 0) === 1 ? "" : "s"}</span>
                </div>
                <div class="deck-actions">
                    <button class="icon-btn" data-act="edit" data-id="${d.id}" title="Renomear">&#9998;</button>
                    <button class="icon-btn danger" data-act="delete" data-id="${d.id}" title="Excluir">&#128465;</button>
                </div>
            </div>`).join("")}</div>`
        : `<div class="empty">
                <h3>Nenhum deck ainda</h3>
                <p>Crie seu primeiro deck para começar a estudar.</p>
           </div>`;

    app.innerHTML = `
        <div class="page-head">
            <div>
                <h1>Minhas listas</h1>
                <div class="subtitle">Organize seu vocabulário em listas temáticas</div>
            </div>
            <button class="btn btn-primary" id="nova-lista">+ Nova Lista</button>
        </div>
        ${grid}`;

    document.getElementById("nova-lista").onclick = () => novoDeck(navigate);

    app.querySelectorAll(".deck-card").forEach((card) => {
        card.onclick = (e) => {
            if (e.target.closest("[data-act]")) return; // clicou num botão de ação
            const deck = decks.find((d) => d.id == card.dataset.id);
            navigate("Lista", { deckId: deck.id, deckName: deck.name });
        };
    });

    app.querySelectorAll('[data-act="edit"]').forEach((b) => {
        b.onclick = async () => {
            const deck = decks.find((d) => d.id == b.dataset.id);
            const nome = await promptDialog({ title: "Renomear lista", label: "Nome da lista", value: deck.name });
            if (!nome) return;
            try {
                await api.atualizarDeck(deck.id, nome);
                toast("Lista renomeada.", "success");
                renderDecks({ navigate });
            } catch (e) { toast(e.message, "error"); }
        };
    });

    app.querySelectorAll('[data-act="delete"]').forEach((b) => {
        b.onclick = async () => {
            const deck = decks.find((d) => d.id == b.dataset.id);
            const ok = await confirmDialog({
                title: "Excluir lista",
                message: `Excluir "${deck.name}"? Todas as palavras dela também serão removidas.`,
                confirmText: "Excluir", danger: true,
            });
            if (!ok) return;
            try {
                await api.excluirDeck(deck.id);
                toast("Lista excluída.", "success");
                renderDecks({ navigate });
            } catch (e) { toast(e.message, "error"); }
        };
    });
}

async function novoDeck(navigate) {
    const nome = await promptDialog({
        title: "Nova lista", label: "Nome da lista",
        placeholder: "Ex: Verbos irregulares", confirmText: "Criar",
    });
    if (!nome) return;
    try {
        const deck = await api.criarDeck(nome);
        toast("Lista criada.", "success");
        navigate("Lista", { deckId: deck.id, deckName: deck.name });
    } catch (e) { toast(e.message, "error"); }
}
