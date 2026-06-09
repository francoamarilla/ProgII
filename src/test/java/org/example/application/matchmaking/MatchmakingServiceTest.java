package org.example.application.matchmaking;

import org.example.domain.model.match.GameState;
import org.example.domain.model.match.Match;
import org.example.domain.model.match.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchmakingServiceTest {

    private MatchmakingService service;

    @BeforeEach
    void setUp() {
        service = new MatchmakingService();
    }

    @Test
    void createMatch_shouldReturnWaitingMatch() {
        Match match = service.createMatch(player("p1"));
        assertEquals(GameState.WAITING, match.getState());
        assertEquals("p1", match.getPlayer1().playerId());
        assertNotNull(match.getId());
    }

    @Test
    void createMatch_shouldBeListedAsWaiting() {
        service.createMatch(player("p1"));
        assertEquals(1, service.listWaitingMatches().size());
    }

    @Test
    void listWaitingMatches_shouldExcludeSetupMatches() {
        Match m1 = service.createMatch(player("p1"));
        service.createMatch(player("p3"));  // second WAITING match

        service.joinMatch(m1.getId(), player("p2"));  // m1 → SETUP

        List<Match> waiting = service.listWaitingMatches();
        assertEquals(1, waiting.size());
        assertEquals(GameState.WAITING, waiting.get(0).getState());
    }

    @Test
    void joinMatch_shouldTransitionToSetup() {
        Match match = service.createMatch(player("p1"));
        Match joined = service.joinMatch(match.getId(), player("p2"));
        assertEquals(GameState.SETUP, joined.getState());
        assertEquals("p2", joined.getPlayer2().playerId());
    }

    @Test
    void listWaitingMatches_afterJoin_shouldNotContainJoinedMatch() {
        Match match = service.createMatch(player("p1"));
        service.joinMatch(match.getId(), player("p2"));
        assertTrue(service.listWaitingMatches().isEmpty());
    }

    @Test
    void getMatch_unknownId_shouldThrow() {
        assertThrows(MatchNotFoundException.class,
                () -> service.getMatch("nonexistent-id"));
    }

    @Test
    void startActivePhase_shouldTransitionToActive() {
        Match match = service.createMatch(player("p1"));
        service.joinMatch(match.getId(), player("p2"));
        Match active = service.startActivePhase(match.getId());
        assertEquals(GameState.ACTIVE, active.getState());
    }

    @Test
    void finishMatch_shouldTransitionToFinished() {
        Match match = service.createMatch(player("p1"));
        service.joinMatch(match.getId(), player("p2"));
        service.startActivePhase(match.getId());
        Match finished = service.finishMatch(match.getId());
        assertEquals(GameState.FINISHED, finished.getState());
    }

    @Test
    void getMatch_shouldReturnSameInstance() {
        Match created = service.createMatch(player("p1"));
        Match retrieved = service.getMatch(created.getId());
        assertSame(created, retrieved);
    }

    @Test
    void createMatch_multipleMatches_shouldHaveUniqueIds() {
        Match m1 = service.createMatch(player("p1"));
        Match m2 = service.createMatch(player("p2"));
        assertNotEquals(m1.getId(), m2.getId());
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private Player player(String playerId) {
        return new Player(playerId, Collections.nCopies(60, "card-001"));
    }
}
