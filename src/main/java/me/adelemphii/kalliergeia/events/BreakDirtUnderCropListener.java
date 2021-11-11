package me.adelemphii.kalliergeia.events;

import me.adelemphii.kalliergeia.utils.enums.CropTypes;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakDirtUnderCropListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        // Checks if the block above the broken block is a valid croptype, if so, it will set the block to air
        // to prevent the crop from yielding loot
        if(CropTypes.matchType(event.getBlock().getRelative(BlockFace.UP).getType()) != null) {
            event.getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
        }
    }
}
