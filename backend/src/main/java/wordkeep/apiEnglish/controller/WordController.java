package wordkeep.apiEnglish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    public void cadastrar(@RequestBody DadosCadastroWord dados){
        service.cadastrar(dados);
    }
}
