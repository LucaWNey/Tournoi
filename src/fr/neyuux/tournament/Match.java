package fr.neyuux.tournament;

import fr.neyuux.tournament.phases.Seed;

public class Match {

    private final Seed seed1;
    private final Seed seed2;
    private final int bestof;

    public Match (Seed seed1, Seed seed2, int bestof) {
        this.seed1 = seed1;
        this.seed2 = seed2;
        this.bestof = bestof;
    }

    public Seed getSeed1() {
        return seed1;
    }

    public Seed getSeed2() {
        return seed2;
    }

    public int getBO() {
        return bestof;
    }
}
