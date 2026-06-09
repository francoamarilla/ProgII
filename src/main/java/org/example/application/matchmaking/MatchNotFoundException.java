package org.example.application.matchmaking;

public class MatchNotFoundException extends RuntimeException {

    public MatchNotFoundException(String matchId) {
        super("Match not found: " + matchId);
    }
}
