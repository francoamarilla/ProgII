package org.example.presentation.rest.match;

import org.example.application.matchmaking.MatchNotFoundException;
import org.example.application.matchmaking.MatchmakingService;
import org.example.domain.model.match.Player;
import org.example.presentation.rest.match.dto.CreateMatchRequest;
import org.example.presentation.rest.match.dto.JoinMatchRequest;
import org.example.presentation.rest.match.dto.MatchResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchmakingService matchmakingService;

    public MatchController(MatchmakingService matchmakingService) {
        this.matchmakingService = matchmakingService;
    }

    @PostMapping
    public ResponseEntity<MatchResponse> createMatch(@RequestBody CreateMatchRequest request) {
        Player player1 = new Player(request.playerId(), request.deckCardIds());
        MatchResponse response = MatchResponse.from(matchmakingService.createMatch(player1));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<MatchResponse> listWaitingMatches() {
        return matchmakingService.listWaitingMatches().stream()
                .map(MatchResponse::from)
                .toList();
    }

    @PostMapping("/{id}/join")
    public MatchResponse joinMatch(@PathVariable String id, @RequestBody JoinMatchRequest request) {
        Player player2 = new Player(request.playerId(), request.deckCardIds());
        return MatchResponse.from(matchmakingService.joinMatch(id, player2));
    }

    @GetMapping("/{id}")
    public MatchResponse getMatch(@PathVariable String id) {
        return MatchResponse.from(matchmakingService.getMatch(id));
    }

    @PostMapping("/{id}/start")
    public MatchResponse startMatch(@PathVariable String id) {
        return MatchResponse.from(matchmakingService.startActivePhase(id));
    }

    @PostMapping("/{id}/finish")
    public MatchResponse finishMatch(@PathVariable String id) {
        return MatchResponse.from(matchmakingService.finishMatch(id));
    }

    @ExceptionHandler(MatchNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(MatchNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleConflict(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }
}
