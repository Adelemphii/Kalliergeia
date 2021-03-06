package me.adelemphii.kalliergeia.events;

import me.adelemphii.kalliergeia.Kalliergeia;
import me.adelemphii.kalliergeia.utils.storage.UserSettings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    Kalliergeia plugin;
    public PlayerJoinListener(Kalliergeia plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        // Check if the player has a settings file on join, if not create one
        if(plugin.getSQLManager().getPlayer(event.getPlayer().getUniqueId().toString()) == null) {
            UserSettings userSettings = new UserSettings(event.getPlayer().getUniqueId().toString(), true, false);
            plugin.getSQLManager().addPlayer(userSettings);
        }
    }
}
