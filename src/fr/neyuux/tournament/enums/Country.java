package fr.neyuux.tournament.enums;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public enum Country {

	RUSSIE("Russie", "§f§lRu§9§lss§c§lie", "RU", "§fRu§9ss§cie §f"),
	ALLEMAGNE("Allemagne", "§8All§c§lema§e§lgne", "DE", "§8§lAllemagne §f"),
	BRESIl("Brésil", "§2§lBr§e§lés§2§lil", "BR", "§2§lBrésil §f"),
	PORTUGAL("Portugal", "§2§lPort§4§lugal", "PT", "§2Port§4ugal §f"),
	ARGENTINE("Argentine", "§b§lArg§fen§b§ltine", "AR", "§b§lArgentine §f"),
	BELGIQUE("Belgique", "§8§lBel§egi§c§lque", "BE", "§c§lBelgique §f"),
	POLOGNE("Pologne", "§f§lPolo§c§lgne", "PL", "§lPolo§c§lgne §f"),
	FRANCE("France", "§9§lFr§f§lan§c§lce", "FR", "§9Fr§fan§cce §f"),
	ESPAGNE("Espagne", "§c§lEs§e§lpag§c§lne", "ES", "§cEs§epag§cne §f"),
	PEROU("Pérou", "§4§lPé§f§lr§4§lou", "PE", "§4Pé§fr§4ou §f"),
	SUISSE("Suisse", "§c§lSu§f§li§c§lsse", "CH", "§cSu§fi§csse §f"),
	ANGLETERRE("Angleterre", "§lAngl§cet§f§lerre", "UK", "§cAngleterre §f"),
	COLOMBIE("Colombie", "§e§lColo§1mb§4§lie", "CO", "§e§lColombie §f"),
	MEXIQUE("Mexique", "§a§lMe§f§lxiq§c§lue", "MX", "§a§lMexique §f"),
	URUGUAY("Uruguay", "§e§lU§f§lrug§1§luay", "UY", "§1§lUruguay §f"),
	CROATIE("Croatie", "§c§lCro§f§lat§9§lie", "HR", "§cCro§fat§9ie §f"),
	DANEMARK("Danemark", "§cD§f§lan§c§lemark", "DK", "§c§lDanemark §f"),
	ISLANDE("Islande", "§1I§fs§4§lla§1§lnde", "IS", "§1§lIslande §f"),
	COSTA_RICA("Costa Rica", "§9§lCosta §c§lRica", "CR", "§9§lCostaRica §f"),
	SUEDE("Suède", "§3§lS§e§lu§3§lède", "SE", "§3S§e§lu§3ède §f"),
	TUNISIE("Tunisie", "§c§lTun§f§li§f§lsie", "TN", "§cTun§fi§fsie §f"),
	EGYPTE("Égypte", "§4§lÉg§f§lyp§8§lte", "EG", "§4Ég§fyp§8te §f"),
	NEPAL("Népal", "§lNé§cp§fa§cl", "NP", "§lNé§cp§fa§cl §f"),
	IRAN("Iran", "§2§lI§f§lr§c§lan", "IR", "§2I§fr§can §f"),
	HONGRIE("Hongrie", "§4§lHo§f§lngr§2§lie", "HU", "§4Ho§fngr§2ie §f"),
	NIGERIA("Nigéria", "§2§lNi§f§lgér§2§lia", "NG", "§2Ni§fgér§2ia §f"),
	AUSTRALIE("Australie", "§c§lA§fus§1§ltralie", "AU", "§1§lAustralie §f"),
	JAPON("Japon", "§lJa§4§lp§f§lon", "JP", "§lJa§4p§f§lon §f"),
	MAROC("Maroc", "§c§lMa§2§lr§c§loc", "MA", "§cMa§2r§coc §f"),
	ITALIE("Italie", "§2§lIt§f§lal§c§lie", "IT", "§2It§fal§cie §f"),
	COREE_DU_SUD("Corée du Sud", "§lCorée §9du §cSud", "KR", "§lCorée duSud §f"),
	ARABIE_SAOUDITE("Arabie Saoudite", "§2§lArabie Saoudite", "SA", "§2Arab. Saou. §f"),
	ESTONIE("Estonie", "§b§lEs§8§lton§f§lie", "EE", "§bEs§8ton§fie §f"),
	ETATS_UNIS("États-Unis", "§9§lÉtats-§c§lUnis", "US", "§c§lÉtatsUnis §f"),
	ALGERIE("Algérie", "§2§lAlg§4§lé§f§lrie", "DZ", "§2Alg§4é§frie §f"),
	PAYS_BAS("Pays-Bas", "§4§lPays§f-§1§lBas", "NL", "§1§lPays-Bas §f");
	
	Country(String name, String displayname, String code, String scoreboardprefixname) {
		this.name = name;
		this.displayname = displayname;
		this.code = code;
		this.scoreboardprefixname = scoreboardprefixname;
	}
	
	private final String name;
	private final String displayname;
	private final String code;
	private final String scoreboardprefixname;
	
	
	public String getName() {
		return name;
	}
	
	public String getDisplayName() {
		return displayname;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getScoreboardPrefixName() {
		return scoreboardprefixname;
	}
	
	public ItemStack getHead() {
		ItemStack it = null;
		SkullMeta itm = null;
		if (Bukkit.getWorld("Tournoi") == null)
			Bukkit.getServer().createWorld(new WorldCreator("Tournoi"));
		org.bukkit.block.Chest chest = (Chest) Bukkit.getWorld("Tournoi").getBlockAt(-641, 58, 336).getState();
		Inventory inventory = chest.getInventory();

		for (ItemStack its : inventory.getContents())
			if (its != null) 
				if (its.hasItemMeta())
					if (its.getItemMeta().getDisplayName().equalsIgnoreCase(getName())) {
						it = new ItemStack(its.getType(), its.getAmount(), its.getDurability());
						itm = (SkullMeta) its.getItemMeta();
				}
		
		if (itm == null) {
			System.out.println(getName());
			for (Player p : Bukkit.getOnlinePlayers())
				p.teleport(new Location(Bukkit.getWorld("Tournoi"), -641, 65, 336));
		}
		it.setItemMeta(itm);
		return it;
	}
	
	
	public static Country getByCode(String code) {
		for (Country p : Country.values())
			if (p.getCode().equalsIgnoreCase(code))
				return p;
		return null;
	}
	
}
