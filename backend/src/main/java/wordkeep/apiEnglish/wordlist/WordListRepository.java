package wordkeep.apiEnglish.wordlist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WordListRepository extends JpaRepository<WordList, Long> {
}
