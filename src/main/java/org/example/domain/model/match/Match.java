package org.example.domain.model.match;

import java.time.LocalDateTime;
import java.util.Objects;

public class Match {

    private final String id;
    private final Player player1;
    private Player player2;
    private GameState state;
    private final LocalDateTime createdAt;

    public Match(String id, Player player1) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.player1 = Objects.requireNonNull(player1, "player1 cannot be null");
        this.player2 = null;
        this.state = GameState.WAITING;
        this.createdAt = LocalDateTime.now();
    }

    /** WAITING → SETUP: el segundo jugador se une a la partida. */
    public void join(Player player2) {
        if (state != GameState.WAITING)
            throw new IllegalStateException("Can only join a match in WAITING state, current: " + state);
        Objects.requireNonNull(player2, "player2 cannot be null");
        if (player1.playerId().equals(player2.playerId()))
            throw new IllegalArgumentException("Player cannot join their own match");
        this.player2 = player2;
        this.state = GameState.SETUP;
    }

    /** SETUP → ACTIVE: ambos jugadores completaron la preparación inicial. */
    public void startActive() {
        if (state != GameState.SETUP)
            throw new IllegalStateException("Match must be in SETUP to start, current: " + state);
        this.state = GameState.ACTIVE;
    }

    /** ACTIVE → FINISHED: se alcanzó una condición de victoria. */
    public void finish() {
        if (state != GameState.ACTIVE)
            throw new IllegalStateException("Match must be ACTIVE to finish, current: " + state);
        this.state = GameState.FINISHED;
    }

    public String getId() { return id; }
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public GameState getState() { return state; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
