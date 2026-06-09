package org.example.presentation.rest.match.dto;

import java.util.List;

public record JoinMatchRequest(String playerId, List<String> deckCardIds) {}
