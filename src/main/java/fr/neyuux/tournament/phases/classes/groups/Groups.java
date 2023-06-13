package fr.neyuux.tournament.phases.classes.groups;

import fr.neyuux.tournament.Match;
import fr.neyuux.tournament.Tournament;
import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.enums.PhaseType;
import fr.neyuux.tournament.phases.Phase;
import fr.neyuux.tournament.phases.Seed;
import org.bukkit.Bukkit;

import java.util.*;

public class Groups extends Phase {

    private static final int[] SIZE_PRIORITIES = new int[]{4, 3, 6, 5, 7, 8};

    private boolean isGoings_And_Comings = true;

    private final List<Group> groups = new ArrayList<>();
    private final HashMap<Integer, List<Match>> matches = new HashMap<>();

    public Groups(String name, int participants) {
        super(name, PhaseType.GROUPS, participants);

        this.participants = participants;

        this.createGroups();
    }

    public Groups(int participants) {
        super(PhaseType.GROUPS.getName(), PhaseType.GROUPS, participants);

        this.participants = participants;

        this.createGroups();
    }


    @Override
    public PhaseType getType() {
        return PhaseType.GROUPS;
    }

    @Override
    public HashMap<Integer, List<Match>> getMatches() {
        return this.matches;
    }

    public void createGroups() {
        Integer groupSize = this.selectGroupSize();

        if (groupSize > 8) throw new IllegalArgumentException("groupSize must be < 9. actual : "  + groupSize);

        this.matches.clear();
        this.groups.clear();

        HashMap<Integer, List<Integer>> pools = new HashMap<>();
        HashMap<Integer, List<Integer>> groups = new HashMap<>();
        int ngroups = (int) Math.ceil(this.participants / groupSize.floatValue());
        int pool = 0;

        for (int i = 1; i <= this.participants; i++) {
            if ((i - 1) % ngroups == 0) {
                pool++;
                pools.put(pool, new ArrayList<>());
            }

            pools.get(pool).add(i);
        }

        Bukkit.getLogger().info("Création de groupes... pools : " + pools);

        for (int i = 1; i <= ngroups; i++) {
            groups.put(i, new ArrayList<>());

            List<Integer> seeds = groups.get(i);

            pools.values().forEach(integers -> {
                int j = integers.get(new Random().nextInt(integers.size()));
                seeds.add(j);
                integers.remove((Object)j);
            });
        }

        for (Map.Entry<Integer, List<Integer>> entry : groups.entrySet()) {
            List<Seed> seeds = new ArrayList<>();

            entry.getValue().forEach(seed -> seeds.add(new Seed(seed)));

            Group g = new Group("Groupe " + entry.getKey(), seeds, this.isGoings_And_Comings);

            this.groups.add(g);
            g.getMatches().forEach((day, dayMatches) -> {
                if (!Groups.this.matches.containsKey(day))
                    Groups.this.matches.put(day, new ArrayList<>());

                Groups.this.matches.get(day).addAll(dayMatches);
            });
        }

        Tournament tournament = TournamentPlugin.getInstance().getSelectedTournament();

        tournament.setInConfig("phases." + this.name + ".groups", groups);
        tournament.setInConfig("matches." + this.getName(), this.getMatches());
    }

    private Integer selectGroupSize() {
        int min = 4;

        for (int sizePriority : SIZE_PRIORITIES) {
            if (participants % sizePriority == 0)
                return sizePriority;
            
            else if (participants % sizePriority < participants % min)
                min = sizePriority;

        }
        return min;
    }

    public boolean isGoings_And_Comings() {
        return isGoings_And_Comings;
    }

    public void setGoings_And_Comings(boolean goings_And_Comings) {
        isGoings_And_Comings = goings_And_Comings;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
