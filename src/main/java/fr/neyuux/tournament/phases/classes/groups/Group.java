package fr.neyuux.tournament.phases.classes.groups;

import fr.neyuux.tournament.Match;
import fr.neyuux.tournament.Scores;
import fr.neyuux.tournament.phases.Seed;
import org.bukkit.Bukkit;

import java.util.*;

public class Group {

    private final String name;
    private final HashMap<Seed, Scores> scores = new HashMap<>();
    private final HashMap<Integer, List<Match>> matchesPerDay = new HashMap<>();

    public Group(String name, List<Seed> seeds, boolean goings_And_Comings) {
        this.name = name;
        for (Seed seed : seeds) scores.put(seed, new Scores());

        int size = seeds.size();

        this.generateMatches(size);
        if (goings_And_Comings) {
            this.generateMatches(size);
        }

        Bukkit.getLogger().info("Creation du groupe " + name + " : " + seeds);
    }

    public String getName() {
        return name;
    }

    public List<Seed> getSeeds() {
        return new ArrayList<>(scores.keySet());
    }

    private List<Integer> getSeedsNumbers() {
        List<Integer> l = new ArrayList<>();
        this.getSeeds().forEach(seed -> l.add(seed.getNumber()));
        return l;
    }

    public HashMap<Integer, List<Match>> getMatches() {
        return matchesPerDay;
    }

    private void generateMatches(int size) {
        if (size % 2 == 0)
            this.generateMatchesPair(size);
        else
            this.generateMatchesImpair(size);
    }

    private void generateMatchesPair(int groupSize) {
        List<Integer> seedNumbers = this.getSeedsNumbers();
        List<Integer> removed = new ArrayList<>();

        int days = 1;
        while(days != (groupSize % 2 == 0 ? groupSize : groupSize + 1)) {
            List<Integer> dayPlayers = new ArrayList<>(seedNumbers);

            matchesPerDay.put(days, new ArrayList<>());

            if (groupSize % 2 != 0) {
                Integer toRemove = dayPlayers.get(new Random().nextInt(dayPlayers.size()));

                while (removed.contains(toRemove))
                    toRemove = dayPlayers.get(new Random().nextInt(dayPlayers.size()));

                dayPlayers.remove(toRemove);
                removed.add(toRemove);
            }

            while (!dayPlayers.isEmpty()) {
                Integer player1 = getFromPrevious(days, matchesPerDay.get(days).size() + 1, seedNumbers, dayPlayers, true);
                if (dayPlayers.size() == groupSize) player1 = dayPlayers.get(0);

                Integer player2 = getFromPrevious(days, matchesPerDay.get(days).size() + 1, seedNumbers, dayPlayers, false);

                Match match = this.createMatch(player1, player2);

                matchesPerDay.get(days).add(match);
                dayPlayers.remove(player2);
                dayPlayers.remove(player1);
            }
            days++;
        }
    }

    private void generateMatchesImpair(int groupSize) {
        List<Match> remainingMatches = new ArrayList<>();
        List<Integer> players = this.getSeedsNumbers();
        HashMap<Integer, Integer> removed = new HashMap<>();
        int matchsPerDay = (groupSize % 2 == 0 ? groupSize / 2 : (groupSize - 1) / 2);

        for (Integer player1 : players) {
            List<Integer> clone  = new ArrayList<>(players);
            Collections.reverse(clone);
            for (Integer player2 : clone) {
                Match match = this.createMatch(player1, player2);

                if (!remainingMatches.contains(match) && !remainingMatches.contains(match) && !player1.equals(player2))
                    remainingMatches.add(match);
            }
        }

        if (groupSize % 2 != 0) {
            int indexremove = 0;
            int idays = 1;

            while (removed.size() != groupSize) {
                removed.put(idays, players.get(indexremove));

                if (indexremove <= 1) {
                    List<Integer> reverted = new ArrayList<>(players);

                    Collections.reverse(reverted);

                    if (idays + 1 < (groupSize + 1) / 2 + 1)
                        //noinspection OptionalGetWithoutIsPresent
                        indexremove = players.indexOf(reverted.stream().filter(integer -> integer % 2 == 0).findFirst().get());
                    else
                        indexremove = players.indexOf(reverted.get(0));

                } else {
                    indexremove = indexremove - 2;
                }

                idays++;
            }
        }

        int days = 1;
        while (days <= (groupSize % 2 == 0 ? groupSize - 1 : groupSize)) {
            List<Integer> dayPlayers = new ArrayList<>();
            List<Match> dayMatches = new ArrayList<>();

            if (removed.get(days) != null) {
                dayPlayers.add(removed.get(days));
            }

            for (int i = 0; i < matchsPerDay; i++) {
                for (Match match : remainingMatches) {
                    Integer player1 = match.getSeed1().getNumber();
                    Integer player2 = match.getSeed2().getNumber();

                    if (dayPlayers.contains(player1) || dayPlayers.contains(player2))
                        continue;

                    dayPlayers.add(player1);
                    dayPlayers.add(player2);
                    dayMatches.add(match);
                    break;
                }
            }

            remainingMatches.removeAll(dayMatches);
            matchesPerDay.put(days, dayMatches);
            days++;
        }
    }

    private Integer getFromPrevious(Integer days, Integer match, List<Integer> players, List<Integer> dayPlayers, boolean first) {
        if ((match == 1 && first) || matchesPerDay.size() == 1)
            return dayPlayers.get((first ? 0 : dayPlayers.size() - 1));

        int previous = (first ? matchesPerDay.get(days - 1).get(match - 1).getSeed1().getNumber() : matchesPerDay.get(days - 1).get(match - 1).getSeed2().getNumber());
        int i = players.indexOf(previous) - 1;

        if (i < 0) {
            i = players.size() - 1;
        }

        while (!dayPlayers.contains(players.get(i))) {
            i--;
            if (i < 0) {
                i = players.size() - 1;
            }
        }

        return players.get(i);
    }

    private Match createMatch(int player1, int player2) {
        return new Match(Seed.getByNumber(this.getSeeds(), player1), Seed.getByNumber(this.getSeeds(), player2), 1);
    }


    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", scores=" + scores +
                ", matchesPerDay=" + matchesPerDay +
                '}';
    }
}
