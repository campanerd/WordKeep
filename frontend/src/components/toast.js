/* Notificações temporárias (toasts) */

export function toast(message, type = "info") {
    const container = document.getElementById("toast-container");
    const t = document.createElement("div");
    t.className = `toast ${type}`;
    t.textContent = message;
    container.appendChild(t);
    setTimeout(() => {
        t.style.opacity = "0";
        t.style.transition = "opacity 0.3s";
        setTimeout(() => t.remove(), 300);
    }, 3200);
}
