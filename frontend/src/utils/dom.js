export const app = document.getElementById("app");


export function escapeHtml(str) {
    return String(str ?? "").replace(/[&<>"']/g, (c) => ({
        "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;",
    }[c]));
}

export function spinner() {
    app.innerHTML = `<div class="spinner"></div>`;
}

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
