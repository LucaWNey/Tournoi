package fr.neyuux.tournament;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Tournament extends JavaPlugin {

    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.INFO, "Tournament disabling");

        super.onDisable();
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().log(Level.INFO, "Tournament enabling");

        super.onEnable();
    }
}
