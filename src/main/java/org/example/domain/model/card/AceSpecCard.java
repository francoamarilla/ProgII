package org.example.domain.model.card;

public class AceSpecCard extends ItemCard {

    /** Only 1 ACE SPEC card of any kind is allowed in a deck (TCG rules). */
    public static final int MAX_COPIES_PER_DECK = 1;

    public AceSpecCard(String id, String name, String setId, String effectText) {
        super(id, name, setId, effectText);
    }

    @Override
    public TrainerType getTrainerType() { return TrainerType.ACE_SPEC; }
}
