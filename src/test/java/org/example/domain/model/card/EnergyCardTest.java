package org.example.domain.model.card;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnergyCardTest {

    // ── BasicEnergyCard ──────────────────────────────────────────────────────

    @Test
    void basicEnergy_shouldHaveCorrectType() {
        BasicEnergyCard fire = new BasicEnergyCard("xy1-e1", "Fire Energy", "XY1", EnergyType.FIRE);
        assertEquals(EnergyType.FIRE, fire.getEnergyType());
    }

    @Test
    void basicEnergy_shouldProvideExactlyOneEnergyOfItsType() {
        BasicEnergyCard water = new BasicEnergyCard("xy1-e2", "Water Energy", "XY1", EnergyType.WATER);
        assertEquals(List.of(EnergyType.WATER), water.getProvidedEnergy());
    }

    @Test
    void basicEnergy_shouldHaveNoCopyLimit() {
        BasicEnergyCard grass = new BasicEnergyCard("xy1-e3", "Grass Energy", "XY1", EnergyType.GRASS);
        assertEquals(BasicEnergyCard.NO_COPY_LIMIT, grass.getMaxCopiesInDeck());
    }

    @Test
    void basicEnergy_providedEnergy_shouldBeImmutable() {
        BasicEnergyCard lightning = new BasicEnergyCard("xy1-e4", "Lightning Energy", "XY1", EnergyType.LIGHTNING);
        assertThrows(UnsupportedOperationException.class,
                () -> lightning.getProvidedEnergy().add(EnergyType.FIRE));
    }

    @Test
    void basicEnergy_allElevenTypes_shouldBeCreatable() {
        for (EnergyType type : EnergyType.values()) {
            BasicEnergyCard card = new BasicEnergyCard("id-" + type, type.name() + " Energy", "XY1", type);
            assertEquals(type, card.getEnergyType());
        }
    }

    @Test
    void basicEnergy_nullEnergyType_shouldThrow() {
        assertThrows(NullPointerException.class,
                () -> new BasicEnergyCard("id", "Bad Energy", "XY1", null));
    }

    // ── SpecialEnergyCard ────────────────────────────────────────────────────

    @Test
    void specialEnergy_shouldHaveMaxFourCopiesInDeck() {
        SpecialEnergyCard dce = buildDoubleColorless();
        assertEquals(4, dce.getMaxCopiesInDeck());
    }

    @Test
    void specialEnergy_shouldReturnEffectText() {
        SpecialEnergyCard dce = buildDoubleColorless();
        assertFalse(dce.getEffectText().isBlank());
    }

    @Test
    void specialEnergy_shouldProvideMultipleEnergies() {
        SpecialEnergyCard dce = buildDoubleColorless();
        assertEquals(List.of(EnergyType.COLORLESS, EnergyType.COLORLESS), dce.getProvidedEnergy());
    }

    @Test
    void specialEnergy_providedEnergy_shouldBeImmutable() {
        SpecialEnergyCard dce = buildDoubleColorless();
        assertThrows(UnsupportedOperationException.class,
                () -> dce.getProvidedEnergy().add(EnergyType.FIRE));
    }

    @Test
    void specialEnergy_nullEffectText_shouldThrow() {
        assertThrows(NullPointerException.class, () ->
                new SpecialEnergyCard("id", "Rainbow", "XY1", EnergyType.COLORLESS,
                        null, List.of(EnergyType.COLORLESS)));
    }

    @Test
    void specialEnergy_emptyProvidedEnergy_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                new SpecialEnergyCard("id", "Bad", "XY1", EnergyType.COLORLESS,
                        "Effect", List.of()));
    }

    // ── EnergyCostValidator ──────────────────────────────────────────────────

    @Test
    void validator_exactMatch_shouldSucceed() {
        List<EnergyType> available = List.of(EnergyType.FIRE, EnergyType.FIRE);
        List<EnergyType> cost = List.of(EnergyType.FIRE, EnergyType.FIRE);
        assertTrue(EnergyCostValidator.canAfford(available, cost));
    }

    @Test
    void validator_insufficientSpecific_shouldFail() {
        List<EnergyType> available = List.of(EnergyType.FIRE);
        List<EnergyType> cost = List.of(EnergyType.FIRE, EnergyType.FIRE);
        assertFalse(EnergyCostValidator.canAfford(available, cost));
    }

    @Test
    void validator_colorlessAcceptsAnyType() {
        List<EnergyType> available = List.of(EnergyType.WATER, EnergyType.GRASS);
        List<EnergyType> cost = List.of(EnergyType.COLORLESS, EnergyType.COLORLESS);
        assertTrue(EnergyCostValidator.canAfford(available, cost));
    }

    @Test
    void validator_colorlessDoesNotReplaceSpecificRequired() {
        // Cost requires FIRE but only COLORLESS/WATER available
        List<EnergyType> available = List.of(EnergyType.COLORLESS, EnergyType.WATER);
        List<EnergyType> cost = List.of(EnergyType.FIRE);
        assertFalse(EnergyCostValidator.canAfford(available, cost));
    }

    @Test
    void validator_mixedCost_specificFirst_thenColorless() {
        // Cost: FIRE + COLORLESS, Available: FIRE + WATER → ok
        List<EnergyType> available = List.of(EnergyType.FIRE, EnergyType.WATER);
        List<EnergyType> cost = List.of(EnergyType.FIRE, EnergyType.COLORLESS);
        assertTrue(EnergyCostValidator.canAfford(available, cost));
    }

    @Test
    void validator_notEnoughForColorless_shouldFail() {
        List<EnergyType> available = List.of(EnergyType.FIRE);
        List<EnergyType> cost = List.of(EnergyType.FIRE, EnergyType.COLORLESS);
        assertFalse(EnergyCostValidator.canAfford(available, cost));
    }

    @Test
    void validator_zeroCost_alwaysSucceeds() {
        assertTrue(EnergyCostValidator.canAfford(List.of(), List.of()));
        assertTrue(EnergyCostValidator.canAfford(List.of(EnergyType.FIRE), List.of()));
    }

    @Test
    void validator_doubleColorlessEnergy_satisfiesTwoColorlessSlots() {
        // Double Colorless Energy provides [COLORLESS, COLORLESS]
        List<EnergyType> available = List.of(EnergyType.COLORLESS, EnergyType.COLORLESS);
        List<EnergyType> cost = List.of(EnergyType.COLORLESS, EnergyType.COLORLESS);
        assertTrue(EnergyCostValidator.canAfford(available, cost));
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private SpecialEnergyCard buildDoubleColorless() {
        return new SpecialEnergyCard(
                "xy1-s1", "Double Colorless Energy", "XY1", EnergyType.COLORLESS,
                "Provides 2 [C] Energy. Discard this card if this Pokémon leaves play.",
                List.of(EnergyType.COLORLESS, EnergyType.COLORLESS));
    }
}
