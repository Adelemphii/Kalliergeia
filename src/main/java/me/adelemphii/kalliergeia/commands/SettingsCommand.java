package me.adelemphii.kalliergeia.commands;

import me.adelemphii.kalliergeia.Kalliergeia;
import me.adelemphii.kalliergeia.utils.ChatUtils;
import me.adelemphii.kalliergeia.utils.gui.SettingsMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCommand implements CommandExecutor {

    Kalliergeia plugin;
    public SettingsCommand(Kalliergeia plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("kalliergeia")) {

            if (sender instanceof Player player) {

                if (!player.hasPermission("kalliergeia.settings")) {
                    ChatUtils.errorMessage(player, "You don't have permission to use this command!");
                    return false;
                }

                if (args.length == 0) {
                    new SettingsMenu(plugin, player).openGUI();
                    return true;
                }

                if(args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        if (player.hasPermission("kalliergeia.reload")) {
                            plugin.reloadConfig();
                            ChatUtils.sendMessage(player, "Kalliergeia config reloaded!");
                            return true;
                        }
                    }
                }

                if (args.length >= 4) {
                    if (args[0].equalsIgnoreCase("force")) {
                        if(player.hasPermission("kalliergeia.settings.mod")) {
                            if (Bukkit.getPlayer(args[1]) != null) {
                                Player target = Bukkit.getPlayer(args[1]);

                                if (args[2].contains("trample")) {
                                    assert target != null;
                                    plugin.getSQLManager().getPlayer(target.getUniqueId().toString()).setCropTrample(args[3].equalsIgnoreCase("true"));
                                    ChatUtils.sendMessage(target, player.getDisplayName() + " &a&oforced &c'Crop Trample' &a&oto " + args[3].equalsIgnoreCase("true"));
                                    ChatUtils.sendMessage(player, "Successfully forced &c'Crop Trample' &a&oto " + args[3].equalsIgnoreCase("true") + " for " + target.getDisplayName());
                                    return true;
                                }
                            }
                        } else {
                            ChatUtils.errorMessage(player, "You don't have permission to use this command!");
                            return false;
                        }
                    } else {
                        ChatUtils.syntaxError(player, "/kalliergeia force <player> <setting> <true/false>");
                        return false;
                    }
                }
                return false;
            }

            return false;
        }
        return false;
    }
}
