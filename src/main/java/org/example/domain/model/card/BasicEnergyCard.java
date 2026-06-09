package org.example.domain.model.card;

import java.util.List;

public class BasicEnergyCard extends EnergyCard {

    /** Basic Energy has no copy limit in a deck (TCG rules). */
    public static final int NO_COPY_LIMIT = Integer.MAX_VALUE;

    public BasicEnergyCard(String id, String name, String setId, EnergyType energyType) {
        super(id, name, setId, energyType);
    }

    @Override
    public int getMaxCopiesInDeck() { return NO_COPY_LIMIT; }

    @Override
    public List<EnergyType> getProvidedEnergy() { return List.of(getEnergyType()); }
}
