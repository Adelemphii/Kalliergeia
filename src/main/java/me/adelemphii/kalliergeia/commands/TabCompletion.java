package me.adelemphii.kalliergeia.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) {
            // /kalliergeia <force/reload/help>
            List<String> argsList = new ArrayList<>();
            argsList.add("force");
            argsList.add("reload");
            argsList.add("help");

            return argsList;
        }
        if (args.length == 2) {
            // /kalliergeia <force> <player>
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        }
        if (args.length == 3) {
            // /kalliergeia force <player> <trample/replant>
            List<String> argsList = new ArrayList<>();
            argsList.add("trample");
            argsList.add("replant");

            return argsList;
        }
        if (args.length == 4) {
            // /kalliergeia force <player> <trample/replant> <true/false>
            List<String> argsList = new ArrayList<>();
            argsList.add("true");
            argsList.add("false");

            return argsList;
        }
        return null;
    }
}
