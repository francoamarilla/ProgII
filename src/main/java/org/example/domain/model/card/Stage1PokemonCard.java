package org.example.domain.model.card;

import java.util.List;

/**
 * Pokémon de Fase 1 — evoluciona a partir de un Pokémon Básico.
 */
public class Stage1PokemonCard extends EvolutionPokemonCard {

    public Stage1PokemonCard(
            String id,
            String name,
            String setId,
            int hp,
            List<Attack> attacks,
            List<Weakness> weaknesses,
            List<Resistance> resistances,
            List<EnergyType> retreatCost,
            List<Ability> abilities,
            String evolvesFrom
    ) {
        super(id, name, setId, hp, attacks, weaknesses, resistances, retreatCost, abilities, evolvesFrom);
    }

    @Override
    public PokemonStage getStage() {
        return PokemonStage.STAGE_1;
    }
}
