package me.adelemphii.kalliergeia.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) {

            List<String> argsList = new ArrayList<>();
            argsList.add("force");
            argsList.add("reload");

            return argsList;
        }
        if (args.length == 2) {
            return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
        }
        if (args.length == 3) {
            return Collections.singletonList("trample");
        }
        if (args.length == 4) {
            List<String> argsList = new ArrayList<>();
            argsList.add("true");
            argsList.add("false");

            return argsList;
        }
        return null;
    }
}
