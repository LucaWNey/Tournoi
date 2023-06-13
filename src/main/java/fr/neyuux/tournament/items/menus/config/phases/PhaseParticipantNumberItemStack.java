package fr.neyuux.tournament.items.menus.config.phases;

import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class PhaseParticipantNumberItemStack extends CustomItemStack {

    private int participants;

    public PhaseParticipantNumberItemStack(int participants) {
        super(Material.MINECART, participants, "§7Nombre de participants");
        this.participants = participants;

        this.setLore("§fChange le nombre de participants de la phase.", "", "§bValeur : §7§l" + participants, "", "§a>>Clique gauche pour ajouter", "§c>>Clique droit pour retirer", "§b>>Shift+Clic pour modifier de 5");

        addItemInList(this);
    }

    @Override
    public void use(HumanEntity player, Event event) {
        InventoryClickEvent inventoryClickEvent = (InventoryClickEvent) event;
        int add = (inventoryClickEvent.isShiftClick() ? 5 : 1);
        Inventory inv = inventoryClickEvent.getInventory();
        int slot = CustomItemStack.getSlot(inv, this);

        if (inventoryClickEvent.isRightClick())
            add *= -1;

        this.participants += add;

        this.setLoreLine(2, "§bValeur : §7§l" + participants);
        inv.setItem(slot, this);
    }

    public int getParticipants() {
        return participants;
    }
}
