package org.example.domain.model.card;

import java.util.List;

/**
 * Pokémon-EX — Pokémon Básico con regla especial del reglamento XY1:
 * cuando queda Fuera de Combate, el oponente toma 2 cartas de Premio.
 *
 * <p>El sufijo "-EX" es parte integral del nombre de la carta
 * (ej: "Venusaur-EX" y "Venusaur" son cartas distintas para el límite de 4 copias).
 */
public class ExPokemonCard extends BasicPokemonCard {

    public ExPokemonCard(
            String id,
            String name,
            String setId,
            int hp,
            List<Attack> attacks,
            List<Weakness> weaknesses,
            List<Resistance> resistances,
            List<EnergyType> retreatCost,
            List<Ability> abilities
    ) {
        super(id, name, setId, hp, attacks, weaknesses, resistances, retreatCost, abilities);
        if (!name.endsWith("-EX")) {
            throw new IllegalArgumentException(
                    "ExPokemonCard name must end with '-EX', got: " + name);
        }
    }

    /** Pokémon-EX otorga 2 cartas de Premio al ser Fuera de Combate (regla XY1). */
    @Override
    public int getPrizeCount() {
        return 2;
    }
}
