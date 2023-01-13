package fr.neyuux.tournament.inventories;

import fr.neyuux.tournament.Tournament;
import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.enums.Countries;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class InscriptionsInv implements Listener {

    private final Inventory inv;
    private boolean onCooldown = false;

    public InscriptionsInv() {
        this.inv = Bukkit.createInventory(null, 54, "§6§lInscriptions");

        for (Countries country : Countries.values()) {
            if(country.isAvailable())
                this.inv.addItem(country.getFlagHeadItemStack());
        }

        TournamentPlugin.getInstance().getServer().getPluginManager().registerEvents(this, TournamentPlugin.getInstance());
    }


    @EventHandler
    public void onInscritpionsInv(InventoryClickEvent ev) {
        if (this.inv.getName().equals(ev.getInventory().getName()) && ev.getCurrentItem() != null) {
            ev.setCancelled(true);

            if (onCooldown) return;

            Tournament tournament = TournamentPlugin.getInstance().getSelectedTournament();
            Player player = (Player) ev.getWhoClicked();
            Countries country = Countries.getFromDisplayName(ev.getCurrentItem().getItemMeta().getDisplayName());
            List<Integer> usedCountry = tournament.getYamlConfiguration().getIntegerList("usedCountries");

            usedCountry.add(country.ordinal());
            tournament.setPlayerCountry(player, country);
            tournament.setInConfig("players." + player.getUniqueId() + ".pays", country);
            tournament.setInConfig("usedcountries", usedCountry);
            Bukkit.broadcastMessage(tournament.getPrefix() + "§b" + player.getName() + " §aa choisi le " + country.getDisplayName() + "§a !");
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.FIREWORK_BLAST, 8f, 1f));

            this.inv.remove(ev.getCurrentItem());

            this.onCooldown = true;
            Bukkit.getScheduler().runTaskLater(TournamentPlugin.getInstance(), () -> this.onCooldown = false, 70L);
        }
    }


    public Inventory getInventory() {
        return inv;
    }
}
