package me.unlegitiment.test4.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class conversionChatColor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;
        Player p = (Player) commandSender;
        if(strings.length == 0) return true;
        String z = strings[0];
        String convert = ChatColor.translateAlternateColorCodes('&', z);
        //ChatColor convert2 = ChatColor.valueOf(z);
        String l = z.substring(0,2);
        p.sendMessage(ChatColor.YELLOW + "You sent: " + z);
        p.sendMessage(ChatColor.GREEN + "After Conversion: " + convert);
        p.sendMessage(l);
        //p.sendMessage(String.valueOf(convert2));
        System.out.println(convert);

        return true;
    }
}