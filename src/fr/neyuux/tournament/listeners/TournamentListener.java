package fr.neyuux.tournament.listeners;

import fr.neyuux.tournament.Tournament;
import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.items.menus.choosetournament.CreateTournamentItemStack;
import fr.neyuux.tournament.items.menus.config.phases.PhaseConfirmCreateItemStack;
import fr.neyuux.tournament.phases.Phase;
import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ConcurrentModificationException;

public class TournamentListener implements Listener {


    @EventHandler
    public void onQuit(PlayerQuitEvent ev) {
        CreateTournamentItemStack.getRenamingPlayers().remove(ev.getPlayer());
        PhaseConfirmCreateItemStack.getRenamingPlayers().remove(ev.getPlayer());

    }

    @EventHandler
    public void onCloseInv(InventoryCloseEvent ev) {
        if (ev.getInventory().getType().equals(InventoryType.ANVIL)) {
            CreateTournamentItemStack.getRenamingPlayers().remove(ev.getPlayer());
            PhaseConfirmCreateItemStack.getRenamingPlayers().remove(ev.getPlayer());
        }
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onRenameInAnvil(InventoryClickEvent e){
        HumanEntity ent = e.getWhoClicked();

        if(ent instanceof Player) {
            Player player = (Player)ent;
            Inventory inv = e.getInventory();

            if(inv instanceof AnvilInventory) {
                InventoryView view = e.getView();
                int rawSlot = e.getRawSlot();

                if(rawSlot == view.convertSlot(rawSlot)) {
                    if(rawSlot == 2) {
                        ItemStack item = e.getCurrentItem();

                        if(item != null){
                            ItemMeta meta = item.getItemMeta();

                            if(meta != null){
                                if(meta.hasDisplayName()){
                                    String displayName = meta.getDisplayName().replace('&', '§');

                                    if (CreateTournamentItemStack.getRenamingPlayers().contains(player)) {
                                        Tournament tournament = TournamentPlugin.getByID(meta.getLore().get(0).substring(meta.getLore().get(0).length() - 4));

                                        tournament.setDisplayName(displayName);
                                        Bukkit.broadcastMessage(TournamentPlugin.getPrefix() + "§b" + player.getName() + " §aa renommé le tournoi avec l'ID §2\"§7" + tournament.getID() + "§2\" §aen §r" + tournament.getDisplayName());
                                    } else if (PhaseConfirmCreateItemStack.getRenamingPlayers().contains(player)) {
                                        Phase phase = TournamentPlugin.getInstance().getSelectedTournament().getPhaseByID(meta.getLore().get(0).substring(meta.getLore().get(0).length() - 4));

                                        phase.setName(displayName);
                                    }
                                    e.setCancelled(true);
                                    inv.clear();
                                    player.closeInventory();

                                    player.setLevel(0);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClickInv(InventoryClickEvent event) {
        HumanEntity human = event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item == null) return;

        if (!event.getClickedInventory().getType().equals(InventoryType.PLAYER))
            event.setCancelled(true);

        try {
            for (CustomItemStack customitem : CustomItemStack.getItemList())
                if (customitem.isCustomSimilar(item)) {
                    customitem.use(human, event);
                }
        } catch (ConcurrentModificationException ignored) {}
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null) return;

        try {
            for (CustomItemStack customitem : CustomItemStack.getItemList())
                if (customitem.isCustomSimilar(item)) {
                    event.setCancelled(true);
                    customitem.use(player, event);
                }
        } catch (ConcurrentModificationException ignored) {}
    }

}
