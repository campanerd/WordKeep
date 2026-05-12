package wordkeep.apiEnglish.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wordkeep.apiEnglish.deck.DeckRepository;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private DeckRepository deckRepository;

    public void cadastrar(DadosCadastroWord dados) {
        Word word = new Word(dados);

        if (dados.deckId() != null) {
            if (wordRepository.existsByWordAndDecksId(dados.word(), dados.deckId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "A palavra '" + dados.word() + "' já existe neste deck.");
            }
            deckRepository.findById(dados.deckId()).ifPresent(word::adicionarDeck);
        }

        wordRepository.save(word);
    }
}