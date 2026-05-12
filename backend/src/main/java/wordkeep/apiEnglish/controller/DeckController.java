package wordkeep.apiEnglish.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import wordkeep.apiEnglish.deck.DadosCadastroDeck;
import wordkeep.apiEnglish.deck.DadosListagemDeck;
import wordkeep.apiEnglish.deck.Deck;
import wordkeep.apiEnglish.deck.DeckRepository;

import java.util.List;

@RestController
@RequestMapping("decks")
public class DeckController {

    @Autowired
    private DeckRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroDeck dados){
        repository.save(new Deck(dados));
    }

    @GetMapping
    public List<DadosListagemDeck> listar() {
        return repository.findAll().stream().map(DadosListagemDeck::new).toList();
    }
}
