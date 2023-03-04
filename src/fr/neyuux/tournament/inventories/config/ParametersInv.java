package fr.neyuux.tournament.inventories.config;

import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.items.menus.ReturnArrowItemStack;
import fr.neyuux.tournament.items.menus.config.parameters.InscriptionsItemStack;
import fr.neyuux.tournament.items.menus.config.parameters.TournamentParticipantsNumberItemStack;
import fr.neyuux.tournament.utils.AbstractCustomInventory;

public class ParametersInv extends AbstractCustomInventory {

    public ParametersInv() {
        super("§7§lParamètres §adu Tournoi", 9);
    }

    @Override
    public void registerItems() {
        this.setItem(8, new ReturnArrowItemStack(new TournamentConfigurationInv()));

        this.setItem(0, new TournamentParticipantsNumberItemStack(TournamentPlugin.getInstance().getSelectedTournament()));
        this.setItem(1, new InscriptionsItemStack());
    }
}
