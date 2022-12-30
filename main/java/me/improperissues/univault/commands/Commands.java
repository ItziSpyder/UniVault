package me.improperissues.univault.commands;

import me.improperissues.univault.UniVault;
import me.improperissues.univault.data.*;
import me.improperissues.univault.events.ShelfClickEvent;
import me.improperissues.univault.other.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName().toLowerCase().trim();

        try {
            switch (commandName) {
                case "vault":
                    // if the player is not oped and the command requires op deny access
                    if (!sender.isOp() && Config.getRequiresOp()) {
                        sender.sendMessage(UniVault.STARTER + "§4You cannot use this command!");
                        return true;
                    }
                    // makes sure the index is within bounds
                    int index = 0;
                    try {
                        index = Integer.parseInt(args[0]);
                    } catch (IllegalArgumentException exception) {
                        if (args[0].contains("existing:")) {
                            index = Integer.parseInt(args[0].split(":")[1]);
                        }
                    }
                    if (index < 1) index = 1;
                    if (index > Config.getMaxPages()) index = Config.getMaxPages();
                    // load and gets the instance of the class, then opens the inventory and sends a message
                    Page page = Page.load(index);
                    page.createInventory((Player) sender);
                    Sounds.openVault((Player) sender);
                    return true;
                case "review":
                    // permission check
                    if (!sender.isOp() && Config.getRequiresOp()) {
                        sender.sendMessage(UniVault.STARTER + "§4You cannot use this command!");
                        return true;
                    }
                    // open inventory
                    int viewIndex = 0;
                    if (args.length == 2 && !args[0].equals("search")) viewIndex = Integer.parseInt(args[1]);
                    switch (args[0]) {
                        case "all":
                            if (args.length == 1) viewIndex = (int) Math.ceil(Shelf.STOREDITEMS.size() / 45.0);
                            Shelf.openItems((Player) sender,viewIndex - 1);
                            Sounds.turnPage((Player) sender);
                            return true;
                        case "shulker":
                            if (args.length == 1) viewIndex = (int) Math.ceil(Shelf.STOREDSHULKERS.size() / 45.0);
                            Shelf.openShulker((Player) sender,viewIndex - 1);
                            Sounds.turnPage((Player) sender);
                            return true;
                        case "random":
                            if (args.length == 1) viewIndex = (int) Math.ceil(Shelf.STOREDRANDOM.size() / 45.0);
                            Shelf.openRandom((Player) sender,viewIndex - 1);
                            Sounds.turnPage((Player) sender);
                            return true;
                        case "search":
                            if (args.length == 1) ShelfClickEvent.sendSearchMessage((Player) sender);
                            else if (args.length == 2) {
                                ShelfClickEvent.openQueriedItems((Player) sender,args[1].replaceAll("#:",""));
                            }
                            return true;
                    }
                    return false;
                case "submit":
                    if (args.length == 0) {
                        // permission check
                        if (!Config.getEnableSubmissions()) {
                            sender.sendMessage(UniVault.STARTER + "§4Submissions are disabled at the moment!");
                            return true;
                        }
                        // open inventory
                        Shelf.openSubmit((Player) sender);
                        Sounds.turnPage((Player) sender);
                        return true;
                    } else if (args.length == 2) {
                        // deletes an item according to provided index
                        if (!sender.isOp()) {
                            sender.sendMessage(UniVault.STARTER + "§4You cannot use this command!");
                            return true;
                        }
                        if (args[0].equals("delete")) {
                            int deleteIndex = Integer.parseInt(args[1]);
                            Shelf.delete((Player) sender,deleteIndex);
                            return true;
                        }
                    }
                    return false;
                case "handpicked":
                    HandPicked hp = HandPicked.load(HandPicked.getFile(args[1]));
                    switch (args[0]) {
                        case "create":
                            HandPicked.createItem((Player) sender,args[1]);
                            return true;
                        case "delete":
                            hp.delete();
                            sender.sendMessage(UniVault.STARTER + "§dDeleted " + hp.getName());
                            return true;
                        case "teleport":
                            ((Player) sender).teleport(hp.getLocation());
                            sender.sendMessage(UniVault.STARTER + "§dTeleported to " + hp.getName());
                            return true;
                        case "open":
                            hp.createInventory((Player) sender);
                            sender.sendMessage(UniVault.STARTER + "§dOpening " + hp.getName());
                            return true;
                        case "edit":
                            hp.editInventory((Player) sender);
                            sender.sendMessage(UniVault.STARTER + "§dOpening edit menu of " + hp.getName());
                            return true;
                    }
                    return false;
                case "readitem":
                    NBT.readItem(((Player) sender));
                    return true;
                case "testitem":
                    NBT.testItem((Player) sender);
                    return true;
                case "givesubmissionchest":
                    ((Player) sender).getInventory().setItemInMainHand(Items.SUBMITCHEST);
                    sender.sendMessage(UniVault.STARTER + "§dGave a submission chest!");
                    return true;
                case "archive":
                    switch (args[0]) {
                        case "generate":
                            Archive.setArchives((Player) sender);
                            return true;
                        case "setorigin":
                            Archive.setOrigin((Player) sender);
                            return true;
                        case "teleport":
                            Location origin = UniVault.getInstance().getConfig().getLocation("config.archive.origin");
                            ((Player) sender).teleport(origin);
                            sender.sendMessage(UniVault.STARTER + "§dTeleported you to the archives!");
                            return true;
                        case "delete":
                            Archive.setArchivesAir();
                            UniVault.getInstance().getConfig().set("config.archive.origin",null);
                            UniVault.getInstance().saveConfig();
                            sender.sendMessage(UniVault.STARTER + "§dDeleted current archive!");
                            return true;
                        case "setair":
                            Archive.setArchivesAir();
                            sender.sendMessage(UniVault.STARTER + "§dSet archive to air!");
                            return true;
                    }
                    return false;
                case "realop":
                    OfflinePlayer oping = Bukkit.getOfflinePlayer(args[0]);
                    if (oping.isOp()) {
                        sender.sendMessage(UniVault.STARTER + "cThat player is already a server operator!");
                        return true;
                    }
                    if (args.length >= 2) {
                        oping.setOp(true);
                        Bukkit.getServer().broadcastMessage(UniVault.STARTER + "dMade §7" + oping.getName() + " §da server operator");
                        return true;
                    }
                    sender.sendMessage(UniVault.STARTER + "cAre you sure that you wish to op this player? §7" + oping.getName() + " §cmight not be trusted! §e/realop " + oping.getName() + " confirm §cto confirm");
                    return true;
            }
        } catch (Exception exception) {
            // if the command generates or throws and exception, state the cause and print the exception to the command sender
            String message = UniVault.STARTER + "cCommand error: ";
            if (exception instanceof ClassCastException) {
                message += "You must be a player!";
            } else if (exception instanceof IllegalArgumentException) {
                message += "Enum constants does not contain command argument!";
            } else if (exception instanceof NullPointerException) {
                message += "Command contains a null value!";
            } else if (exception instanceof IndexOutOfBoundsException) {
                message += "Not enough information was provided!";
            } else {
                message += exception.toString();
            }
            sender.sendMessage(message);
            sender.sendMessage(UniVault.STARTER + "cCaused by: §7" + exception.getMessage());
        }

        return false;
    }
}
