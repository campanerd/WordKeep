package wordkeep.apiEnglish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
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
    public void cadastrar(@RequestBody @Valid DadosCadastroWord dados){
        service.cadastrar(dados);
    }

    @GetMapping
    public List<DadosListagemWord> listar() {
        return wordRepository.findAll().stream().map(DadosListagemWord::new).toList();
    }

    @Transactional
    @PutMapping
    public void atualizar(@RequestBody @Valid DadosAtualizacaoWord dados) {
        var word = wordRepository.getReferenceById(dados.id());
        word.atualizarInformacoes(dados);
    }

    @DeleteMapping("{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        wordRepository.deleteById(id);
    }
}
