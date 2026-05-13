package wordkeep.apiEnglish.deck;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import wordkeep.apiEnglish.config.LocalDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wordkeep.apiEnglish.word.Word;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "decks")
@Entity(name = "Deck")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Deck {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "decks")
    private List<Word> words;

    public Deck(DadosCadastroDeck dados) {
        this.name = dados.name();
        this.createdAt = LocalDateTime.now();
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoDeck dados) {
        if (dados.name() != null ) {
            this.name = dados.name();
        }
    }
}
