import { renderDecks } from "./views/decksView.js";
import { renderDeckView } from "./views/deckView.js";
import { renderStudy } from "./views/studyView.js";

/* ============================================================
   Roteamento / estado global da SPA
   ============================================================ */

const state = { view: "decks", deckId: null, deckName: "" };

function navigate(view, params = {}) {
    Object.assign(state, { view, ...params });
    render();
}

function render() {
    const ctx = { state, navigate };
    if (state.view === "deck") return renderDeckView(ctx);
    if (state.view === "study") return renderStudy(ctx);
    return renderDecks(ctx);
}

document.querySelector(".brand").onclick = () => navigate("decks");

render();
