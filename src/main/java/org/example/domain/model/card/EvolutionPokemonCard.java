package org.example.domain.model.card;

import java.util.List;
import java.util.Objects;

/**
 * Carta de evolución — base común para Fase 1 y Fase 2.
 *
 * <p>Reglas de evolución del reglamento XY1:
 * <ul>
 *   <li>No puede evolucionar en el turno en que el Pokémon entró en juego.
 *   <li>No puede evolucionar en el primer turno del jugador.
 *   <li>Al evolucionar, el Pokémon conserva daño, energías y cartas unidas.
 *   <li>Al evolucionar, se eliminan todas las condiciones especiales.
 *   <li>El Pokémon evolucionado no puede volver a evolucionar en el mismo turno.
 * </ul>
 *
 * Nota: la aplicación de estas restricciones es responsabilidad del {@code RuleValidator},
 * no de esta clase. Aquí solo se almacena el dato de la carta.
 */
public abstract class EvolutionPokemonCard extends PokemonCard {

    private final String evolvesFrom;

    protected EvolutionPokemonCard(
            String id,
            String name,
            String setId,
            int hp,
            List<Attack> attacks,
            List<Weakness> weaknesses,
            List<Resistance> resistances,
            List<EnergyType> retreatCost,
            List<Ability> abilities,
            String evolvesFrom
    ) {
        super(id, name, setId, hp, attacks, weaknesses, resistances, retreatCost, abilities);
        this.evolvesFrom = Objects.requireNonNull(evolvesFrom,
                "evolvesFrom cannot be null for evolution cards");
    }

    /**
     * Nombre del Pokémon sobre el que se juega esta carta de evolución.
     * Debe coincidir exactamente con el nombre del Pokémon en juego.
     */
    public String getEvolvesFrom() {
        return evolvesFrom;
    }
}
