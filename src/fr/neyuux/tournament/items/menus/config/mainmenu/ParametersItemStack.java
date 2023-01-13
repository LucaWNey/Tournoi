package fr.neyuux.tournament.items.menus.config.mainmenu;

import fr.neyuux.tournament.inventories.config.ParametersInv;
import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;

public class ParametersItemStack extends CustomItemStack {

    public ParametersItemStack() {
        super(Material.APPLE, 1, "§7§lParamètres de la Partie");

        this.setLore("§fPermet de changer les", "§foptions de la partie.");

        addItemInList(this);
    }

    @Override
    public void use(HumanEntity player, Event event) {
        new ParametersInv().open(player);
    }
}
