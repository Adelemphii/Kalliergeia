package me.adelemphii.kalliergeia.utils.enums;

import org.bukkit.Material;

public enum HoeTypes {

    HAND(Material.AIR, 0),
    WOOD_HOE(Material.WOODEN_HOE, 1),
    STONE_HOE(Material.STONE_HOE, 1),
    IRON_HOE(Material.IRON_HOE, 2),
    GOLD_HOE(Material.GOLDEN_HOE, 2),
    DIAMOND_HOE(Material.DIAMOND_HOE, 3);

    private final Material material;
    private final int yield;

    HoeTypes(Material material, int yield) {
        this.material = material;
        this.yield = yield;
    }

    public Material getMaterial() {
        return material;
    }

    public int getYield() {
        return yield;
    }

    // get hoetype from material
    public static HoeTypes getType(Material material) {
        if(material == null) return HAND;

        for (HoeTypes hoe : HoeTypes.values()) {
            if (hoe.getMaterial() == material) {
                return hoe;
            }
        }
        return HAND;
    }
}
