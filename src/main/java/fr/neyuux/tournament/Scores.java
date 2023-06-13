package fr.neyuux.tournament;

public class Scores {

    private int played = 0, victories = 0, defeats = 0, wonRounds = 0, lostRounds = 0, roundAverage = 0 ;


    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public int getDefeats() {
        return defeats;
    }

    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }

    public int getWonRounds() {
        return wonRounds;
    }

    public void setWonRounds(int wonRounds) {
        this.wonRounds = wonRounds;
    }

    public int getLostRounds() {
        return lostRounds;
    }

    public void setLostRounds(int lostRounds) {
        this.lostRounds = lostRounds;
    }

    public int getRoundAverage() {
        return roundAverage;
    }

    public void setRoundAverage(int roundAverage) {
        this.roundAverage = roundAverage;
    }
}
