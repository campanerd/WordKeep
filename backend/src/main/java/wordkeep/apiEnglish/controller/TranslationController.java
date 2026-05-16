package wordkeep.apiEnglish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wordkeep.apiEnglish.translation.TranslationService;

@RestController
@RequestMapping("words")
public class TranslationController {

    @Autowired
    private TranslationService service;

    @GetMapping("/translate")
    public String traduzir(
            @RequestParam String word,
            @RequestParam String sourceLanguage,
            @RequestParam String targetLanguage) {
        return service.traduzir(word, sourceLanguage, targetLanguage);
    }
}