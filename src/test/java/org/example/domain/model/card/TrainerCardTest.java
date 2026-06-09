package org.example.domain.model.card;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TrainerCardTest {

    // ── TrainerType enum ─────────────────────────────────────────────────────

    @Test
    void trainerType_shouldContainAllFiveSubtypes() {
        Set<TrainerType> types = Arrays.stream(TrainerType.values()).collect(Collectors.toSet());
        assertTrue(types.contains(TrainerType.ITEM));
        assertTrue(types.contains(TrainerType.ACE_SPEC));
        assertTrue(types.contains(TrainerType.SUPPORTER));
        assertTrue(types.contains(TrainerType.STADIUM));
        assertTrue(types.contains(TrainerType.POKEMON_TOOL));
        assertEquals(5, types.size());
    }

    // ── TrainerCard (abstract) — via ItemCard ────────────────────────────────

    @Test
    void trainerCard_nullEffectText_shouldThrow() {
        assertThrows(NullPointerException.class,
                () -> new ItemCard("id", "Potion", "XY1", null));
    }

    @Test
    void trainerCard_shouldReturnEffectText() {
        ItemCard potion = buildPotion();
        assertFalse(potion.getEffectText().isBlank());
    }

    @Test
    void trainerCard_equalityById() {
        ItemCard a = new ItemCard("xy1-t1", "Potion", "XY1", "Heal 30 damage.");
        ItemCard b = new ItemCard("xy1-t1", "Potion", "XY1", "Heal 30 damage.");
        assertEquals(a, b);
    }

    @Test
    void trainerCard_differentId_notEqual() {
        ItemCard a = new ItemCard("xy1-t1", "Potion", "XY1", "Heal 30 damage.");
        ItemCard b = new ItemCard("xy1-t2", "Potion", "XY1", "Heal 30 damage.");
        assertNotEquals(a, b);
    }

    // ── ItemCard ─────────────────────────────────────────────────────────────

    @Test
    void itemCard_shouldHaveItemTrainerType() {
        assertEquals(TrainerType.ITEM, buildPotion().getTrainerType());
    }

    // ── AceSpecCard ──────────────────────────────────────────────────────────

    @Test
    void aceSpecCard_shouldHaveAceSpecTrainerType() {
        assertEquals(TrainerType.ACE_SPEC, buildAceSpec().getTrainerType());
    }

    @Test
    void aceSpecCard_shouldHaveMaxOneCopyPerDeck() {
        assertEquals(1, AceSpecCard.MAX_COPIES_PER_DECK);
    }

    @Test
    void aceSpecCard_shouldBeSubtypeOfItemCard() {
        assertTrue(buildAceSpec() instanceof ItemCard,
                "AceSpecCard must be a subtype of ItemCard (Liskov Substitution)");
    }

    @Test
    void aceSpecCard_trainerType_shouldDifferFromItem() {
        assertNotEquals(TrainerType.ITEM, buildAceSpec().getTrainerType());
    }

    // ── SupporterCard ────────────────────────────────────────────────────────

    @Test
    void supporterCard_shouldHaveSupporterTrainerType() {
        SupporterCard professor = new SupporterCard(
                "xy1-t10", "Professor Sycamore", "XY1",
                "Discard your hand and draw 7 cards.");
        assertEquals(TrainerType.SUPPORTER, professor.getTrainerType());
    }

    @Test
    void supporterCard_nullEffectText_shouldThrow() {
        assertThrows(NullPointerException.class,
                () -> new SupporterCard("id", "Name", "XY1", null));
    }

    // ── StadiumCard ──────────────────────────────────────────────────────────

    @Test
    void stadiumCard_shouldHaveStadiumTrainerType() {
        StadiumCard stadium = new StadiumCard(
                "xy1-t20", "Rough Seas", "XY1",
                "Heal 30 damage from each Water and Lightning Pokémon in play.");
        assertEquals(TrainerType.STADIUM, stadium.getTrainerType());
    }

    @Test
    void stadiumCard_nullEffectText_shouldThrow() {
        assertThrows(NullPointerException.class,
                () -> new StadiumCard("id", "Name", "XY1", null));
    }

    // ── PokemonToolCard ──────────────────────────────────────────────────────

    @Test
    void pokemonToolCard_shouldHavePokemonToolTrainerType() {
        PokemonToolCard tool = new PokemonToolCard(
                "xy1-t30", "Float Stone", "XY1",
                "The Retreat Cost of the Pokémon this card is attached to is 0.");
        assertEquals(TrainerType.POKEMON_TOOL, tool.getTrainerType());
    }

    @Test
    void pokemonToolCard_nullEffectText_shouldThrow() {
        assertThrows(NullPointerException.class,
                () -> new PokemonToolCard("id", "Name", "XY1", null));
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private ItemCard buildPotion() {
        return new ItemCard("xy1-t1", "Potion", "XY1",
                "Heal 30 damage from 1 of your Pokémon.");
    }

    private AceSpecCard buildAceSpec() {
        return new AceSpecCard("xy1-ace1", "Computer Search", "XY1",
                "Discard 2 cards from your hand. Search your deck for any card and put it into your hand.");
    }
}
