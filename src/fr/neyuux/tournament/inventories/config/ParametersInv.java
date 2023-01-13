package fr.neyuux.tournament.inventories.config;

import fr.neyuux.tournament.items.menus.ReturnArrowItemStack;
import fr.neyuux.tournament.items.menus.config.parameters.InscriptionsItemStack;
import fr.neyuux.tournament.items.menus.config.parameters.ParticipantsNumberItemStack;
import fr.neyuux.tournament.utils.AbstractCustomInventory;

public class ParametersInv extends AbstractCustomInventory {

    public ParametersInv() {
        super("§7§lParamètres §7du Tournoi", 9);
    }

    @Override
    public void registerItems() {
        this.setItem(8, new ReturnArrowItemStack(new TournamentConfigurationInv()));

        this.setItem(0, new ParticipantsNumberItemStack());
        this.setItem(1, new InscriptionsItemStack());
    }
}
