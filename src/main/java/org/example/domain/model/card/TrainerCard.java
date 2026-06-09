package org.example.domain.model.card;

import java.util.Objects;

public abstract class TrainerCard extends Card {

    private final String effectText;

    protected TrainerCard(String id, String name, String setId, String effectText) {
        super(id, name, setId);
        this.effectText = Objects.requireNonNull(effectText, "effectText cannot be null");
    }

    public String getEffectText() { return effectText; }

    public abstract TrainerType getTrainerType();
}
