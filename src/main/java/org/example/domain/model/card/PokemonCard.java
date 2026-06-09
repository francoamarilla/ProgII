package org.example.domain.model.card;

import java.util.List;
import java.util.Objects;

/**
 * Carta Pokémon — clase abstracta base para Básico, Fase 1, Fase 2 y EX.
 *
 * <p>Implementa los atributos comunes del reglamento XY1:
 * HP, ataques, debilidad (×2), resistencia (-20), costo de retirada y habilidades.
 *
 * <p>Las subclases definen la etapa ({@link #getStage()}) y la cantidad de
 * cartas de Premio que otorga al ser Fuera de Combate ({@link #getPrizeCount()}).
 */
public abstract class PokemonCard extends Card {

    private final int hp;
    private final List<Attack> attacks;
    private final List<Weakness> weaknesses;
    private final List<Resistance> resistances;
    private final List<EnergyType> retreatCost;
    private final List<Ability> abilities;

    protected PokemonCard(
            String id,
            String name,
            String setId,
            int hp,
            List<Attack> attacks,
            List<Weakness> weaknesses,
            List<Resistance> resistances,
            List<EnergyType> retreatCost,
            List<Ability> abilities
    ) {
        super(id, name, setId);
        if (hp <= 0) throw new IllegalArgumentException("HP must be positive, got: " + hp);
        this.hp = hp;
        this.attacks = List.copyOf(Objects.requireNonNull(attacks, "attacks cannot be null"));
        this.weaknesses = List.copyOf(Objects.requireNonNull(weaknesses, "weaknesses cannot be null"));
        this.resistances = List.copyOf(Objects.requireNonNull(resistances, "resistances cannot be null"));
        this.retreatCost = List.copyOf(Objects.requireNonNull(retreatCost, "retreatCost cannot be null"));
        this.abilities = List.copyOf(Objects.requireNonNull(abilities, "abilities cannot be null"));
    }

    public int getHp() { return hp; }
    public List<Attack> getAttacks() { return attacks; }
    public List<Weakness> getWeaknesses() { return weaknesses; }
    public List<Resistance> getResistances() { return resistances; }
    public List<EnergyType> getRetreatCost() { return retreatCost; }
    public List<Ability> getAbilities() { return abilities; }

    /** Etapa de evolución del Pokémon (BASIC, STAGE_1, STAGE_2). */
    public abstract PokemonStage getStage();

    /**
     * Cartas de Premio que toma el oponente al dejar este Pokémon Fuera de Combate.
     * Por defecto: 1. Pokémon-EX y Megaevoluciones devuelven 2.
     */
    public int getPrizeCount() {
        return 1;
    }

    /** Devuelve true si este Pokémon es una carta de evolución (Fase 1 o Fase 2). */
    public boolean isEvolution() {
        return getStage() != PokemonStage.BASIC;
    }

    /** Costo de retirada expresado como cantidad de cartas de Energía a descartar. */
    public int getRetreatCostCount() {
        return retreatCost.size();
    }

    /** Devuelve true si el Pokémon tiene al menos una Habilidad. */
    public boolean hasAbility() {
        return !abilities.isEmpty();
    }
}
