package fr.neyuux.tournament.phases;

import java.util.Collection;

public class Seed {

    private final int i;

    public Seed (int i) {

        this.i = i;
    }

    public int getNumber() {
        return i;
    }


    public static Seed getByNumber(Collection<Seed> collection, int i) {
        for (Seed seed : collection) {
            if (seed.getNumber() == i)
                return seed;
        }
        return null;
    }
}
