package wordkeep.apiEnglish.word;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoWord(Long id,
                                    String word,
                                    String translation,
                                    String sourceLanguage,
                                    String targetLanguage,
                                    LocalDateTime createdAt,
                                    List<DeckInfo> decks) {

    public record DeckInfo(Long id, String name) {}

    public DadosDetalhamentoWord(Word word) {
        this(
                word.getId(),
                word.getWord(),
                word.getTranslation(),
                word.getSourceLanguage(),
                word.getTargetLanguage(),
                word.getCreatedAt(),
                word.getDecks().stream().map(d -> new DeckInfo(d.getId(), d.getName())).toList()
        );
    }
}