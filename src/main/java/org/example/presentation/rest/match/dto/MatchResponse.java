package org.example.presentation.rest.match.dto;

import org.example.domain.model.match.Match;

public record MatchResponse(
        String id,
        String state,
        String player1Id,
        String player2Id,
        String createdAt
) {
    public static MatchResponse from(Match match) {
        return new MatchResponse(
                match.getId(),
                match.getState().name(),
                match.getPlayer1().playerId(),
                match.getPlayer2() != null ? match.getPlayer2().playerId() : null,
                match.getCreatedAt().toString()
        );
    }
}
