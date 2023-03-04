package fr.neyuux.tournament.items.menus.config.phases;

import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.enums.PhaseType;
import fr.neyuux.tournament.inventories.config.phases.PhaseCreatorInv;
import fr.neyuux.tournament.phases.Phase;
import fr.neyuux.tournament.utils.Anvil;
import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class PhaseConfirmCreateItemStack extends CustomItemStack {

    private static final ArrayList<HumanEntity> renamingPlayer = new ArrayList<>();

    private final PhaseType phaseType;
    private final int participants;

    public PhaseConfirmCreateItemStack(PhaseCreatorInv inv) {
        super(Material.INK_SACK, 1, "§c§lCréer une Phase");

        this.setDurability((short) 10);

        this.phaseType = inv.getPhaseTypeModifierItemStack().getPhaseType();
        this.participants = inv.getParticipantsNumberItemStack().getParticipants();

        this.setLore("§fConfirme la création de la Phase", "", "§bType : §c§l" + this.phaseType.getName(), "§bNombre de participants : §7§l" + this.participants, "", "§7>>Clique pour confirmer");

        addItemInList(this);
    }

    @Override
    public void use(HumanEntity player, Event event) {
        try {
            Phase phase = PhaseType.getPhaseConstructors().get(phaseType.getName()).newInstance(phaseType.getName(), participants);
            TournamentPlugin main = TournamentPlugin.getInstance();

            main.getSelectedTournament().getPhases().add(phase);

            player.closeInventory();
            TournamentPlugin.sendTitle((Player) player, "§c§lNommez cette Phase", "§fRenommez le papier dans l'enclume avec le nom de la Phase.", 2, 40, 10);

            Bukkit.getScheduler().runTaskLater(main, () -> {
                ((Player) player).setLevel(4);
                Inventory anvilInv = Anvil.openAnvilInventory(((Player) player).getPlayer());
                anvilInv.setItem(0, new CustomItemStack(Material.PAPER, 1, "Nouveau Phase " + (TournamentPlugin.getInstance().getSelectedTournament().getPhases().size() + 1)).addLore("§0" + TournamentPlugin.getInstance().getSelectedTournament().getPhases().indexOf(phase)));
                renamingPlayer.add(player);
            }, 22L);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            Bukkit.broadcastMessage(TournamentPlugin.getPrefix() + "§4[§cErreur§4] §CImpossible de créer une nouvelle phase.");
            e.printStackTrace();
        }
    }

    public static ArrayList<HumanEntity> getRenamingPlayers() {
        return renamingPlayer;
    }
}
