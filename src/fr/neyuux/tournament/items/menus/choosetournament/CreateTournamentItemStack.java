package fr.neyuux.tournament.items.menus.choosetournament;

import fr.neyuux.tournament.Tournament;
import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.utils.Anvil;
import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.UUID;

public class CreateTournamentItemStack extends CustomItemStack {

    private static final ArrayList<HumanEntity> renamingPlayer = new ArrayList<>();

    public CreateTournamentItemStack() {
        super(Material.EYE_OF_ENDER, 1, "§a§lCréer un tournoi");

        this.setLore("§fPermet de créer un", "§fnouveau tournoi.", "", "§b>>Clique pour utiliser");

        addItemInList(this);
    }

    @Override
    public void use(HumanEntity player, Event event) {
        Tournament tournament = new Tournament(UUID.randomUUID().toString(), player.getName());
        TournamentPlugin main = TournamentPlugin.getInstance();

        player.closeInventory();
        main.setSelectedTournament(tournament);

        TournamentPlugin.sendTitle((Player) player, "§a§lNommez ce Tournoi", "§fRenommez le papier dans l'enclume avec le nom du Tournoi.", 2, 40, 10);

        Bukkit.getScheduler().runTaskLater(main, () -> {
            ((Player) player).setLevel(4);
            Inventory anvilInv = Anvil.openAnvilInventory(((Player) player).getPlayer());
            anvilInv.setItem(0, new CustomItemStack(Material.PAPER, 1, "Nouveau Tournoi " + (TournamentPlugin.getLoadedTournaments().size() + 1)).setLore("§0" + TournamentPlugin.getLoadedTournaments().indexOf(tournament)));
            renamingPlayer.add(player);
            }, 22L);
    }

    public static ArrayList<HumanEntity> getRenamingPlayers() {
        return renamingPlayer;
    }
}
