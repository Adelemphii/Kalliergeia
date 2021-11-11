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
        if(!plugin.getConfig().getBoolean("disable-piston-crop-breaking")) return;

        for(Block block : event.getBlocks()) {
            if(CropTypes.checkCropType(block.getType()) != null || block.getType() == Material.FARMLAND) {
                System.out.println(CropTypes.checkCropType(block.getType()));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        if(!plugin.getConfig().getBoolean("disable-piston-crop-breaking")) return;

        for(Block block : event.getBlocks()) {
            if(CropTypes.checkCropType(block.getType()) != null || block.getType() == Material.FARMLAND) {
                System.out.println(CropTypes.checkCropType(block.getType()));
                event.setCancelled(true);
            }
        }
    }
}
