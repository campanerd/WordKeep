package wordkeep.apiEnglish.word;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroWord(

        @NotBlank
        String word,

        String translation,

        String sourceLanguage,

        String targetLanguage,

        Long deckId) {
}
