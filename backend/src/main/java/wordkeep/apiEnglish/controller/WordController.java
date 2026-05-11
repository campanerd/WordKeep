package wordkeep.apiEnglish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wordkeep.apiEnglish.word.DadosCadastroWord;
import wordkeep.apiEnglish.word.Word;
import wordkeep.apiEnglish.word.WordRepository;

@RestController
@RequestMapping("words")
public class WordController {

    @Autowired
    private WordRepository repository;

    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroWord dados){
        repository.save(new Word(dados));
    }
}
