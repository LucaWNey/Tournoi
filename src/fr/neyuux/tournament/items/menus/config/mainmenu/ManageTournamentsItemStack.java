package fr.neyuux.tournament.items.menus.config.mainmenu;

import fr.neyuux.tournament.inventories.TournamentSelectionInv;
import fr.neyuux.tournament.inventories.config.TournamentConfigurationInv;
import fr.neyuux.tournament.items.menus.ReturnArrowItemStack;
import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;

public class ManageTournamentsItemStack extends CustomItemStack {

    public ManageTournamentsItemStack() {
        super(Material.BARRIER, 1, "§a§lGérer les Tournois");

        this.setLore("§fRouvre le menu de", "§fsélection des tournois.");

        addItemInList(this);
    }

    @Override
    public void use(HumanEntity player, Event event) {
        TournamentSelectionInv tournamentSelectionInv = new TournamentSelectionInv();
        tournamentSelectionInv.open(player);
        player.getOpenInventory().getTopInventory().setItem(tournamentSelectionInv.getSize() - 1, new ReturnArrowItemStack(new TournamentConfigurationInv()));
    }
}
