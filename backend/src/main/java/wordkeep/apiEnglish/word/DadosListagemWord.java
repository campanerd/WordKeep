package wordkeep.apiEnglish.word;

public record DadosListagemWord(Long id,String word, String translation) {

    public DadosListagemWord(Word word) {
        this(word.getId(), word.getWord(), word.getTranslation());
    }

}
