package wordkeep.apiEnglish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;
import wordkeep.apiEnglish.word.*;

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
    public ResponseEntity<DadosDetalhamentoWord> cadastrar(@RequestBody @Valid DadosCadastroWord dados, UriComponentsBuilder uriBuilder) {
        var word = service.cadastrar(dados);
        var uri = uriBuilder.path("/words/{id}").buildAndExpand(word.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoWord(word));
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemWord>> listar() {
        var words = wordRepository.findAll().stream().map(DadosListagemWord::new).toList();
        return ResponseEntity.ok(words);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<DadosDetalhamentoWord> atualizar(@RequestBody @Valid DadosAtualizacaoWord dados) {
        var word = wordRepository.getReferenceById(dados.id());
        word.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoWord(word));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        wordRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
