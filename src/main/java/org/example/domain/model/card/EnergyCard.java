package org.example.domain.model.card;

import java.util.List;
import java.util.Objects;

public abstract class EnergyCard extends Card {

    private final EnergyType energyType;

    protected EnergyCard(String id, String name, String setId, EnergyType energyType) {
        super(id, name, setId);
        this.energyType = Objects.requireNonNull(energyType, "energyType cannot be null");
    }

    public EnergyType getEnergyType() { return energyType; }

    /** Maximum number of copies allowed in a single deck. */
    public abstract int getMaxCopiesInDeck();

    /** Energy types this card provides when attached to a Pokémon. */
    public abstract List<EnergyType> getProvidedEnergy();
}
