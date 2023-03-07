package fr.neyuux.tournament.inventories;

import fr.neyuux.tournament.Tournament;
import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.items.menus.choosetournament.CreateTournamentItemStack;
import fr.neyuux.tournament.items.menus.choosetournament.TournamentChooseItemStack;
import fr.neyuux.tournament.utils.AbstractCustomInventory;
import org.bukkit.entity.HumanEntity;

public class TournamentSelectionInv extends AbstractCustomInventory {

    public TournamentSelectionInv() {
        super("§a§lChoix du Tournoi", 54);
        this.adaptIntToInvSize(20 + TournamentPlugin.getLoadedTournaments().size());
    }

    @Override
    public void registerItems() {
        this.setAllCorners((byte)5);

        this.setItem(8, new CreateTournamentItemStack());

        for (Tournament tournament : TournamentPlugin.getLoadedTournaments())
            for (int slot = 10; slot < this.getSize() - 9; slot++)
                if (this.getItem(slot) == null) {

                    this.setItem(slot, new TournamentChooseItemStack(tournament));
                    break;
                }
    }

    @Override
    public void open(HumanEntity player) {
        TournamentPlugin.getInstance().loadTournaments();
        super.open(player);
    }
}
