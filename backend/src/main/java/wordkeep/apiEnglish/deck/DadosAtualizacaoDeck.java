package wordkeep.apiEnglish.deck;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoDeck(

        @NotNull
        Long id,

        String name) {
}
