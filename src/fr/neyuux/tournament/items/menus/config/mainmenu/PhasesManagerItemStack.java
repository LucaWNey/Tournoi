package fr.neyuux.tournament.items.menus.config.mainmenu;

import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;

public class PhasesManagerItemStack extends CustomItemStack {

    public PhasesManagerItemStack() {
        super(Material.BRICK, 1, "§c§lStructures du Tournoi");

        this.setLore("§fPermet de gérer les", "§fphases de la partie.");

        addItemInList(this);
    }

    @Override
    public void use(HumanEntity player, Event event) {
        new PhasesManagerInv.open(player);
    }
}
