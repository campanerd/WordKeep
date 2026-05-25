package wordkeep.apiEnglish.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import wordkeep.apiEnglish.deck.*;
import wordkeep.apiEnglish.word.DadosListagemWord;


import java.util.List;

@RestController
@RequestMapping("decks")
public class DeckController {

    @Autowired
    private DeckRepository repository;

    @Autowired
    private DeckService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoDeck> cadastrar(@RequestBody @Valid DadosCadastroDeck dados, UriComponentsBuilder uriBuilder) {
        var deck = repository.save(new Deck(dados));
        var uri = uriBuilder.path("/decks/{id}").buildAndExpand(deck.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoDeck(deck));
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemDeck>> listar() {
        var decks = repository.findAll().stream().map(DadosListagemDeck::new).toList();
        return ResponseEntity.ok(decks);
    }

    @GetMapping("/{id}/words")
    public ResponseEntity<List<DadosListagemWord>> listarPalavras(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPalavras(id));
    }

    @Transactional
    @PutMapping
    public ResponseEntity<DadosDetalhamentoDeck> atualizar(@RequestBody @Valid DadosAtualizacaoDeck dados) {
        var deck = repository.findById(dados.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Deck com id " + dados.id() + " não encontrado."));
        deck.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoDeck(deck));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{deckId}/words/{wordId}")
    @Transactional
    public ResponseEntity<Void> adicionarPalavra(@PathVariable Long deckId, @PathVariable Long wordId) {
        service.adicionarPalavra(deckId, wordId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{deckId}/words/{wordId}")
    @Transactional
    public ResponseEntity<Void> removerPalavra(@PathVariable Long deckId, @PathVariable Long wordId) {
        service.removerPalavra(deckId, wordId);
        return ResponseEntity.noContent().build();
    }

}

