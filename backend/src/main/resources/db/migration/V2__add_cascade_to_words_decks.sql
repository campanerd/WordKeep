DROP TABLE IF EXISTS words_decks;

CREATE TABLE words_decks (
    word_id INTEGER NOT NULL,
    deck_id INTEGER NOT NULL,
    PRIMARY KEY (word_id, deck_id),
    FOREIGN KEY (word_id) REFERENCES words(id) ON DELETE CASCADE,
    FOREIGN KEY (deck_id) REFERENCES decks(id) ON DELETE CASCADE
);
