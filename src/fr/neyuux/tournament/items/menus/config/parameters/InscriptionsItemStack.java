package fr.neyuux.tournament.items.menus.config.parameters;

import fr.neyuux.tournament.Tournament;
import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InscriptionsItemStack extends CustomItemStack {

    private final Tournament tournament;

    public InscriptionsItemStack() {
        super(Material.PAINTING, 1, "§6§lInscriptions");

        this.tournament = TournamentPlugin.getInstance().getSelectedTournament();

        this.setLore("§fPermet d'ouvrir ou de fermer", "§fles inscriptions au Tournoi.", "", "§bValeur : §7§l" + getStringFromBoolean(tournament.isInscriptionsOpen()), "", "§b>>Clique pour modifier");

        addItemInList(this);
    }

    @Override
    public void use(HumanEntity player, Event event) {
        InventoryClickEvent inventoryClickEvent = (InventoryClickEvent) event;
        Inventory inv = inventoryClickEvent.getInventory();
        int slot = getSlot(inv, this);

        tournament.setInscriptionsOpen(!tournament.isInscriptionsOpen());

        this.setLoreLine(3, "§bValeur : " + getStringFromBoolean(tournament.isInscriptionsOpen()));
        inv.setItem(slot, this);
    }


    private static String getStringFromBoolean(boolean b) {
        return (b ? "§aOuvertes" : "§cFermées");
    }
}
