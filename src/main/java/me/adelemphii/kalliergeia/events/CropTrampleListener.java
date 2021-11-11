package me.adelemphii.kalliergeia.events;

import me.adelemphii.kalliergeia.Kalliergeia;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CropTrampleListener implements Listener {

    // inject plugin
    private final Kalliergeia plugin;

    public CropTrampleListener(Kalliergeia plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCropTrample(PlayerInteractEvent event) {
        // Prevents the player from trampling crops if they have the setting disabled
        if(event.getAction() == Action.PHYSICAL) {
            if(event.getClickedBlock() == null) return;

            if(event.getClickedBlock().getType() == Material.FARMLAND) {
                if(!plugin.getSQLManager().getPlayer(event.getPlayer().getUniqueId().toString()).isCropTrample()) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
