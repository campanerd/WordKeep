package wordkeep.apiEnglish.word;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findByDecksId(Long deckId);

    boolean existsByWordIgnoreCaseAndDecksId(String word, Long deckId);

    Optional<Word> findFirstByWordIgnoreCase(String word);
}
