package wordkeep.apiEnglish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wordkeep.apiEnglish.word.DadosCadastroWord;
import wordkeep.apiEnglish.word.WordService;

@RestController
@RequestMapping("words")
public class WordController {

    @Autowired
    private WordService service;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroWord dados){
        service.cadastrar(dados);
    }
}
