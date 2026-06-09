package org.example.domain.model.card;

import java.util.Objects;

/**
 * Habilidad de un Pokémon. No es un ataque: no consume el ataque del turno
 * y no requiere energía por sí misma (salvo que el texto lo indique).
 */
public record Ability(
        String name,
        String text,
        AbilityType abilityType
) {
    public Ability {
        Objects.requireNonNull(name, "Ability name cannot be null");
        Objects.requireNonNull(text, "Ability text cannot be null");
        Objects.requireNonNull(abilityType, "Ability type cannot be null");
    }
}
