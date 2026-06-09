package org.example.domain.model.card;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("KAN-23 - Jerarquía de cartas Pokémon")
class PokemonCardHierarchyTest {

    // ─── Helpers ────────────────────────────────────────────────────────────────

    private BasicPokemonCard bulbasaur() {
        return new BasicPokemonCard(
                "xy1-1", "Bulbasaur", "xy1", 60,
                List.of(new Attack("Tackle", List.of(EnergyType.COLORLESS), 10, "")),
                List.of(new Weakness(EnergyType.FIRE)),
                List.of(),
                List.of(EnergyType.COLORLESS),
                List.of()
        );
    }

    private ExPokemonCard venusaurEx() {
        return new ExPokemonCard(
                "xy1-2", "Venusaur-EX", "xy1", 180,
                List.of(new Attack("Poison Powder",
                        List.of(EnergyType.GRASS, EnergyType.COLORLESS, EnergyType.COLORLESS),
                        60, "Tu Pokémon Activo rival está Envenenado.")),
                List.of(new Weakness(EnergyType.FIRE)),
                List.of(),
                List.of(EnergyType.COLORLESS, EnergyType.COLORLESS, EnergyType.COLORLESS, EnergyType.COLORLESS),
                List.of()
        );
    }

    private Stage1PokemonCard ivysaur() {
        return new Stage1PokemonCard(
                "xy1-3", "Ivysaur", "xy1", 90,
                List.of(new Attack("Vine Whip",
                        List.of(EnergyType.GRASS, EnergyType.COLORLESS), 30, "")),
                List.of(new Weakness(EnergyType.FIRE)),
                List.of(),
                List.of(EnergyType.COLORLESS, EnergyType.COLORLESS),
                List.of(),
                "Bulbasaur"
        );
    }

    private Stage2PokemonCard venusaur() {
        return new Stage2PokemonCard(
                "xy1-4", "Venusaur", "xy1", 130,
                List.of(new Attack("Mega Drain",
                        List.of(EnergyType.GRASS, EnergyType.GRASS, EnergyType.COLORLESS), 60,
                        "Cura 30 daño de este Pokémon.")),
                List.of(new Weakness(EnergyType.FIRE)),
                List.of(),
                List.of(EnergyType.COLORLESS, EnergyType.COLORLESS),
                List.of(new Ability("Overgrow", "Reduce en 10 el coste de todos los ataques Planta.", AbilityType.PASSIVE)),
                "Ivysaur"
        );
    }

    // ─── BasicPokemonCard ───────────────────────────────────────────────────────

    @Test
    @DisplayName("BasicPokemonCard tiene etapa BASIC")
    void basicPokemon_stage_isBasic() {
        assertEquals(PokemonStage.BASIC, bulbasaur().getStage());
    }

    @Test
    @DisplayName("BasicPokemonCard no es evolución")
    void basicPokemon_isNotEvolution() {
        assertFalse(bulbasaur().isEvolution());
    }

    @Test
    @DisplayName("BasicPokemonCard otorga 1 carta de Premio")
    void basicPokemon_prizeCount_isOne() {
        assertEquals(1, bulbasaur().getPrizeCount());
    }

    @Test
    @DisplayName("BasicPokemonCard expone HP, ataques, debilidad, costo de retirada")
    void basicPokemon_attributes_areCorrect() {
        BasicPokemonCard card = bulbasaur();
        assertEquals(60, card.getHp());
        assertEquals(1, card.getAttacks().size());
        assertEquals("Tackle", card.getAttacks().get(0).name());
        assertEquals(1, card.getWeaknesses().size());
        assertEquals(EnergyType.FIRE, card.getWeaknesses().get(0).type());
        assertEquals(1, card.getRetreatCostCount());
    }

    // ─── ExPokemonCard ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("ExPokemonCard tiene etapa BASIC (es Pokémon Básico)")
    void exPokemon_stage_isBasic() {
        assertEquals(PokemonStage.BASIC, venusaurEx().getStage());
    }

    @Test
    @DisplayName("ExPokemonCard otorga 2 cartas de Premio")
    void exPokemon_prizeCount_isTwo() {
        assertEquals(2, venusaurEx().getPrizeCount());
    }

    @Test
    @DisplayName("ExPokemonCard falla si el nombre no termina en -EX")
    void exPokemon_invalidName_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new ExPokemonCard("xy1-99", "Venusaur", "xy1", 180,
                        List.of(), List.of(), List.of(), List.of(), List.of())
        );
    }

    @Test
    @DisplayName("ExPokemonCard no es evolución (es instancia de BasicPokemonCard)")
    void exPokemon_isInstanceOfBasicPokemon() {
        assertInstanceOf(BasicPokemonCard.class, venusaurEx());
        assertFalse(venusaurEx().isEvolution());
    }

    // ─── Stage1PokemonCard ──────────────────────────────────────────────────────

    @Test
    @DisplayName("Stage1PokemonCard tiene etapa STAGE_1")
    void stage1Pokemon_stage_isStage1() {
        assertEquals(PokemonStage.STAGE_1, ivysaur().getStage());
    }

    @Test
    @DisplayName("Stage1PokemonCard es evolución")
    void stage1Pokemon_isEvolution() {
        assertTrue(ivysaur().isEvolution());
    }

    @Test
    @DisplayName("Stage1PokemonCard expone evolvesFrom correctamente")
    void stage1Pokemon_evolvesFrom_isCorrect() {
        assertEquals("Bulbasaur", ivysaur().getEvolvesFrom());
    }

    @Test
    @DisplayName("Stage1PokemonCard otorga 1 carta de Premio")
    void stage1Pokemon_prizeCount_isOne() {
        assertEquals(1, ivysaur().getPrizeCount());
    }

    // ─── Stage2PokemonCard ──────────────────────────────────────────────────────

    @Test
    @DisplayName("Stage2PokemonCard tiene etapa STAGE_2")
    void stage2Pokemon_stage_isStage2() {
        assertEquals(PokemonStage.STAGE_2, venusaur().getStage());
    }

    @Test
    @DisplayName("Stage2PokemonCard es evolución")
    void stage2Pokemon_isEvolution() {
        assertTrue(venusaur().isEvolution());
    }

    @Test
    @DisplayName("Stage2PokemonCard expone evolvesFrom correctamente")
    void stage2Pokemon_evolvesFrom_isCorrect() {
        assertEquals("Ivysaur", venusaur().getEvolvesFrom());
    }

    @Test
    @DisplayName("Stage2PokemonCard con Habilidad: hasAbility() devuelve true")
    void stage2Pokemon_withAbility_hasAbilityIsTrue() {
        assertTrue(venusaur().hasAbility());
        assertEquals("Overgrow", venusaur().getAbilities().get(0).name());
        assertEquals(AbilityType.PASSIVE, venusaur().getAbilities().get(0).abilityType());
    }

    // ─── Invariantes comunes ────────────────────────────────────────────────────

    @Test
    @DisplayName("HP debe ser positivo")
    void pokemonCard_negativeHp_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new BasicPokemonCard("id", "name", "xy1", 0,
                        List.of(), List.of(), List.of(), List.of(), List.of())
        );
    }

    @Test
    @DisplayName("Las listas son inmutables desde fuera")
    void pokemonCard_lists_areImmutable() {
        BasicPokemonCard card = bulbasaur();
        assertThrows(UnsupportedOperationException.class, () ->
                card.getAttacks().add(new Attack("Extra", List.of(), 0, ""))
        );
    }

    @Test
    @DisplayName("Weakness.apply() duplica el daño")
    void weakness_apply_doublesBaseDamage() {
        Weakness weakness = new Weakness(EnergyType.FIRE);
        assertEquals(100, weakness.apply(50));
    }

    @Test
    @DisplayName("Resistance.apply() reduce el daño en 20, mínimo 0")
    void resistance_apply_reducesDamageAndFloorAtZero() {
        Resistance resistance = new Resistance(EnergyType.WATER);
        assertEquals(30, resistance.apply(50));
        assertEquals(0, resistance.apply(10));  // 10 - 20 = -10 → 0
    }

    @Test
    @DisplayName("Attack.hasEffect() devuelve false cuando no hay texto")
    void attack_withoutEffect_hasEffectIsFalse() {
        Attack attack = new Attack("Tackle", List.of(EnergyType.COLORLESS), 10, "");
        assertFalse(attack.hasEffect());
    }

    @Test
    @DisplayName("Attack.hasEffect() devuelve true cuando hay texto de efecto")
    void attack_withEffect_hasEffectIsTrue() {
        Attack attack = new Attack("Poison Powder", List.of(EnergyType.GRASS), 60,
                "El Pokémon Activo rival está Envenenado.");
        assertTrue(attack.hasEffect());
    }

    @Test
    @DisplayName("Card igualdad por ID")
    void card_equality_basedOnId() {
        BasicPokemonCard card1 = bulbasaur();
        BasicPokemonCard card2 = bulbasaur();
        assertEquals(card1, card2);
    }
}
