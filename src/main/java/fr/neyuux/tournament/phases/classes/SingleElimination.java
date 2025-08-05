package fr.neyuux.tournament.phases.classes;

import fr.neyuux.tournament.*;
import fr.neyuux.tournament.phases.Phase;
import fr.neyuux.tournament.phases.PhaseType;
import fr.neyuux.tournament.utils.Utils;
import lombok.Setter;

import java.util.*;

public class SingleElimination extends Phase {


    @Setter
    private boolean thirdPlaceMatch;

    public SingleElimination(int index, Status status, String tournamentId, int numStarting, int numAdvancing, int matchRounds, List<Participant> entrants, boolean thirdPlaceMatch) {
        super(index, status, tournamentId, PhaseType.SINGLE_ELIMINATION, null, numAdvancing, numStarting, matchRounds, entrants, new ArrayList<>());
        this.thirdPlaceMatch = thirdPlaceMatch;
    }

    public SingleElimination(int index, Status status, String tournamentId, int numStarting, int numAdvancing, int matchRounds, List<Participant> entrants) {
        super(index, status, tournamentId, PhaseType.SINGLE_ELIMINATION, null, numAdvancing, numStarting, matchRounds, entrants, new ArrayList<>());
        this.thirdPlaceMatch = false;
    }

    @Override
    public void generateMatchs() {
        int size = Utils.getNext2Power(this.getNumStarting());

        int totalRounds = Integer.numberOfTrailingZeros(size);

        List<Participant> sorted = new ArrayList<>(this.getEntrants());
        sorted.sort(Comparator.comparingInt(Participant::getSeed));

        List<Integer> order = Utils.seedOrder(size);
        Participant[] bracket = new Participant[size];

        for (int i = 0; i < size; i++) {
            int seedNum = order.get(i);

            if (seedNum <= sorted.size()) {
                bracket[i] = sorted.get(seedNum - 1);
            } else {
                bracket[i] = null;
            }
        }

        List<Match> prevRound = new ArrayList<>();

        for (int i = 0; i < size; i += 2) {
            Participant p1 = bracket[i];
            Participant p2 = bracket[i + 1];
            String matchId = UUID.randomUUID().toString();

            Match match = new Match(matchId, p1, p2, this.getMatchRounds(), 1);

            this.addMatch(match);
            prevRound.add(match);
        }

        for (int roundIdx = 2; roundIdx <= totalRounds; roundIdx++) {
            List<Match> currentRound = new ArrayList<>();

            for (int i = 0; i < prevRound.size(); i += 2) {
                String matchId = UUID.randomUUID().toString();

                Match match = new Match(matchId, null, null, this.getMatchRounds(), roundIdx);

                this.addMatch(match);
                prevRound.get(i).setWinnerNextMatchId(matchId);
                prevRound.get(i + 1).setWinnerNextMatchId(matchId);
                currentRound.add(match);
            }
            prevRound = currentRound;
        }

        if (this.thirdPlaceMatch) {
            String thirdPlaceMatchId = UUID.randomUUID().toString();
            Match thirdPlaceMatch = new Match(thirdPlaceMatchId, null, null, this.getMatchRounds(), totalRounds);

            this.getMatches()
                    .stream()
                    .filter(match -> match.getRoundIndex() == totalRounds - 1)
                    .forEach(match -> match.setLoserNextMatchId(thirdPlaceMatchId));

            this.addMatch(thirdPlaceMatch);
        }
    }

    public boolean hasThirdPlaceMatch() {
        return thirdPlaceMatch;
    }


    @Override
    public void onEndMatch(String matchId) {
        if (this.getMatches().stream().allMatch(match -> match.getStatus() == Status.FINISHED))
            this.end();
    }
}