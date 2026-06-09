package org.example.domain.model.card;

import java.util.List;
import java.util.Objects;

/**
 * Representa un ataque de una carta Pokémon.
 * Inmutable por diseño: los ataques son datos de la carta, nunca cambian en juego.
 */
public record Attack(
        String name,
        List<EnergyType> energyCost,
        int baseDamage,
        String effectText
) {
    public Attack {
        Objects.requireNonNull(name, "Attack name cannot be null");
        Objects.requireNonNull(energyCost, "energyCost cannot be null");
        energyCost = List.copyOf(energyCost);
        if (baseDamage < 0) {
            throw new IllegalArgumentException("baseDamage cannot be negative");
        }
    }

    public int convertedEnergyCost() {
        return energyCost.size();
    }

    public boolean hasEffect() {
        return effectText != null && !effectText.isBlank();
    }
}
