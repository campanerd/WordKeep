package wordkeep.apiEnglish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;
import wordkeep.apiEnglish.word.*;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("words")
public class WordController {

    @Autowired
    private WordService service;
    @Autowired
    private WordRepository wordRepository;

    @PostMapping
    @Transactional
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Palavra criada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (palavra ou deckId ausente)"),
            @ApiResponse(responseCode = "404", description = "Deck não encontrado"),
            @ApiResponse(responseCode = "409", description = "Palavra já existe neste deck")
    })
    public ResponseEntity<DadosDetalhamentoWord> cadastrar(@RequestBody @Valid DadosCadastroWord dados, UriComponentsBuilder uriBuilder) {
        var word = service.cadastrar(dados);
        var uri = uriBuilder.path("/words/{id}").buildAndExpand(word.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoWord(word));
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Lista de todas as palavras")
    public ResponseEntity<List<DadosListagemWord>> listar() {
        var words = wordRepository.findAll().stream().map(DadosListagemWord::new).toList();
        return ResponseEntity.ok(words);
    }

    @Transactional
    @PutMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Palavra atualizada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (id ausente)"),
            @ApiResponse(responseCode = "404", description = "Palavra não encontrada")
    })
    public ResponseEntity<DadosDetalhamentoWord> atualizar(@RequestBody @Valid DadosAtualizacaoWord dados) {
        var word = service.atualizar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoWord(word));
    }

    @DeleteMapping("{id}")
    @Transactional
    @ApiResponse(responseCode = "204", description = "Palavra excluída")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        wordRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
