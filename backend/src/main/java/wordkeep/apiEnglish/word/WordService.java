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

    public Word cadastrar(DadosCadastroWord dados) {
        Word word = new Word(dados);

        if (dados.deckId() != null) {
            if (wordRepository.existsByWordAndDecksId(dados.word(), dados.deckId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "A palavra '" + dados.word() + "' já existe neste deck.");
            }
            var deck = deckRepository.findById(dados.deckId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Deck com id " + dados.deckId() + " não encontrado."));
            word.adicionarDeck(deck);
        }
        String translation = translationService.traduzir(dados.word(), "en", "pt-BR");
        word.setTranslation(translation);
        word.setSourceLanguage("en");
        word.setTargetLanguage("pt-BR");

        return wordRepository.save(word);
    }


    public Word atualizar(DadosAtualizacaoWord dados) {
        Word word = wordRepository.getReferenceById(dados.id());

        if (dados.word() != null) {
            String translation = translationService.traduzir(dados.word(), "en", "pt-BR");
            word.setTranslation(translation);
            word.atualizarInformacoes(dados);
        }

        return word;
    }
    
}