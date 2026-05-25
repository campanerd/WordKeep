import { api } from "../api/api.js";
import { app, escapeHtml, spinner, renderError } from "../utils/dom.js";
import { toast } from "../components/toast.js";


export async function renderStudy(ctx) {
    const { state, navigate } = ctx;
    spinner();
    let words;
    try {
        words = await api.listarPalavrasDoDeck(state.deckId);
    } catch (e) {
        return renderError(e.message, () => renderStudy(ctx));
    }

    const voltarAoDeck = () => navigate("deck", { deckId: state.deckId, deckName: state.deckName });

    if (!words.length) {
        app.innerHTML = `
            <button class="back-btn" id="back">&larr; Voltar</button>
            <div class="empty">
                <div class="emoji">&#127919;</div>
                <h3>Nada para estudar</h3>
                <p>Adicione palavras ao deck primeiro.</p>
            </div>`;
        document.getElementById("back").onclick = voltarAoDeck;
        return;
    }

    const ordem = words.map((_, i) => i);
    let pos = 0;

    app.innerHTML = `
        <button class="back-btn" id="back">&larr; Voltar ao deck</button>
        <div class="page-head">
            <div>
                <h1>Estudar: ${escapeHtml(state.deckName)}</h1>
                <div class="subtitle">Clique no card para virar</div>
            </div>
            <button class="btn btn-ghost" id="shuffle">&#128256; Embaralhar</button>
        </div>
        <div class="study">
            <div class="flashcard" id="card">
                <div class="flashcard-inner">
                    <div class="flashcard-face front">
                        <span class="hint">Inglês</span>
                        <span class="word" id="front-word"></span>
                    </div>
                    <div class="flashcard-face back">
                        <span class="hint">Tradução</span>
                        <span class="word" id="back-word"></span>
                    </div>
                </div>
            </div>
            <div class="study-controls">
                <button class="round-btn" id="prev" title="Anterior">&larr;</button>
                <span class="study-progress" id="progress"></span>
                <button class="round-btn" id="next" title="Próxima">&rarr;</button>
            </div>
        </div>`;

    const card = document.getElementById("card");
    const frontWord = document.getElementById("front-word");
    const backWord = document.getElementById("back-word");
    const progress = document.getElementById("progress");
    const btnPrev = document.getElementById("prev");
    const btnNext = document.getElementById("next");

    function mostrar() {
        const w = words[ordem[pos]];
        card.classList.remove("flipped");
        frontWord.textContent = w.word;
        backWord.textContent = w.translation || "(sem tradução)";
        progress.textContent = `${pos + 1} / ${words.length}`;
        btnPrev.disabled = pos === 0;
        btnNext.disabled = pos === words.length - 1;
    }

    card.onclick = () => card.classList.toggle("flipped");
    btnPrev.onclick = () => { if (pos > 0) { pos--; mostrar(); } };
    btnNext.onclick = () => { if (pos < words.length - 1) { pos++; mostrar(); } };
    document.getElementById("shuffle").onclick = () => {
        for (let i = ordem.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [ordem[i], ordem[j]] = [ordem[j], ordem[i]];
        }
        pos = 0;
        mostrar();
        toast("Cards embaralhados.", "info");
    };
    document.getElementById("back").onclick = voltarAoDeck;

    mostrar();
}
