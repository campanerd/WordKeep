package wordkeep.apiEnglish.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wordkeep.apiEnglish.deck.DeckRepository;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private DeckRepository deckRepository;

    public void cadastrar(DadosCadastroWord dados) {
        // 1. cria a word com os dados do DTO
        Word word = new Word(dados);

        // 2. se o deckId foi informado, busca o deck no banco
        if (dados.deckId() != null) {
            deckRepository.findById(dados.deckId()).ifPresent(word::adicionarDeck);
        }

        // 3. salva — o JPA popula words_decks automaticamente
        wordRepository.save(word);
    }
}