package fr.neyuux.tournament.items.menus.choosetournament;

import fr.neyuux.tournament.phases.Phase;
import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;

public class PhaseModifierItemStack extends CustomItemStack {

    private final Phase phase;

    public PhaseModifierItemStack(Phase phase) {
        super(Material.BREWING_STAND_ITEM, 1, "§cModifier la Phase §l" + phase.getName());

        this.phase = phase;

        this.setLore("§fPermet de modifier cette phase.", "", "§bType : §c§l" + phase.getType(), "§bNombre de participants : §7§l" + phase.getParticipants(), "", "§7>>Clique pour modifier");
    }

    @Override
    public void use(HumanEntity player, Event event) {
        //TODO
    }

}
