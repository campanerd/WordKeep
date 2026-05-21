package wordkeep.apiEnglish.word;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroWord(

        @NotBlank
        String word,

        @NotNull
        Long deckId) {
}
