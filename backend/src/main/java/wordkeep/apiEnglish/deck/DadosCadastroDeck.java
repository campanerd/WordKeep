package wordkeep.apiEnglish.deck;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroDeck(
        @NotBlank
        String name) {
}
