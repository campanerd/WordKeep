package wordkeep.apiEnglish.word;

import java.util.List;

public record DadosListagemWord(Long id, String word, String translation, List<Long> deckIds) {

    public DadosListagemWord(Word word) {
        this(
            word.getId(),
            word.getWord(),
            word.getTranslation(),
            word.getDecks().stream().map(deck -> deck.getId()).toList()
        );
    }

}
