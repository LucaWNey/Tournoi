package fr.neyuux.tournament.items;

import fr.minuskube.inv.SmartInventory;
import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.guis.TournamentListGUI;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ComparatorConfigItem extends CustomItemStack{
    public ComparatorConfigItem() {
        super(Material.REDSTONE_COMPARATOR, 1, "§c§lConfiguration");
        this.addGlowEffect();
    }

    @Override
    public void use(HumanEntity player, Event event) {

        SmartInventory.builder()
                .id("config_main_inv")
                .provider(new TournamentListGUI())
                .size(TournamentListGUI.calculateRows(), 9)
                .title("§aTournois")
                .closeable(true)
                .build()
                .open((Player) player);

        super.use(player, event);
    }

    @Override
    public void drop(Player player, PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
