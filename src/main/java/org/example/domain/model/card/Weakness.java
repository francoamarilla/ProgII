package org.example.domain.model.card;

import java.util.Objects;

/**
 * Debilidad de un Pokémon. En XY1 siempre es ×2.
 * Solo se aplica al Pokémon Activo defensor, nunca a los de Banca.
 */
public record Weakness(EnergyType type) {

    public static final int MULTIPLIER = 2;

    public Weakness {
        Objects.requireNonNull(type, "Weakness type cannot be null");
    }

    public int apply(int damage) {
        return damage * MULTIPLIER;
    }
}
