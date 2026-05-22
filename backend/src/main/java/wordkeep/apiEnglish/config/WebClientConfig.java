package wordkeep.apiEnglish.config;

import io.netty.resolver.ResolvedAddressTypes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
                .resolver(spec -> spec.resolvedAddressTypes(ResolvedAddressTypes.IPV4_ONLY));

        return WebClient.builder()
                .baseUrl("https://api.mymemory.translated.net")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
 }
