package org.example.domain.model.card;

import java.util.List;
import java.util.Objects;

public class SpecialEnergyCard extends EnergyCard {

    /** Special Energy is limited to 4 copies per deck (TCG rules). */
    public static final int MAX_COPIES = 4;

    private final String effectText;
    private final List<EnergyType> providedEnergy;

    public SpecialEnergyCard(String id, String name, String setId, EnergyType energyType,
                              String effectText, List<EnergyType> providedEnergy) {
        super(id, name, setId, energyType);
        this.effectText = Objects.requireNonNull(effectText, "effectText cannot be null for Special Energy");
        this.providedEnergy = List.copyOf(
                Objects.requireNonNull(providedEnergy, "providedEnergy cannot be null"));
        if (this.providedEnergy.isEmpty()) {
            throw new IllegalArgumentException("Special Energy must provide at least one energy type");
        }
    }

    @Override
    public int getMaxCopiesInDeck() { return MAX_COPIES; }

    @Override
    public List<EnergyType> getProvidedEnergy() { return providedEnergy; }

    public String getEffectText() { return effectText; }
}
