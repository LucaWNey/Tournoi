package fr.neyuux.tournament.items.menus.choosetournament;

import fr.neyuux.tournament.Tournament;
import fr.neyuux.tournament.TournamentPlugin;
import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TournamentChooseItemStack extends CustomItemStack {

    private final Tournament tournament;

    public TournamentChooseItemStack(Tournament tournament) {
        super(Material.SIGN, tournament.getParticipants(), tournament.getDisplayName());

        this.tournament = tournament;

        this.setLore("§c§lPhase actuelle : §c" + tournament.getPhase().getName(),
                "§7§lParticipants au départ : §7" + tournament.getParticipants(),
                "§b§lCréateur : §b" + tournament.getCreator(),
                "",
                "§a>>Clique pour sélectionner ce tournoi",
                "§b>>Shift + Clic pour renommer",
                "§4>>Touche de Drop pour supprimer ce tournoi");

        addItemInList(this);
    }

    @Override
    public void use(HumanEntity player, Event event) {
        ClickType clickType =((InventoryClickEvent)event).getClick();

        if (clickType.equals(ClickType.DROP)) {
            tournament.delete();

        } else if (clickType.isShiftClick()) {
            TournamentPlugin.openRenameTournamentInventory((Player) player);

        } else {
            player.closeInventory();
            TournamentPlugin.getInstance().setSelectedTournament(this.tournament);
            Bukkit.broadcastMessage(TournamentPlugin.getPrefix() + "§b" + player.getName() + " §aa sélectionné le tournoi §2\"" + tournament.getDisplayName() + "§2\"§a.");
        }
    }
}
