package wordkeep.apiEnglish.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "WordKeep API",
                version = "1.0",
                description = "API para estudo de vocabulário em inglês. " +
                        "Cadastre palavras (com tradução automática en→pt-BR) e organize-as em decks temáticos.",
                contact = @Contact(
                        name = "Davi Campaner & Vinicius Regazio",
                        url = "https://github.com/campanerd"
                )
        )
)
public class OpenApiConfig {
}