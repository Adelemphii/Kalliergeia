package me.adelemphii.kalliergeia.events;

import me.adelemphii.kalliergeia.Kalliergeia;
import me.adelemphii.kalliergeia.utils.enums.CropTypes;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class PistonBreakCropListener implements Listener {

    Kalliergeia plugin;

    public PistonBreakCropListener(Kalliergeia plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {

        // Prevents pistons from pushing crops/farmland, therefore breaking them, if enabled in config.
        if(!plugin.getConfig().getBoolean("disable-piston-crop-breaking")) return;

        for(Block block : event.getBlocks()) {
            if(CropTypes.matchType(block.getType()) != null || block.getType() == Material.FARMLAND) {
                System.out.println(CropTypes.matchType(block.getType()));
                event.setCancelled(true); // would prefer if this set the block above to AIR instead
            }
        }
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {

        // Prevents pistons from pulling crops/farmland, therefore breaking them, if enabled in config.
        if(!plugin.getConfig().getBoolean("disable-piston-crop-breaking")) return;

        for(Block block : event.getBlocks()) {
            if(CropTypes.matchType(block.getType()) != null || block.getType() == Material.FARMLAND) {
                System.out.println(CropTypes.matchType(block.getType()));
                event.setCancelled(true);
            }
        }
    }
}
