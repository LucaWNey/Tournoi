package fr.neyuux.tournament;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participant {
    private UUID uuid;
    private String name;
    private List<UUID> teammates;
    private int seed;
    private int points;
    private int buchholz;
    private int wins;
    private int loses;
    private int ties;
    private int diff;
    private long timeToWin;

    public Participant(UUID uuid) {
        this.uuid = uuid;
        this.teammates = new ArrayList<>();
    }
}