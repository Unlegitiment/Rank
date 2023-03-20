package me.unlegitiment.test4.commands;

import me.unlegitiment.test4.manager.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class test2 implements CommandExecutor {
    private FileManager fileManager;

    public test2(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!command.getName().equals("test2")) return false;
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            p.sendMessage("UUID: " + p.getUniqueId() + "\nBasic Name: " + p.getName() + "\nDisplay Name: "+ p.getDisplayName() + "\nCustomName: " + p.getCustomName());
            p.sendMessage("File Associated with your account: \n" + fileManager.getFileFromPlayer(p));
        } else {
            Bukkit.getLogger().log(Level.WARNING, "You must be on the server to access this command!");
            return false;
        }
        return true;
    }


}
