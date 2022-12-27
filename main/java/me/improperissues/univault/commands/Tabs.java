package me.improperissues.univault.commands;

import me.improperissues.univault.data.HandPicked;
import me.improperissues.univault.data.Page;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class Tabs implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        String commandName = command.getName().toLowerCase().trim();

        switch (commandName) {
            case "vault":
                switch (args.length) {
                    case 1:
                        if (args[0].length() < 1) {
                            list.add("1");
                            list.add("existing:");
                            break;
                        } else if (args[0].contains("existing")) {
                            for (int index : Page.getExistingIndexes()) {
                                list.add("existing:" + index);
                            }
                            break;
                        }
                        break;
                }
                break;
            case "review":
                switch (args.length) {
                    case 1:
                        list.add("shulker");
                        list.add("random");
                        list.add("all");
                        list.add("search");
                        break;
                    case 2:
                        if (!args[0].equals("search")) list.add("1"); else list.add("#:");
                        break;
                }
                break;
            case "submit":
                switch (args.length) {
                    case 1:
                        list.add("delete");
                        break;
                }
                break;
            case "handpicked":
                switch (args.length) {
                    case 1:
                        list.add("teleport");
                        list.add("delete");
                        list.add("create");
                        list.add("open");
                        list.add("edit");
                        break;
                    case 2:
                        return HandPicked.getAllChests();
                }
                break;
            case "readitem":
            case "testitem":
            case "givesubmissionchest":
                break;
            case "archive":
                switch (args.length) {
                    case 1:
                        list.add("setorigin");
                        list.add("generate");
                        list.add("teleport");
                        list.add("setair");
                        list.add("delete");
                        break;
                }
                break;
        }

        return list;
    }
}
