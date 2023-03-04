package fr.neyuux.tournament.inventories.config.phases;

import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.enums.PhaseType;
import fr.neyuux.tournament.items.menus.ReturnArrowItemStack;
import fr.neyuux.tournament.items.menus.config.phases.PhaseConfirmCreateItemStack;
import fr.neyuux.tournament.items.menus.config.phases.PhaseParticipantNumberItemStack;
import fr.neyuux.tournament.items.menus.config.phases.PhaseTypeModifierItemStack;
import fr.neyuux.tournament.utils.AbstractCustomInventory;

public class PhaseCreatorInv extends AbstractCustomInventory {

    private PhaseTypeModifierItemStack phaseTypeModifierItemStack;
    private PhaseParticipantNumberItemStack participantsNumberItemStack;

    public PhaseCreatorInv() {
        super("§c§lCréation d'une Phase", 9);
    }

    @Override
    public void registerItems() {
        this.setItem(this.getSize() - 1, new ReturnArrowItemStack(new PhasesManagerInv()));

        this.phaseTypeModifierItemStack = new PhaseTypeModifierItemStack(PhaseType.GROUPS);
        this.participantsNumberItemStack = new PhaseParticipantNumberItemStack(TournamentPlugin.getInstance().getSelectedTournament().getParticipants());

        this.setItem(0, this.phaseTypeModifierItemStack);
        this.setItem(1, this.participantsNumberItemStack);

        this.setItem(this.getSize() - 2, new PhaseConfirmCreateItemStack(this));
    }


    public PhaseParticipantNumberItemStack getParticipantsNumberItemStack() {
        return participantsNumberItemStack;
    }

    public PhaseTypeModifierItemStack getPhaseTypeModifierItemStack() {
        return phaseTypeModifierItemStack;
    }
}
