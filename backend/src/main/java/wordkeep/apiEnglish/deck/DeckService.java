package wordkeep.apiEnglish.deck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wordkeep.apiEnglish.word.DadosListagemWord;
import wordkeep.apiEnglish.word.WordRepository;

import java.util.List;

@Service
public class DeckService {

        @Autowired
        private DeckRepository deckRepository;

        @Autowired
        private WordRepository wordRepository;

        public List<DadosListagemWord> listarPalavras(Long id) {
            return wordRepository.findByDecksId(id).stream().map(DadosListagemWord::new).toList();
        }

    @Transactional
    public void excluir(Long id) {
        var deck = deckRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Deck com id " + id + " não encontrado."));

        var words = wordRepository.findByDecksId(id);
        for (var w : words) {
            w.getDecks().remove(deck);
        }
        wordRepository.saveAll(words);

        var orfas = words.stream().filter(w -> w.getDecks().isEmpty()).toList();
        wordRepository.deleteAll(orfas);

        deckRepository.delete(deck);
    }

    @Transactional
    public void adicionarPalavra(Long deckId, Long wordId) {
        var deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck não encontrado."));
        var word = wordRepository.findById(wordId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Palavra não encontrada."));

        if (word.getDecks().contains(deck)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A palavra já está neste deck.");
        }
        word.adicionarDeck(deck);
        wordRepository.save(word);
    }

    @Transactional
    public void removerPalavra(Long deckId, Long wordId) {
        var deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck não encontrado."));
        var word = wordRepository.findById(wordId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Palavra não encontrada."));

        word.getDecks().remove(deck);
        if (word.getDecks().isEmpty()) {
            wordRepository.delete(word);   // virou órfã → apaga
        } else {
            wordRepository.save(word);
        }
    }

}


