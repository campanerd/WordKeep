CREATE TABLE IF NOT EXISTS words (
     id INTEGER PRIMARY KEY AUTOINCREMENT,
     word TEXT NOT NULL,
     translation TEXT,
     source_language TEXT,
     target_language TEXT,
     created_at TEXT
);

CREATE TABLE IF NOT EXISTS decks (
     id INTEGER PRIMARY KEY AUTOINCREMENT,
     name TEXT NOT NULL,
     created_at TEXT
);

CREATE TABLE IF NOT EXISTS words_decks (
    word_id INTEGER NOT NULL,
    deck_id INTEGER NOT NULL,
    PRIMARY KEY (word_id, deck_id),
    FOREIGN KEY (word_id) REFERENCES words(id),
    FOREIGN KEY (deck_id) REFERENCES decks(id)
    );