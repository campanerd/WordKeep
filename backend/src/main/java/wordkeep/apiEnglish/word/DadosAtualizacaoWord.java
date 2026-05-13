package wordkeep.apiEnglish.word;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoWord(

        @NotNull
        Long id,

        String word,

        String translation) {
}
