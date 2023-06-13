package fr.neyuux.tournament.items.menus.config.phases;

import fr.neyuux.tournament.inventories.config.phases.PhaseCreatorInv;
import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;

public class PhaseCreatorItemStack extends CustomItemStack {

    public PhaseCreatorItemStack() {
        super(Material.INK_SACK, 1, "§c§lCréer une Phase");

        this.setDurability((short) 10);

        this.setLore("§fPermet de créer une", "§fnouvelle phase pour le tournoi.");

        addItemInList(this);
    }

    @Override
    public void use(HumanEntity player, Event event) {
        new PhaseCreatorInv().open(player);
    }
}
