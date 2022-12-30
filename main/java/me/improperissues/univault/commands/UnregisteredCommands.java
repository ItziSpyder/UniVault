package me.improperissues.univault.commands;

import me.improperissues.univault.UniVault;
import me.improperissues.univault.data.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UnregisteredCommands implements Listener {

    static HashMap<String,Long> COMMAND_COOL = new HashMap<>();

    @EventHandler
    public static void PlayerCommandEvent(PlayerCommandPreprocessEvent e) {
        Player sender = e.getPlayer();
        List<String> preArgs = new ArrayList<>(Arrays.asList(e.getMessage().toLowerCase().trim().split(" ")));
        String command = preArgs.get(0);
        preArgs.remove(command);
        String[] args = preArgs.toArray(new String[0]);
        // register command
        if (!OnCommand(sender,command,args)) e.setCancelled(true);
    }

    public static boolean OnCommand(Player sender, String command, String[] args) {
        if (COMMAND_COOL.containsKey(sender.getName()) && COMMAND_COOL.get(sender.getName()) > System.currentTimeMillis()) return false;
        if (Config.getDelayBlacklist().contains(command)) COMMAND_COOL.put(sender.getName(),System.currentTimeMillis() + Config.getPlayerCommandDelay());
        try {
            switch (command) {
                case "/op":
                case "/minecraft:op":
                    sender.sendMessage("§cUnknown or incomplete command, type \"/help\" for help");
                    return false;
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

        return true;
    }
}
