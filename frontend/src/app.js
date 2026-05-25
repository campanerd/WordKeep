import { renderDecks } from "./views/decksView.js";
import { renderDeckView } from "./views/deckView.js";
import { renderStudy } from "./views/studyView.js";

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
const savedTheme = localStorage.getItem("theme");

if (savedTheme === "dark") {
    document.body.classList.add("dark-theme");

    document.getElementById("theme-toggle").textContent = "☀️";
}
render();
const themeToggle = document.getElementById("theme-toggle");

themeToggle.onclick = () => {
    document.body.classList.toggle("dark-theme");

    const darkMode = document.body.classList.contains("dark-theme");

    themeToggle.textContent = darkMode ? "☀️" : "🌙";

    localStorage.setItem("theme", darkMode ? "dark" : "light");
};