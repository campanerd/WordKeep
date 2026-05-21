package wordkeep.apiEnglish.word;

import java.time.LocalDateTime;

public record DadosDetalhamentoWord(Long id, String word, String translation, String sourceLanguage, String targetLanguage, LocalDateTime createdAt) {

    public DadosDetalhamentoWord (Word word){
        this(word.getId(), word.getWord(), word.getTranslation(), word.getSourceLanguage(), word.getTargetLanguage(), word.getCreatedAt());
    }



}
