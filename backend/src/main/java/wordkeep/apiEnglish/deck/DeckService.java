package wordkeep.apiEnglish.deck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
            var deck = deckRepository.getReferenceById(id);
            var words = wordRepository.findByDecksId(id);
            wordRepository.deleteAll(words);
            deckRepository.delete(deck);
        }
}


