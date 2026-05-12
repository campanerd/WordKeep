package wordkeep.apiEnglish.deck;

public record DadosListagemDeck(Long id,String name) {

    public DadosListagemDeck(Deck deck) {
        this(deck.getId(),deck.getName());
    }
}
