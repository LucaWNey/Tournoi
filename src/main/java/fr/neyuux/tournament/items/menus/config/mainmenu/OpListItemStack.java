package fr.neyuux.tournament.items.menus.config.mainmenu;

import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashSet;

public class OpListItemStack extends CustomItemStack {

    public OpListItemStack() {
        super(Material.SIGN, 1, "§cListe des §lConfigurateurs");

        HashSet<String> namesList = new HashSet<>();

        for (OfflinePlayer player : Bukkit.getOperators())
            if (player.isOnline())
                namesList.add("§c" + player.getName());

        this.setLore(new ArrayList<>(namesList));
    }

}
