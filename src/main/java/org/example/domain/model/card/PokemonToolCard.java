package org.example.domain.model.card;

public class PokemonToolCard extends TrainerCard {

    public PokemonToolCard(String id, String name, String setId, String effectText) {
        super(id, name, setId, effectText);
    }

    @Override
    public TrainerType getTrainerType() { return TrainerType.POKEMON_TOOL; }
}
