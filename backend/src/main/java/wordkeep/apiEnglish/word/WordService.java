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
        var deck = deckRepository.findById(dados.deckId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Deck com id " + dados.deckId() + " não encontrado."));

        if (wordRepository.existsByWordIgnoreCaseAndDecksId(dados.word(), dados.deckId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "A palavra '" + dados.word() + "' já existe neste deck.");
        }

        Word word = wordRepository.findFirstByWordIgnoreCase(dados.word())
                .orElseGet(() -> {
                    Word nova = new Word(dados);
                    nova.setTranslation(translationService.traduzir(dados.word(), "en", "pt-BR"));
                    nova.setSourceLanguage("en");
                    nova.setTargetLanguage("pt-BR");
                    return nova;
                });

        word.adicionarDeck(deck);
        return wordRepository.save(word);
    }


    public Word atualizar(DadosAtualizacaoWord dados) {
        Word word = wordRepository.findById(dados.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Palavra com id " + dados.id() + " não encontrada."));

        if (dados.word() != null) {
            word.setTranslation(translationService.traduzir(dados.word(), "en", "pt-BR"));
            word.atualizarInformacoes(dados);
        }
        return word;
    }

}