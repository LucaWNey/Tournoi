package fr.neyuux.tournament.items.menus.config.parameters;

import fr.neyuux.tournament.Tournament;
import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class TournamentParticipantsNumberItemStack extends CustomItemStack {

    private final Tournament tournament;
    private int value = 32;

    public TournamentParticipantsNumberItemStack(Tournament tournament) {
        super(Material.MINECART, TournamentPlugin.getInstance().getSelectedTournament().getParticipants(), "§7§lNombre de participants");

        this.tournament = tournament;
        if (tournament != null) this.value = tournament.getParticipants();

        this.setLore("§fPermet de modifier le nombre", "§fmaximal de participants au Tournoi.", "", "§bValeur : §7§l" + this.value, "", "§a>>Clique gauche pour ajouter", "§c>>Clique droit pour retirer", "§b>>Utiliser Shift pour modifier de 5");

        addItemInList(this);
    }

    @Override
    public void use(HumanEntity player, Event event) {
        InventoryClickEvent inventoryClickEvent = (InventoryClickEvent) event;
        int add = (inventoryClickEvent.isShiftClick() ? 5 : 1);
        Inventory inv = inventoryClickEvent.getInventory();
        int slot = getSlot(inv, this);

        if (inventoryClickEvent.isRightClick())
            add *= -1;

        this.value += add;

        if (value <= 1)
            return;

        if (tournament != null)
            tournament.addParticipants(add);

        this.setLoreLine(3, "§bValeur : §7§l" + this.value);
        this.setAmount(this.value);
        inv.setItem(slot, this);
    }

    public int getValue() {
        return value;
    }
}
