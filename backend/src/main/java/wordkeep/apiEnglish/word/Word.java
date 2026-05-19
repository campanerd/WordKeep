package wordkeep.apiEnglish.word;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import jakarta.persistence.Convert;
import wordkeep.apiEnglish.config.LocalDateTimeConverter;
import wordkeep.apiEnglish.deck.Deck;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "words")
@Entity(name = "Word")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;

    @Setter
    private String sourceLanguage;

    @Setter
    private String targetLanguage;

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdAt;

    @Setter
    private String translation;

    @ManyToMany
    @JoinTable(
            name = "words_decks",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "deck_id")
    )
    private List<Deck> decks;

    public Word(DadosCadastroWord dados) {

        this.word = dados.word();
        this.createdAt = LocalDateTime.now();
        this.decks = new ArrayList<>();
    }

    public void adicionarDeck(Deck deck) {
        this.decks.add(deck);
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoWord dados) {
        if (dados.word() != null) {
            this.word = dados.word();
        }
        if (dados.translation() != null) {
            this.translation = dados.translation();
        }
    }
}
