package wordkeep.apiEnglish.deck;

import java.time.LocalDateTime;

public record DadosDetalhamentoDeck(Long id, String name, LocalDateTime createdAt) {

    public DadosDetalhamentoDeck(Deck deck) {
        this(deck.getId(), deck.getName(), deck.getCreatedAt());
    }
}