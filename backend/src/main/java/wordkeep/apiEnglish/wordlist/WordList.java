package wordkeep.apiEnglish.wordlist;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wordkeep.apiEnglish.word.Word;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "word_lists")
@Entity(name = "WordList")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class WordList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "wordLists")
    private List<Word> words;

}
