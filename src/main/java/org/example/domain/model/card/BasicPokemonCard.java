package org.example.domain.model.card;

import java.util.List;

/**
 * Pokémon Básico — puede jugarse directamente desde la mano a la Banca
 * o colocarse como Pokémon Activo al inicio de la partida.
 */
public class BasicPokemonCard extends PokemonCard {

    public BasicPokemonCard(
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
    }

    @Override
    public PokemonStage getStage() {
        return PokemonStage.BASIC;
    }
}
