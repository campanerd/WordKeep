package wordkeep.apiEnglish.word;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import wordkeep.apiEnglish.deck.Deck;

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

    @Column(columnDefinition = "TEXT")
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "words_decks",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "deck_id")
    )
    private List<Deck> decks;

    public Word(DadosCadastroWord dados) {

        this.word = dados.word();
        this.translation = dados.translation();
        this.sourceLanguage = dados.sourceLanguage();
        this.targetLanguage = dados.targetLanguage();
        this.createdAt = LocalDateTime.now();
    }
}
