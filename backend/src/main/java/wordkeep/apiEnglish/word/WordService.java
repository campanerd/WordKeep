package wordkeep.apiEnglish.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wordkeep.apiEnglish.deck.DeckRepository;
import wordkeep.apiEnglish.translation.TranslationService;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private TranslationService translationService;

    public void cadastrar(DadosCadastroWord dados) {
        Word word = new Word(dados);

        if (dados.deckId() != null) {
            if (wordRepository.existsByWordAndDecksId(dados.word(), dados.deckId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "A palavra '" + dados.word() + "' já existe neste deck.");
            }
            deckRepository.findById(dados.deckId()).ifPresent(word::adicionarDeck);
        }
        String translation = translationService.traduzir(dados.word(), "en", "pt-BR");
        word.setTranslation(translation);

        wordRepository.save(word);
    }
}