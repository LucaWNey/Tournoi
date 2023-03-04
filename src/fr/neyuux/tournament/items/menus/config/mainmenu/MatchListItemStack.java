package fr.neyuux.tournament.items.menus.config.mainmenu;

import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;

public class MatchListItemStack extends CustomItemStack {

    public MatchListItemStack() {
        super(Material.PAPER, 1, "§f§lListe des Matchs");

        this.setLore("§fPermet de gérer les", "§fmatchs du Tournoi.", "§fLes inscriptions doivent être fermées", "§fpour actualiser les matchs du Tournoi.");

        addItemInList(this);
    }

    @Override
    public void use(HumanEntity player, Event event) {
        //new MatchListInv().open(player);
    }
}
