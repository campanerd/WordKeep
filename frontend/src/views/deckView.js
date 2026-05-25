import { api } from "../api/api.js";
import { app, escapeHtml, spinner, renderError } from "../utils/dom.js";
import { toast } from "../components/toast.js";
import { confirmDialog, promptDialog } from "../components/modal.js";

export async function renderDeckView(ctx) {
    const { state, navigate } = ctx;
    spinner();
    let words;
    try {
        words = await api.listarPalavrasDoDeck(state.deckId);
    } catch (e) {
        return renderError(e.message, () => renderDeckView(ctx));
    }

    const lista = words.length
        ? `<div class="word-list">${words.map((w) => `
            <div class="word-row" data-id="${w.id}">
                <span class="term">${escapeHtml(w.word)}</span>
                <span class="arrow">&rarr;</span>
                ${w.translation
                    ? `<span class="translation">${escapeHtml(w.translation)}</span>`
                    : `<span class="missing">sem tradução</span>`}
                <span class="spacer"></span>
                <button class="icon-btn" data-act="edit-word" data-id="${w.id}" title="Editar">&#9998;</button>
                <button class="icon-btn danger" data-act="del-word" data-id="${w.id}" title="Remover desta lista">&#128465;</button>
            </div>`).join("")}</div>`
        : `<div class="empty">
                <div class="emoji">&#10024;</div>
                <h3>Deck vazio</h3>
                <p>Adicione palavras em inglês acima — a tradução é automática.</p>
           </div>`;

    app.innerHTML = `
        <button class="back-btn" id="back">&larr; Voltar as listas</button>
        <div class="page-head">
            <div>
                <h1>${escapeHtml(state.deckName)}</h1>
                <div class="subtitle">${words.length} palavra${words.length === 1 ? "" : "s"}</div>
            </div>
            <div style="display:flex; gap:10px; flex-wrap:wrap;">
                <button class="btn btn-ghost" id="study" ${words.length ? "" : "disabled"}>Estudar</button>
                <button class="btn btn-ghost" id="rename">Renomear</button>
                <button class="btn btn-danger" id="del-deck">Excluir lista</button>
            </div>
        </div>

        <form class="word-add" id="add-form">
            <input class="input" id="word-input" placeholder="Palavra em inglês (ex: house)" autocomplete="off" />
            <button class="btn btn-primary" type="submit" id="add-btn">Adicionar</button>
        </form>

        ${lista}`;

    document.getElementById("back").onclick = () => navigate("decks");
    document.getElementById("rename").onclick = () => renomearDeck(ctx);
    document.getElementById("del-deck").onclick = () => excluirDeck(ctx);
    document.getElementById("study").onclick = () =>
        navigate("study", { deckId: state.deckId, deckName: state.deckName });

    document.getElementById("add-form").onsubmit = (e) => adicionarPalavra(e, ctx);

    app.querySelectorAll('[data-act="edit-word"]').forEach((b) => {
        b.onclick = () => editarPalavra(words.find((w) => w.id == b.dataset.id), ctx);
    });
    app.querySelectorAll('[data-act="del-word"]').forEach((b) => {
        b.onclick = () => excluirPalavra(words.find((w) => w.id == b.dataset.id), ctx);
    });
}

async function adicionarPalavra(e, ctx) {
    e.preventDefault();
    const input = document.getElementById("word-input");
    const btn = document.getElementById("add-btn");
    const word = input.value.trim();
    if (!word) return;

    btn.disabled = true;
    btn.textContent = "Traduzindo...";
    try {
        const nova = await api.criarWord(word, ctx.state.deckId);
        toast(nova.translation
            ? `"${nova.word}" → ${nova.translation}`
            : `"${nova.word}" adicionada (sem tradução).`, "success");
        renderDeckView(ctx);
    } catch (err) {
        toast(err.message, "error");
        btn.disabled = false;
        btn.textContent = "Adicionar";
    }
}

async function editarPalavra(w, ctx) {
    const novo = await promptDialog({ title: "Editar palavra", label: "Palavra em inglês", value: w.word });
    if (!novo || novo === w.word) return;
    try {
        await api.atualizarWord(w.id, novo);
        toast("Palavra atualizada.", "success");
        renderDeckView(ctx);
    } catch (e) { toast(e.message, "error"); }
}

async function excluirPalavra(w, ctx) {
    const ok = await confirmDialog({
        title: "Remover palavra",
        message: `Remover "${w.word}" desta lista? Se ela não estiver em nenhuma outra, será excluída de vez.`,
        confirmText: "Remover", danger: true,
    });
    if (!ok) return;
    try {
        await api.removerPalavraDoDeck(ctx.state.deckId, w.id);
        toast("Palavra removida da lista.", "success");
        renderDeckView(ctx);
    } catch (e) { toast(e.message, "error"); }
}

async function renomearDeck(ctx) {
    const nome = await promptDialog({ title: "Renomear deck", label: "Nome do deck", value: ctx.state.deckName });
    if (!nome) return;
    try {
        await api.atualizarDeck(ctx.state.deckId, nome);
        ctx.state.deckName = nome;
        toast("Deck renomeado.", "success");
        renderDeckView(ctx);
    } catch (e) { toast(e.message, "error"); }
}

async function excluirDeck(ctx) {
    const ok = await confirmDialog({
        title: "Excluir deck",
        message: `Excluir "${ctx.state.deckName}"? Todas as palavras dele também serão removidas.`,
        confirmText: "Excluir", danger: true,
    });
    if (!ok) return;
    try {
        await api.excluirDeck(ctx.state.deckId);
        toast("Deck excluído.", "success");
        ctx.navigate("decks");
    } catch (e) { toast(e.message, "error"); }
}
