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
    MELON(0, 3, Material.MELON_SEEDS, Material.MELON),
    PUMPKIN(0, 0, Material.PUMPKIN_SEEDS, Material.PUMPKIN),
    COCOA(0, 0, Material.COCOA_BEANS, Material.COCOA),
    NETHERWART(0, 0, Material.NETHER_WART, Material.NETHER_WART);

    private final int dropMin;
    private final int dropMax;
    private final Material seedType;
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

    public ItemStack getSeed() {
        return new ItemStack(seedType);
    }

    public ItemStack getCrop() {
        return new ItemStack(cropType);
    }

    // get crop type based off seeds or crop
    public static CropTypes checkCropType(Material item) {
        for (CropTypes crop : CropTypes.values()) {
            if (item == crop.getCropType() || item == crop.getSeedType()) {
                return crop;
            }
        }
        return null;
    }

    // get extra seed amount based on binomial distribution
    public static int getSeedDropAmount(CropTypes crop, int fortune) {
        Random random = new Random();

        int extra = 0;
        for (int i = 0; i < 1 + fortune; i++) {
            extra += (int) (Math.random() * (crop.getDropMax() - crop.getDropMin() + 1)) + crop.getDropMin();
        }
        return extra;
    }
}
