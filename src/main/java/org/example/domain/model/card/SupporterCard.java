package org.example.domain.model.card;

public class SupporterCard extends TrainerCard {

    public SupporterCard(String id, String name, String setId, String effectText) {
        super(id, name, setId, effectText);
    }

    @Override
    public TrainerType getTrainerType() { return TrainerType.SUPPORTER; }
}
