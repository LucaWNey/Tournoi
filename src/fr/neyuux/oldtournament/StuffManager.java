package fr.neyuux.oldtournament;

import fr.neyuux.oldtournament.enums.Stuff;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.File;
import java.io.IOException;

public class StuffManager {

    private final Tournament main;

    private final File file;
    private final YamlConfiguration yconfig;

    public StuffManager(Tournament main) {
        this.main = main;

        this.file = new File(main.getDataFolder(), "stuff.yml");
        this.yconfig = YamlConfiguration.loadConfiguration(file);
    }

    public void saveStuff(Player player, Stuff stuff) {
        String key = stuff.ordinal() + "." + player.getUniqueId();
        PlayerInventory inv = player.getInventory();

        this.yconfig.set(key + ".contents", inv.getContents());
        this.yconfig.set(key + ".armorcontents", inv.getArmorContents());
        try {
            yconfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void giveStuff(Player player, Stuff stuff) {
        String key = stuff.ordinal() + "." + player.getUniqueId();
        PlayerInventory inv = player.getInventory();

        inv.setContents((ItemStack[]) yconfig.get(key + ".contents"));
        inv.setArmorContents((ItemStack[]) yconfig.get(key + ".armorcontents"));
    }

    public void clearStuff(Player player) {
        PlayerInventory inv = player.getInventory();

        inv.clear();
        inv.setHelmet(null);
        inv.setChestplate(null);
        inv.setLeggings(null);
        inv.setBoots(null);
    }
}