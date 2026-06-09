package org.example.domain.model.match;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    // ── Player validation ────────────────────────────────────────────────────

    @Test
    void player_nullPlayerId_shouldThrow() {
        assertThrows(NullPointerException.class,
                () -> new Player(null, deck60()));
    }

    @Test
    void player_nullDeckCardIds_shouldThrow() {
        assertThrows(NullPointerException.class,
                () -> new Player("p1", null));
    }

    @Test
    void player_deckNot60Cards_shouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> new Player("p1", List.of("card1", "card2")));
    }

    @Test
    void player_exactly60Cards_shouldBeCreated() {
        Player p = new Player("p1", deck60());
        assertEquals("p1", p.playerId());
        assertEquals(60, p.deckCardIds().size());
    }

    @Test
    void player_deckIsImmutable() {
        Player p = new Player("p1", deck60());
        assertThrows(UnsupportedOperationException.class,
                () -> p.deckCardIds().add("extra"));
    }

    // ── Match creation ────────────────────────────────────────────────────────

    @Test
    void match_initialState_shouldBeWaiting() {
        Match match = buildMatch("m1", "p1");
        assertEquals(GameState.WAITING, match.getState());
    }

    @Test
    void match_shouldHaveIdAndPlayer1() {
        Match match = buildMatch("m1", "p1");
        assertEquals("m1", match.getId());
        assertEquals("p1", match.getPlayer1().playerId());
    }

    @Test
    void match_player2_shouldBeNullInitially() {
        Match match = buildMatch("m1", "p1");
        assertNull(match.getPlayer2());
    }

    @Test
    void match_createdAt_shouldNotBeNull() {
        assertNotNull(buildMatch("m1", "p1").getCreatedAt());
    }

    // ── join (WAITING → SETUP) ────────────────────────────────────────────────

    @Test
    void join_shouldTransitionToSetup() {
        Match match = buildMatch("m1", "p1");
        match.join(new Player("p2", deck60()));
        assertEquals(GameState.SETUP, match.getState());
        assertEquals("p2", match.getPlayer2().playerId());
    }

    @Test
    void join_samePlayer_shouldThrow() {
        Match match = buildMatch("m1", "p1");
        assertThrows(IllegalArgumentException.class,
                () -> match.join(new Player("p1", deck60())));
    }

    @Test
    void join_whenNotWaiting_shouldThrow() {
        Match match = buildMatch("m1", "p1");
        match.join(new Player("p2", deck60()));  // → SETUP
        assertThrows(IllegalStateException.class,
                () -> match.join(new Player("p3", deck60())));
    }

    // ── startActive (SETUP → ACTIVE) ─────────────────────────────────────────

    @Test
    void startActive_shouldTransitionToActive() {
        Match match = buildMatch("m1", "p1");
        match.join(new Player("p2", deck60()));
        match.startActive();
        assertEquals(GameState.ACTIVE, match.getState());
    }

    @Test
    void startActive_fromWaiting_shouldThrow() {
        Match match = buildMatch("m1", "p1");
        assertThrows(IllegalStateException.class, match::startActive);
    }

    // ── finish (ACTIVE → FINISHED) ────────────────────────────────────────────

    @Test
    void finish_shouldTransitionToFinished() {
        Match match = buildMatch("m1", "p1");
        match.join(new Player("p2", deck60()));
        match.startActive();
        match.finish();
        assertEquals(GameState.FINISHED, match.getState());
    }

    @Test
    void finish_fromSetup_shouldThrow() {
        Match match = buildMatch("m1", "p1");
        match.join(new Player("p2", deck60()));
        assertThrows(IllegalStateException.class, match::finish);
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private Match buildMatch(String id, String playerId) {
        return new Match(id, new Player(playerId, deck60()));
    }

    private List<String> deck60() {
        return Collections.nCopies(60, "card-001");
    }
}
