package wordkeep.apiEnglish.translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class TranslationService {

    @Autowired
    private WebClient webClient;

    public String traduzir(String word, String sourceLanguage, String targetLanguage) {
        String resultado = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/get")
                        .queryParam("q", word)
                        .queryParam("langpair", sourceLanguage + "|" + targetLanguage)
                        .build())
                .retrieve()
                .bodyToMono(MyMemoryResponse.class)
                .map(response -> response.responseData().translatedText())
                .timeout(Duration.ofSeconds(5))
                .onErrorReturn("")
                .block();

        if (resultado == null || resultado.isBlank() || resultado.equalsIgnoreCase(word)) {
            return "";
        }
        return resultado;
    }
}