package wordkeep.apiEnglish.word;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findByDecksId(Long deckId);

    boolean existsByWordAndDecksId(String word, Long deckId);
}
