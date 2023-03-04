package fr.neyuux.tournament.items.menus.config.phases;

import fr.neyuux.tournament.enums.PhaseType;
import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class PhaseTypeModifierItemStack extends CustomItemStack {

    private PhaseType phaseType;

    public PhaseTypeModifierItemStack(PhaseType type) {
        super(Material.STAINED_CLAY, 1, "§cType de la phase");
        this.phaseType = type;

        this.setLore("§fChange le type de phase.", "", "§bValeur : §c§l" + phaseType.getName(), "", "§7>>Clique pour modifier");

        addItemInList(this);
    }

    @Override
    public void use(HumanEntity player, Event event) {
        Inventory inv = ((InventoryClickEvent) event).getInventory();
        int slot = CustomItemStack.getSlot(inv, this);
        int i = this.phaseType.ordinal() + 1;
        if (PhaseType.values().length - 1 <= i)
            i = 0;

        this.phaseType = PhaseType.values()[i];
        this.setLoreLine(2, "§bValeur : §c§l" + phaseType.getName());

        inv.setItem(slot, this);
    }

    public PhaseType getPhaseType() {
        return phaseType;
    }
}
