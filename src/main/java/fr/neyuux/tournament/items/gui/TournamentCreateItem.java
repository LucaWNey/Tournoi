package fr.neyuux.tournament.items.gui;

import fr.neyuux.tournament.Tournament;
import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerDropItemEvent;

public class TournamentCreateItem extends CustomItemStack {

    public TournamentCreateItem() {
        super(Material.SIGN, 1, "§a§lCréer un Tournoi");

    }

    @Override
    public void use(HumanEntity player, Event event) {

        TournamentPlugin.INSTANCE.setSelectedTournament(tournament);

        super.use(player, event);
    }

    @Override
    public void drop(Player player, PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
