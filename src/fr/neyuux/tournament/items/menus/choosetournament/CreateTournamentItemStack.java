package fr.neyuux.tournament.items.menus.choosetournament;

import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class CreateTournamentItemStack extends CustomItemStack {

    public CreateTournamentItemStack() {
        super(Material.EYE_OF_ENDER, 1, "§a§lCréer un tournoi");

        this.setLore("§fPermet de créer un", "§fnouveau tournoi.", "", "§b>>Clique pour utiliser");
    }

    @Override
    public void use(HumanEntity player, Event event) {
        TournamentPlugin.openRenameTournamentInventory((Player)player);
    }

}
