package me.adelemphii.kalliergeia.utils;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ChatUtils {

    /**
     * formats error message to be sent to a user
     *
     * @param player player to send error message to
     * @param error error message to send to user
     *
     */
    public static void errorMessage(Player player, String error) {
        player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + ChatColor.ITALIC + ChatColor.translateAlternateColorCodes('&', error));
        player.playSound(player.getLocation(), Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 1F, 1F);
    }

    /**
     * formats correct syntax for user to use
     *
     * @param player user to send syntax error to
     * @param correctSyntax the right syntax to use
     *
     */
    public static void syntaxError(Player player, String correctSyntax) {
        player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "Syntax Error: " + ChatColor.ITALIC + ChatColor.translateAlternateColorCodes('&', correctSyntax));
        player.playSound(player.getLocation(), Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 1F, 1F);
    }

    /**
     * formats message to be sent to a user
     * @param player player to send message to
     * @param message message to send to user
     */
    public static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.GREEN + ChatColor.ITALIC + ChatColor.translateAlternateColorCodes('&', message));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F);
    }
}
