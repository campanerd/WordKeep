package wordkeep.apiEnglish.translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TranslationService {

    @Autowired
    private WebClient webClient;

    public String traduzir(String word, String sourceLanguage, String targetLanguage) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/get")
                        .queryParam("q", word)
                        .queryParam("langpair", sourceLanguage + "|" + targetLanguage)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}