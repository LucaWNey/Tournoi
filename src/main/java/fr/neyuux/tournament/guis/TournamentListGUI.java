package fr.neyuux.tournament.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.neyuux.tournament.Tournament;
import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class TournamentListGUI implements InventoryProvider {

    public static final ClickableItem GLASS_PANE = ClickableItem.empty(new CustomItemStack(Material.STAINED_GLASS_PANE, 1, (byte)5).setDisplayName("§f"));


    @Override
    public void init(Player player, InventoryContents contents) {
        int rows = calculateRows();
        contents.set(0, 0, GLASS_PANE);
        contents.set(0, 1, GLASS_PANE);
        contents.set(0, 7, GLASS_PANE);
        contents.set(0, 8, GLASS_PANE);

        contents.set(1, 0, GLASS_PANE);
        contents.set(1, 8, GLASS_PANE);

        contents.set(rows - 2, 0, GLASS_PANE);
        contents.set(rows - 2, 8, GLASS_PANE);

        contents.set(rows - 1, 0, GLASS_PANE);
        contents.set(rows - 1, 1, GLASS_PANE);
        contents.set(rows - 1, 7, GLASS_PANE);
        contents.set(rows - 1, 8, GLASS_PANE);
    }

    @Override
    public void update(Player player, InventoryContents contents) {

        Iterator<Tournament> tournamentIterator = TournamentPlugin.INSTANCE.getAllTournaments().iterator();

        for (int column = 0; column < 8; column++) {
            for (int row = 0; row <= calculateRows() - 2; row++) {
                tournamentIterator.next()
            }
        }
    }



    public static int calculateRows() {
        return (int) Math.ceil(TournamentPlugin.INSTANCE.getAllTournaments().size() / 5.0) + 2;
    }
}
