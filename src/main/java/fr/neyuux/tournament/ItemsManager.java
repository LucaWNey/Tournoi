package fr.neyuux.tournament;

import fr.neyuux.tournament.items.ComparatorConfigItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class ItemsManager {

    public static void updateSpawnItems(Player player) {
        PlayerInventory playerInv = player.getInventory();

        clearInventory(player);
        if (player.isOp()) {
            playerInv.setItem(6, new ComparatorConfigItem());
        }

        player.setExp(0);
        player.setLevel(0);
        player.getActivePotionEffects().clear();
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0, false , false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
        player.setMaxHealth(10);
        player.setHealth(player.getMaxHealth());
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(new Location(Bukkit.getWorld("Tournoi"), -571.5, 92.2, 322.5));
    }

    public static void updateSpawnAll() {
        Bukkit.getOnlinePlayers().forEach(ItemsManager::updateSpawnItems);
    }


    public static void clearInventory(Player player) {
        PlayerInventory playerInventory = player.getInventory();

        playerInventory.clear();
        playerInventory.setArmorContents(null);

        player.setExp(0);
        player.setLevel(0);
        player.getActivePotionEffects().clear();
        player.setMaxHealth(10);
        player.setHealth(player.getMaxHealth());
    }

    public static void clearAll() {
        Bukkit.getOnlinePlayers().forEach(ItemsManager::clearInventory);
    }
}
