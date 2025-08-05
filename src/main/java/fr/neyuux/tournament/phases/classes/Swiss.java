package fr.neyuux.tournament.phases.classes;

import fr.neyuux.tournament.*;
import fr.neyuux.tournament.phases.Phase;
import fr.neyuux.tournament.phases.PhaseType;
import fr.neyuux.tournament.utils.Utils;
import lombok.*;

import java.util.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Swiss extends Phase {

    private int pointsPerWin;
    private int pointsPerTie;
    private int pointsPerLoss;
    private int numberOfWinsToAdvance;
    private int totalRounds;

    private int round;

    public Swiss(int index,
                 Status status,
                 String tournamentsId,
                 List<Comparator<Participant>> tieBreakers,
                 int numAdvancing,
                 int numStarting,
                 int matchRounds,
                 List<Participant> entrants,
                 int pointsPerWin,
                 int pointsPerTie,
                 int pointsPerLoss,
                 int numberOfWinsToAdvance,
                 int totalRounds,
                 int round) {

        super(index,
                status,
                tournamentsId,
                PhaseType.SWISS_SYSTEM,
                tieBreakers,
                numAdvancing,
                numStarting,
                matchRounds,
                entrants,
                new ArrayList<>());

        this.pointsPerWin = pointsPerWin;
        this.pointsPerTie = pointsPerTie;
        this.pointsPerLoss = pointsPerLoss;
        this.numberOfWinsToAdvance = numberOfWinsToAdvance;
        this.totalRounds = totalRounds;
        this.round = round;
    }

    public Swiss(int index,
                 Status status,
                 String tournamentsId,
                 List<Comparator<Participant>> tieBreakers,
                 int numAdvancing,
                 int numStarting,
                 int matchRounds,
                 List<Participant> entrants) {

        super(index,
                status,
                tournamentsId,
                PhaseType.SWISS_SYSTEM,
                tieBreakers,
                numAdvancing,
                numStarting,
                matchRounds,
                entrants,
                new ArrayList<>());

        this.pointsPerWin = 2;
        this.pointsPerTie = 1;
        this.pointsPerLoss = 0;
        this.numberOfWinsToAdvance = 0;
        this.totalRounds = 5;
        this.round = 1;
    }


    @Override
    public void generateMatchs() {

        if (round == 1) {

            int size = Utils.getNext2Power(this.getNumStarting());

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

            for (int i = 0; i < size; i += 2) {
                Participant p1 = bracket[i];
                Participant p2 = bracket[i + 1];
                String matchId = UUID.randomUUID().toString();

                Match match = new Match(matchId, p1, p2, this.getMatchRounds(), 1);

                this.addMatch(match);
            }
        } else {

            List<Participant> sorted = new ArrayList<>(this.getEntrants());
            sorted.sort(Comparator.comparingInt(Participant::getPoints).reversed());

            if (this.numberOfWinsToAdvance != 0) {
                for (Participant participant : this.getEntrants()) {

                    if (participant.getWins() >= this.numberOfWinsToAdvance) {
                        this.getWinners().add(participant);
                        sorted.remove(participant);

                    } else if (participant.getLoses() > this.numberOfWinsToAdvance) {
                        sorted.remove(participant);
                    }
                }
            }

            if (sorted.isEmpty() || this.round == this.totalRounds + 1) {
                this.end();
                return;
            }

            List<List<Participant>> groupsByScore = new ArrayList<>();
            groupsByScore.add(new ArrayList<>());
            int lastScore = sorted.get(0).getPoints();

            for (Participant p : sorted) {
                if (lastScore != p.getPoints())
                    groupsByScore.add(new ArrayList<>());

                groupsByScore.get(groupsByScore.size() - 1).add(p);
                lastScore = p.getPoints();
            }

            groupsByScore.forEach(Collections::shuffle);

            for (int i = 0; i < groupsByScore.size(); i++) {
                List<Participant> group = groupsByScore.get(i);

                if (group.size() % 2 != 0) {

                    if (i == groupsByScore.size() - 1) {
                        Participant best = this.getBest(group);
                        this.addMatch(new Match(UUID.randomUUID().toString(), best, null, this.getMatchRounds(), this.round));
                        group.remove(best);
                    }

                    List<Participant> nextGroup = groupsByScore.get(i + 1);
                    Participant bestPrev = this.getBest(nextGroup);

                    nextGroup.remove(bestPrev);
                    group.add(bestPrev);
                }


                for (int j = 0; j < group.size(); j+= 2) {
                    Participant p1 = group.get(j);
                    Participant p2 = group.get(j + 1);

                    Match match = new Match(UUID.randomUUID().toString(), p1, p2, this.getMatchRounds(), this.round);

                    this.addMatch(match);
                }
            }

        }
        this.round++;
    }


    private Participant floatUpCompare(Participant p1, Participant p2) {
        if (p1.getBuchholz() != p2.getBuchholz()) {
            return (p1.getBuchholz() > p2.getBuchholz() ? p1 : p2);

        } else if (p1.getWins() != p2.getWins()) {
            return (p1.getWins() > p2.getWins() ? p1 : p2);

        } else if (p1.getDiff() != p2.getDiff()) {
            return (p1.getDiff() > p2.getDiff() ? p1 : p2);

        } else if (p1.getTimeToWin() != p2.getTimeToWin()) {
            return (p1.getTimeToWin() > p2.getTimeToWin() ? p1 : p2);

        } else {
            return Utils.selectRandomParticipant(Arrays.asList(p1, p2));
        }
    }

    private Participant getBest(List<Participant> list) {
        List<Participant> comparating = new ArrayList<>(list);

        while (comparating.size() != 1) {
            comparating.remove((this.floatUpCompare(comparating.get(0), comparating.get(1)).equals(comparating.get(0)) ? comparating.get(1) : comparating.get(0)));
        }

        return comparating.get(0);
    }

    @Override
    public void onEndMatch(String matchId) {
         if (this.getMatches().stream().allMatch(match -> match.getStatus() == Status.FINISHED))
             this.generateMatchs();
    }
}
