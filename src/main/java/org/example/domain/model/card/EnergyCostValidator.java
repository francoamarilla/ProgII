package org.example.domain.model.card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Validates whether available energy satisfies an attack's energy cost.
 * COLORLESS (★) in the cost is satisfied by any single energy of any type.
 * Specific types (e.g., FIRE) must be matched exactly.
 */
public final class EnergyCostValidator {

    private EnergyCostValidator() {}

    /**
     * Returns true if the available energy pool fully covers the attack cost.
     *
     * Algorithm:
     * 1. Match specific (non-COLORLESS) cost slots first.
     * 2. Use leftover energy to satisfy COLORLESS slots.
     */
    public static boolean canAfford(List<EnergyType> available, List<EnergyType> cost) {
        Map<EnergyType, Integer> pool = new HashMap<>();
        for (EnergyType e : available) {
            pool.merge(e, 1, Integer::sum);
        }

        int colorlessNeeded = 0;

        for (EnergyType required : cost) {
            if (required == EnergyType.COLORLESS) {
                colorlessNeeded++;
            } else {
                int count = pool.getOrDefault(required, 0);
                if (count == 0) return false;
                pool.put(required, count - 1);
            }
        }

        int remaining = pool.values().stream().mapToInt(Integer::intValue).sum();
        return remaining >= colorlessNeeded;
    }
}
