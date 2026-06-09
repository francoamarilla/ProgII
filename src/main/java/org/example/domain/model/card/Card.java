package org.example.domain.model.card;

import java.util.Objects;

/**
 * Entidad base del dominio para todas las cartas del JCC Pokémon.
 * Dominio puro: sin dependencias de Spring ni JPA.
 */
public abstract class Card {

    private final String id;
    private final String name;
    private final String setId;

    protected Card(String id, String name, String setId) {
        this.id = Objects.requireNonNull(id, "Card id cannot be null");
        this.name = Objects.requireNonNull(name, "Card name cannot be null");
        this.setId = Objects.requireNonNull(setId, "Card setId cannot be null");
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSetId() { return setId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return id.equals(card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "%s[id=%s, name=%s]".formatted(getClass().getSimpleName(), id, name);
    }
}
