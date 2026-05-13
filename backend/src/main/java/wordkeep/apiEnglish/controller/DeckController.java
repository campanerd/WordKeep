package wordkeep.apiEnglish.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import wordkeep.apiEnglish.deck.*;
import wordkeep.apiEnglish.word.DadosListagemWord;
import wordkeep.apiEnglish.word.WordRepository;

import java.util.List;

@RestController
@RequestMapping("decks")
public class DeckController {

    @Autowired
    private DeckRepository repository;

    @Autowired
    private WordRepository wordRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroDeck dados){
        repository.save(new Deck(dados));
    }

    @GetMapping
    public List<DadosListagemDeck> listar() {
        return repository.findAll().stream().map(DadosListagemDeck::new).toList();
    }

    @GetMapping("/{id}/words")
    public List<DadosListagemWord> listarPalavras(@PathVariable Long id) {
        return wordRepository.findByDecksId(id).stream().map(DadosListagemWord::new).toList();
    }

    @Transactional
    @PutMapping
    public void atualizar(@RequestBody @Valid DadosAtualizacaoDeck dados){
        var deck = repository.getReferenceById(dados.id());
        deck.atualizarInformacoes(dados);

    }
}
