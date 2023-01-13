package fr.neyuux.tournament;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class TournamentListener implements Listener {

    @EventHandler
    public void onRenameTournament(InventoryClickEvent ev) {
         if (ev.getInventory().getName().equals("§a§lNommez votre Tournoi")) {
             ItemStack current = ev.getCurrentItem();

             if (ev.getSlot() != 2 || current == null)
                 ev.setCancelled(true);
             else {
                 TournamentPlugin main = TournamentPlugin.getInstance();
                 Tournament tournament = main.getSelectedTournament();
                 String displayName = current.getItemMeta().getDisplayName();
                 Player player = (Player)ev.getWhoClicked();

                 if (tournament == null) {
                     main.setSelectedTournament(new Tournament(displayName.replace('&', '§'), player.getName()));
                 } else {
                     tournament.setDisplayName(displayName.replace('&', '§'));
                 }

                 player.setTotalExperience(0);
             }
         }
     }

}
