package fr.neyuux.tournament.enums;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;

public enum Stuff {
	
	BUILD_UHC("§c§lBuild UHC", getEnchantedItem(Material.DIAMOND_HELMET, new SimpleEntry<>(Enchantment.PROTECTION_PROJECTILE, 2), null, null),
			getEnchantedItem(Material.DIAMOND_CHESTPLATE, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			getEnchantedItem(Material.DIAMOND_LEGGINGS, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			getEnchantedItem(Material.DIAMOND_BOOTS, new SimpleEntry<>(Enchantment.PROTECTION_PROJECTILE, 2), null, null),
			Arrays.asList(getEnchantedItem(Material.DIAMOND_SWORD, new SimpleEntry<>(Enchantment.DAMAGE_ALL, 3), null, null),
					new ItemStack(Material.GOLDEN_APPLE, 6), getGoldenHeads(),
					getEnchantedItem(Material.BOW, new SimpleEntry<>(Enchantment.ARROW_DAMAGE, 3), null, null),
					new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.LAVA_BUCKET),
					new ItemStack(Material.FISHING_ROD),
					new ItemStack(Material.COBBLESTONE, 64), new ItemStack(Material.WOOD, 64), new ItemStack(Material.DIAMOND_PICKAXE),
					new ItemStack(Material.DIAMOND_AXE), new ItemStack(Material.ARROW, 24)
					)),
	
	NO_ROD("§e§lNo Rod", getEnchantedItem(Material.IRON_HELMET, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			getEnchantedItem(Material.DIAMOND_CHESTPLATE, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			getEnchantedItem(Material.IRON_LEGGINGS, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			getEnchantedItem(Material.DIAMOND_BOOTS, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
			Arrays.asList(getEnchantedItem(Material.DIAMOND_SWORD, new SimpleEntry<>(Enchantment.DAMAGE_ALL, 3), null, null),
					new ItemStack(Material.GOLDEN_APPLE, 10),
					getEnchantedItem(Material.BOW, new SimpleEntry<>(Enchantment.ARROW_DAMAGE, 2), null, null),
					new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.LAVA_BUCKET, 1),
					new ItemStack(Material.COBBLESTONE, 64), new ItemStack(Material.WOOD, 64), new ItemStack(Material.DIAMOND_PICKAXE),
					new ItemStack(Material.IRON_AXE), new ItemStack(Material.ARROW, 24)
					));
	
	
	Stuff(String name, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, List<ItemStack> inventory) {
		this.name = name;
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
		this.inventory = inventory;
	}
	
	String name;
	ItemStack helmet;
	ItemStack chestplate;
	ItemStack leggings;
	ItemStack boots;
	List<ItemStack> inventory;
	
	public String getName() {
		return name;
	}
	
	public ItemStack getHelmet() {
		return helmet;
	}
	
	public ItemStack getChestplate() {
		return chestplate;
	}
	
	public ItemStack getLeggings() {
		return leggings;
	}
	
	public ItemStack getBoots() {
		return boots;
	}
	
	public List<ItemStack> getInventory() {
		return inventory;
	}
	
	
	private static ItemStack getEnchantedItem(Material material, SimpleEntry<Enchantment, Integer> enchant1, SimpleEntry<Enchantment, Integer> enchant2, SimpleEntry<Enchantment, Integer> enchant3) {
		ItemStack it = new ItemStack(material);
		ItemMeta itm = it.getItemMeta();
		if (enchant1 != null)itm.addEnchant(enchant1.getKey(), enchant1.getValue(), true);
		if (enchant2 != null)itm.addEnchant(enchant2.getKey(), enchant2.getValue(), true);
		if (enchant3 != null)itm.addEnchant(enchant3.getKey(), enchant3.getValue(), true);
		it.setItemMeta(itm);
		
		return it;
	}
	
	public static ItemStack getGoldenHeads() {
		ItemStack it = new ItemStack(Material.GOLDEN_APPLE, 3);
		ItemMeta itm = it.getItemMeta();
		itm.setDisplayName("§6Golden Head");
		it.setItemMeta(itm);
		return it;
	}
	
	
	
	public static Stuff getByRoundNumber(int roundNumber) {
		if (roundNumber % 2 == 0) return NO_ROD;
		else return BUILD_UHC;
	}

}
