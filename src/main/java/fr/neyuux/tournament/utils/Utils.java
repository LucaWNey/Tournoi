package fr.neyuux.tournament.utils;

import fr.neyuux.tournament.Participant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    private static final Random random = new Random();

    // generate the seed list for a n-sized bracket (1-based)
    public static List<Integer> seedOrder(int n) {

        if (n == 1) {
            List<Integer> base = new ArrayList<>();
            base.add(1);
            return base;
        }

        int half = n / 2;
        List<Integer> prev = seedOrder(half);
        List<Integer> result = new ArrayList<>();

        for (int seed : prev) {
            result.add(seed);
            result.add(n + 1 - seed);
        }

        return result;
    }

    public static int getNext2Power(int n) {
        int size = 1;

        while (size < n) {
            size <<= 1;
        }
        return size;
    }

    public static int generateRandomNumber(int min, int max) {
        return random.nextInt(max) + min + 1;
    }

    public static Participant selectRandomParticipant(List<Participant> list) {
        return list.get(generateRandomNumber(0, list.size() - 1));
    }
}
