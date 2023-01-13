package fr.neyuux.tournament.inventories.config;

import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.items.menus.config.mainmenu.*;
import fr.neyuux.tournament.utils.AbstractCustomInventory;
import org.bukkit.entity.HumanEntity;

public class TournamentConfigurationInv extends AbstractCustomInventory {

    private String openerName = null;


    public TournamentConfigurationInv() {
        super("§a§lConfiguration " + TournamentPlugin.getInstance().getSelectedTournament().getDisplayName(), 45);
    }

    @Override
    public void registerItems() {
        this.setAllCorners((byte)5);

        this.setItem(38, new OpListItemStack());
        this.setItem(30, new PlayersMenuItemStack(openerName));
        this.setItem(32, new ManageTournamentsItemStack());
        this.setItem(13, new ParametersItemStack());
        this.setItem(15, new PhasesManagerItemStack());
        this.setItem(11, new MatchListItemStack());
    }

    @Override
    public void open(HumanEntity player) {
        this.openerName = player.getName();
        super.open(player);
    }
}
