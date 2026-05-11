package wordkeep.apiEnglish.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wordkeep.apiEnglish.deck.DadosCadastroDeck;
import wordkeep.apiEnglish.deck.Deck;
import wordkeep.apiEnglish.deck.DeckRepository;

@RestController
@RequestMapping("decks")
public class DeckController {

    @Autowired
    private DeckRepository repository;

    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroDeck dados){
        repository.save(new Deck(dados));
    }
}
