package org.example.domain.model.card;

import java.util.Objects;

/**
 * Resistencia de un Pokémon. En XY1 siempre es -20, mínimo 0.
 * Solo se aplica al Pokémon Activo defensor, nunca a los de Banca.
 */
public record Resistance(EnergyType type) {

    public static final int REDUCTION = 20;

    public Resistance {
        Objects.requireNonNull(type, "Resistance type cannot be null");
    }

    public int apply(int damage) {
        return Math.max(0, damage - REDUCTION);
    }
}
