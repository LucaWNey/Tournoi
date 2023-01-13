package fr.neyuux.tournament.enums;

import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum Stuffs {

    BUILD_UHC("§c§lBuild UHC",
            new CustomItemStack(Material.DIAMOND_HELMET).addEnchantmentV(Enchantment.PROTECTION_PROJECTILE, 2),
            new CustomItemStack(Material.DIAMOND_CHESTPLATE).addEnchantmentV(Enchantment.PROTECTION_ENVIRONMENTAL, 2),
            new CustomItemStack(Material.DIAMOND_LEGGINGS).addEnchantmentV(Enchantment.PROTECTION_ENVIRONMENTAL, 2),
            new CustomItemStack(Material.DIAMOND_BOOTS).addEnchantmentV(Enchantment.PROTECTION_PROJECTILE, 2),
            new CustomItemStack(Material.DIAMOND_SWORD).addEnchantmentV(Enchantment.DAMAGE_ALL, 3),
            new ItemStack(Material.GOLDEN_APPLE, 6),
            getGoldenHeads(3),
            new CustomItemStack(Material.BOW).addEnchantmentV(Enchantment.ARROW_DAMAGE, 3),
            new ItemStack(Material.WATER_BUCKET),
            new ItemStack(Material.WATER_BUCKET),
            new ItemStack(Material.LAVA_BUCKET),
            new ItemStack(Material.LAVA_BUCKET),
            new ItemStack(Material.FISHING_ROD),
            new ItemStack(Material.COBBLESTONE, 64),
            new ItemStack(Material.WOOD, 64),
            new ItemStack(Material.DIAMOND_PICKAXE),
            new ItemStack(Material.DIAMOND_AXE),
            new ItemStack(Material.ARROW, 28)
            ),

    NO_ROD("§e§lNo Rod",
            new CustomItemStack(Material.IRON_HELMET).addEnchantmentV(Enchantment.PROTECTION_ENVIRONMENTAL, 2),
            new CustomItemStack(Material.DIAMOND_CHESTPLATE).addEnchantmentV(Enchantment.PROTECTION_ENVIRONMENTAL, 2),
            new CustomItemStack(Material.IRON_LEGGINGS).addEnchantmentV(Enchantment.PROTECTION_ENVIRONMENTAL,  2),
            new CustomItemStack(Material.DIAMOND_BOOTS).addEnchantmentV(Enchantment.PROTECTION_ENVIRONMENTAL, 2),
            new CustomItemStack(Material.DIAMOND_SWORD).addEnchantmentV(Enchantment.PROTECTION_ENVIRONMENTAL, 2),
            new ItemStack(Material.GOLDEN_APPLE, 10),
            new CustomItemStack(Material.BOW).addEnchantmentV(Enchantment.ARROW_DAMAGE, 2),
            new ItemStack(Material.WATER_BUCKET),
            new ItemStack(Material.WATER_BUCKET),
            new ItemStack(Material.LAVA_BUCKET, 1),
            new ItemStack(Material.COBBLESTONE, 64),
            new ItemStack(Material.WOOD, 64),
            new ItemStack(Material.DIAMOND_PICKAXE),
            new ItemStack(Material.IRON_AXE),
            new ItemStack(Material.ARROW, 24)
            );


    Stuffs(String displayName, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, ItemStack... inventory) {
        this.displayName = displayName;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
        this.inventory = inventory;
    }

    String displayName;
    ItemStack helmet;
    ItemStack chestplate;
    ItemStack leggings;
    ItemStack boots;
    ItemStack[] inventory;

    public String getName() {
        return displayName;
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

    public ItemStack[] getInventory() {
        return inventory;
    }

    public static ItemStack getGoldenHeads(int amount) {
        return new CustomItemStack(Material.GOLDEN_APPLE, amount, "§6Golden Head").setLore("§dRégénère 5 coeurs", "§eDonne 2 coeurs d'absorption.");
    }

}
