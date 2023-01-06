package fr.neyuux.tournament;

import fr.neyuux.tournament.utils.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;

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
            new ItemStack(Material.ARROW, 24)
            ),

    NO_ROD("§e§lNo Rod",
            new CustomItemStack(Material.IRON_HELMET).addEnchantmentV(Enchantment.PROTECTION_ENVIRONMENTAL, 2),
            new CustomItemStack(Material.DIAMOND_CHESTPLATE).addEnchantmentV(Enchantment.PROTECTION_ENVIRONMENTAL, 2),
            getEnchantedItem(Material.IRON_LEGGINGS, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
            getEnchantedItem(Material.DIAMOND_BOOTS, new SimpleEntry<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), null, null),
            getEnchantedItem(Material.DIAMOND_SWORD, new SimpleEntry<>(Enchantment.DAMAGE_ALL, 3), null, null),
                    new ItemStack(Material.GOLDEN_APPLE, 10),
                    getEnchantedItem(Material.BOW, new SimpleEntry<>(Enchantment.ARROW_DAMAGE, 2), null, null),
                    new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.LAVA_BUCKET, 1),
                    new ItemStack(Material.COBBLESTONE, 64), new ItemStack(Material.WOOD, 64), new ItemStack(Material.DIAMOND_PICKAXE),
                    new ItemStack(Material.IRON_AXE), new ItemStack(Material.ARROW, 24)
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
    List<ItemStack> inventory;

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

    public List<ItemStack> getInventory() {
        return inventory;
    }

    public static ItemStack getGoldenHeads(int amount) {
        return new CustomItemStack(Material.GOLDEN_APPLE, amount, "§6Golden Head").setLore("§dRégénère 5 coeurs", "§eDonne 2 coeurs d'absorption.");
    }

}
