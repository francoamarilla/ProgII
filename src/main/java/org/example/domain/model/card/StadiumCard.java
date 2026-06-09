package org.example.domain.model.card;

public class StadiumCard extends TrainerCard {

    public StadiumCard(String id, String name, String setId, String effectText) {
        super(id, name, setId, effectText);
    }

    @Override
    public TrainerType getTrainerType() { return TrainerType.STADIUM; }
}
