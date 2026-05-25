/* Utilitários de DOM compartilhados entre as views */

export const app = document.getElementById("app");

/** Escapa conteúdo do usuário antes de injetar via innerHTML. */
export function escapeHtml(str) {
    return String(str ?? "").replace(/[&<>"']/g, (c) => ({
        "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;",
    }[c]));
}

/** Mostra o spinner de carregamento na área principal. */
export function spinner() {
    app.innerHTML = `<div class="spinner"></div>`;
}

/** Tela de erro genérica com botão de "tentar de novo". */
export function renderError(message, retry) {
    app.innerHTML = `
        <div class="empty">
            <div class="emoji">&#9888;&#65039;</div>
            <h3>Algo deu errado</h3>
            <p>${escapeHtml(message)}</p>
            <button class="btn btn-primary" id="retry" style="margin-top:16px;">Tentar de novo</button>
        </div>`;
    document.getElementById("retry").onclick = retry;
}
