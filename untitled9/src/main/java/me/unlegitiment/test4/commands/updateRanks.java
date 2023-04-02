package me.unlegitiment.test4.commands;

import me.unlegitiment.test4.manager.FileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class updateRanks implements CommandExecutor {
    private final FileManager fileManager;

    public updateRanks(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player p = (Player) commandSender;

        fileManager.updateAllRanks(fileManager.getFileFromPlayer(p));
        return true;

    }
}
