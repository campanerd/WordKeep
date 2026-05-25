/* Modais reutilizáveis: confirmação e prompt de texto */

import { escapeHtml } from "../utils/dom.js";

const root = document.getElementById("modal-root");

/** Modal de confirmação. Retorna Promise<boolean>. */
export function confirmDialog({ title, message, confirmText = "Confirmar", danger = false }) {
    return new Promise((resolve) => {
        root.innerHTML = `
            <div class="modal-overlay">
                <div class="modal">
                    <h2>${escapeHtml(title)}</h2>
                    <p>${escapeHtml(message)}</p>
                    <div class="modal-actions">
                        <button class="btn btn-ghost" data-act="cancel">Cancelar</button>
                        <button class="btn ${danger ? "btn-danger" : "btn-primary"}" data-act="ok">${escapeHtml(confirmText)}</button>
                    </div>
                </div>
            </div>`;
        const close = (val) => { root.innerHTML = ""; resolve(val); };
        root.querySelector('[data-act="cancel"]').onclick = () => close(false);
        root.querySelector('[data-act="ok"]').onclick = () => close(true);
        root.querySelector(".modal-overlay").onclick = (e) => {
            if (e.target.classList.contains("modal-overlay")) close(false);
        };
    });
}

/** Modal com input de texto. Retorna Promise<string|null>. */
export function promptDialog({ title, label, value = "", placeholder = "", confirmText = "Salvar" }) {
    return new Promise((resolve) => {
        root.innerHTML = `
            <div class="modal-overlay">
                <div class="modal">
                    <h2>${escapeHtml(title)}</h2>
                    <label class="field-label">${escapeHtml(label)}</label>
                    <input class="input" id="prompt-input" value="${escapeHtml(value)}" placeholder="${escapeHtml(placeholder)}" />
                    <div class="modal-actions">
                        <button class="btn btn-ghost" data-act="cancel">Cancelar</button>
                        <button class="btn btn-primary" data-act="ok">${escapeHtml(confirmText)}</button>
                    </div>
                </div>
            </div>`;
        const input = root.querySelector("#prompt-input");
        input.focus();
        input.select();
        const close = (val) => { root.innerHTML = ""; resolve(val); };
        const submit = () => {
            const v = input.value.trim();
            if (v) close(v); else input.focus();
        };
        root.querySelector('[data-act="cancel"]').onclick = () => close(null);
        root.querySelector('[data-act="ok"]').onclick = submit;
        input.onkeydown = (e) => {
            if (e.key === "Enter") submit();
            if (e.key === "Escape") close(null);
        };
        root.querySelector(".modal-overlay").onclick = (e) => {
            if (e.target.classList.contains("modal-overlay")) close(null);
        };
    });
}
