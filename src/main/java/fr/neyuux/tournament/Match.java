package fr.neyuux.tournament;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    private String matchId;
    private Participant player1;
    private Participant player2;
    private int rounds;
    private int roundIndex;
    private String winnerNextMatchId;
    private String loserNextMatchId;
    private Participant winner;
    private Status status;

    public Match(String matchId, Participant player1, Participant player2, int rounds, int roundIndex) {
        this.matchId = matchId;
        this.player1 = player1;
        this.player2 = player2;
        this.rounds = rounds;
        this.roundIndex = roundIndex;
        this.winnerNextMatchId = null;
        this.loserNextMatchId = null;
        this.winner = null;
        this.status = Status.NOT_STARTED;
    }

    public Participant determineWinner() {
        // Placeholder : si player2 est null, player1 passe automatiquement
        if (player2 == null) {
            return player1;
        }
        // Exemple simpliste : choix aléatoire
        return (Math.random() < 0.5) ? player1 : player2;
    }
}
