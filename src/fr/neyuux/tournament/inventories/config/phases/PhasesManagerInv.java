package fr.neyuux.tournament.inventories.config.phases;

import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.inventories.config.TournamentConfigurationInv;
import fr.neyuux.tournament.items.menus.ReturnArrowItemStack;
import fr.neyuux.tournament.items.menus.choosetournament.PhaseModifierItemStack;
import fr.neyuux.tournament.items.menus.config.phases.PhaseCreatorItemStack;
import fr.neyuux.tournament.phases.Phase;
import fr.neyuux.tournament.utils.AbstractCustomInventory;

public class PhasesManagerInv extends AbstractCustomInventory {

    public PhasesManagerInv() {
        super("§c§lStructure §adu Tournoi", 54);
        this.adaptIntToInvSize(TournamentPlugin.getInstance().getSelectedTournament().getPhases().size() + 2);
    }

    @Override
    public void registerItems() {
        this.setItem(this.getSize() - 1, new ReturnArrowItemStack(new TournamentConfigurationInv()));
        this.setItem(this.getSize() - 2, new PhaseCreatorItemStack());

        for (Phase phase : TournamentPlugin.getInstance().getSelectedTournament().getPhases())
            this.addItem(new PhaseModifierItemStack(phase));
    }
}
