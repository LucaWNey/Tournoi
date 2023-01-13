package fr.neyuux.tournament.items.hotbar;

import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.inventories.TournamentSelectionInv;
import fr.neyuux.tournament.inventories.config.TournamentConfigurationInv;
import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;

public class OpComparatorItemStack extends CustomItemStack {

    public OpComparatorItemStack() {
        super(Material.REDSTONE_COMPARATOR);
        this.setDisplayName("§a§lConfiguration du Tournoi");
        this.addGlowEffect();

        addItemInList(this);
    }

    @Override
    public void use(HumanEntity player, Event event) {
        if (TournamentPlugin.getInstance().getSelectedTournament() == null) {
            new TournamentSelectionInv().open(player);
        } else {
            new TournamentConfigurationInv().open(player);
        }
    }
}
