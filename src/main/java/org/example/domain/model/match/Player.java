package org.example.domain.model.match;

import java.util.List;
import java.util.Objects;

public record Player(String playerId, List<String> deckCardIds) {

    public static final int DECK_SIZE = 60;

    public Player {
        Objects.requireNonNull(playerId, "playerId cannot be null");
        Objects.requireNonNull(deckCardIds, "deckCardIds cannot be null");
        if (deckCardIds.size() != DECK_SIZE)
            throw new IllegalArgumentException("Deck must have exactly " + DECK_SIZE + " cards, got " + deckCardIds.size());
        deckCardIds = List.copyOf(deckCardIds);
    }
}
