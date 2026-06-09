package org.example.domain.model.card;

public class ItemCard extends TrainerCard {

    public ItemCard(String id, String name, String setId, String effectText) {
        super(id, name, setId, effectText);
    }

    @Override
    public TrainerType getTrainerType() { return TrainerType.ITEM; }
}
