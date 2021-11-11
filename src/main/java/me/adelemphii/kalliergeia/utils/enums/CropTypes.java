package me.adelemphii.kalliergeia.utils.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public enum CropTypes {

    WHEAT(0, 3, Material.WHEAT_SEEDS, Material.WHEAT),
    BEETROOT(1, 4, Material.BEETROOT_SEEDS, Material.BEETROOT),
    CARROT(0, 3, Material.CARROT, Material.CARROT),
    CARROTS(0, 3, Material.CARROTS, Material.CARROTS),
    POTATO(1, 3, Material.POTATO, Material.POTATO),
    POTATOES(1, 3, Material.POTATOES, Material.POTATOES),
    NETHERWART(0, 0, Material.NETHER_WART, Material.NETHER_WART);

    // minimum number of seeds to produce
    private final int dropMin;
    // maximum number of seeds to produce
    private final int dropMax;
    // material of the seed
    private final Material seedType;
    // material of the crop
    private final Material cropType;

    CropTypes(int dropMin, int dropMax, Material seedType, Material cropType) {
        this.dropMin = dropMin;
        this.dropMax = dropMax;
        this.seedType = seedType;
        this.cropType = cropType;
    }

    public int getDropMin() {
        return dropMin;
    }

    public int getDropMax() {
        return dropMax;
    }

    public Material getSeedType() {
        return seedType;
    }

    public Material getCropType() {
        return cropType;
    }

    /**
     * Get the crop type based off the seed/yield material
     * @param item ItemStack of the seed/yield
     * @return CropTypes of the seed/yield
     */
    public static CropTypes matchType(Material item) {
        for (CropTypes crop : CropTypes.values()) {
            if (item == crop.getCropType() || item == crop.getSeedType()) {
                return crop;
            }
        }
        return null;
    }

    /**
     * Get a random number of seeds to drop based on binomial distribution
     * @param crop The crop type to access the dropMin/dropMax
     * @param fortune fortune level of the hoe used to break the crop
     * @return number of seeds to drop
     */
    public static int getSeedDropAmount(CropTypes crop, int fortune) {

        int extra = 0;
        for (int i = 0; i < 1 + fortune; i++) {
            extra += (int) (Math.random() * (crop.getDropMax() - crop.getDropMin() + 1)) + crop.getDropMin();
        }
        return extra;
    }
}
