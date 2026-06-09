package org.example.application.matchmaking;

import org.example.domain.model.match.GameState;
import org.example.domain.model.match.Match;
import org.example.domain.model.match.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MatchmakingService {

    private final Map<String, Match> matches = new ConcurrentHashMap<>();

    public Match createMatch(Player player1) {
        String id = UUID.randomUUID().toString();
        Match match = new Match(id, player1);
        matches.put(id, match);
        return match;
    }

    public Match joinMatch(String matchId, Player player2) {
        Match match = getMatch(matchId);
        match.join(player2);
        return match;
    }

    public List<Match> listWaitingMatches() {
        return matches.values().stream()
                .filter(m -> m.getState() == GameState.WAITING)
                .toList();
    }

    public Match getMatch(String matchId) {
        Match match = matches.get(matchId);
        if (match == null) throw new MatchNotFoundException(matchId);
        return match;
    }

    public Match startActivePhase(String matchId) {
        Match match = getMatch(matchId);
        match.startActive();
        return match;
    }

    public Match finishMatch(String matchId) {
        Match match = getMatch(matchId);
        match.finish();
        return match;
    }
}
