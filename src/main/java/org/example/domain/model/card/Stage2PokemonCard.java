package org.example.domain.model.card;

import java.util.List;

/**
 * Pokémon de Fase 2 — evoluciona a partir de un Pokémon de Fase 1.
 */
public class Stage2PokemonCard extends EvolutionPokemonCard {

    public Stage2PokemonCard(
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
        return PokemonStage.STAGE_2;
    }
}
