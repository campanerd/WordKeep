package wordkeep.apiEnglish.word;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import wordkeep.apiEnglish.lista.Lista;

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
            name = "word_lista",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "lista_id")
    )
    private List<Lista> listas;
}
