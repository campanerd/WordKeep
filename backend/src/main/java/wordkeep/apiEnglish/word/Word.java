package wordkeep.apiEnglish.word;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import wordkeep.apiEnglish.wordlist.WordList;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "words")
@Entity(name = "Word")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Word {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;
    private String translation;
    private String sourceLanguage;
    private String targetLanguage;

    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "word_word_list",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "word_list_id")
    )
    private List<WordList> wordLists;
}
