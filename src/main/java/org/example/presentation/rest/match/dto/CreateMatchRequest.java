package org.example.presentation.rest.match.dto;

import java.util.List;

public record CreateMatchRequest(String playerId, List<String> deckCardIds) {}
