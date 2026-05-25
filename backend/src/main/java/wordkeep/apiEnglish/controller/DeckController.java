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

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Deck criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (nome ausente)")
    })
    public ResponseEntity<DadosDetalhamentoDeck> cadastrar(@RequestBody @Valid DadosCadastroDeck dados, UriComponentsBuilder uriBuilder) {
        var deck = repository.save(new Deck(dados));
        var uri = uriBuilder.path("/decks/{id}").buildAndExpand(deck.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoDeck(deck));
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Lista de todos os decks")
    public ResponseEntity<List<DadosListagemDeck>> listar() {
        var decks = repository.findAll().stream().map(DadosListagemDeck::new).toList();
        return ResponseEntity.ok(decks);
    }

    @GetMapping("/{id}/words")
    @ApiResponse(responseCode = "200", description = "Palavras do deck")
    public ResponseEntity<List<DadosListagemWord>> listarPalavras(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPalavras(id));
    }

    @Transactional
    @PutMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deck atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (id ausente)"),
            @ApiResponse(responseCode = "404", description = "Deck não encontrado")
    })
    public ResponseEntity<DadosDetalhamentoDeck> atualizar(@RequestBody @Valid DadosAtualizacaoDeck dados) {
        var deck = repository.findById(dados.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Deck com id " + dados.id() + " não encontrado."));
        deck.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoDeck(deck));
    }

    @DeleteMapping("{id}")
    @Transactional
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deck excluído (e palavras órfãs removidas)"),
            @ApiResponse(responseCode = "404", description = "Deck não encontrado")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{deckId}/words/{wordId}")
    @Transactional
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Palavra associada ao deck"),
            @ApiResponse(responseCode = "404", description = "Deck ou palavra não encontrado"),
            @ApiResponse(responseCode = "409", description = "Palavra já está neste deck")
    })
    public ResponseEntity<Void> adicionarPalavra(@PathVariable Long deckId, @PathVariable Long wordId) {
        service.adicionarPalavra(deckId, wordId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{deckId}/words/{wordId}")
    @Transactional
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Palavra removida do deck"),
            @ApiResponse(responseCode = "404", description = "Deck ou palavra não encontrado")
    })
    public ResponseEntity<Void> removerPalavra(@PathVariable Long deckId, @PathVariable Long wordId) {
        service.removerPalavra(deckId, wordId);
        return ResponseEntity.noContent().build();
    }

}

