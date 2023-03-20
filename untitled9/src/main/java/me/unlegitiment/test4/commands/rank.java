package me.unlegitiment.test4.commands;

import me.unlegitiment.test4.manager.FileManager;
import me.unlegitiment.test4.manager.RankManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class rank implements CommandExecutor {
    private FileManager fileManager;
    private final RankManager rankManager;
    private FileConfiguration fC;

    public rank(RankManager rankManager) {
        this.fileManager = new FileManager(fileManager.getTest4());
        this.rankManager = rankManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!command.getName().equals("rank")) return false;
        if(!(commandSender instanceof Player)) return false;

        Player p = ((Player) commandSender).getPlayer();
        if(strings.length == 0) {
            p.sendMessage(ChatColor.RED +"args not provided");
            p.sendMessage("Retry and put: /rank set prefix OR ");
            return false;
        }
            if(strings[0].equalsIgnoreCase("set")&&strings[1].equalsIgnoreCase("type")){
            assert p != null;
            File f = fileManager.getFileFromPlayer(p);
            fC = YamlConfiguration.loadConfiguration(f);
            ConfigurationSection rank = fC.getConfigurationSection("player.rank");
            rank.set("type",strings[2]);
                try {
                    fC.save(f);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                p.sendMessage("Set rank TYPE to " + String.valueOf(fC.get("player.rank.type")));
            return true;
        }else if (strings[0].equalsIgnoreCase("set")&&strings[1].equalsIgnoreCase("prefix")){
            File f = fileManager.getFileFromPlayer(p);
            fC = YamlConfiguration.loadConfiguration(f);
            ConfigurationSection rank = fC.getConfigurationSection("player.rank");
            fC.set("player.rank.prefix",strings[2]);
                try {
                    fC.save(f);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                p.sendMessage("Set rank " + ChatColor.BOLD + ChatColor.GREEN + "PREFIX " + ChatColor.RESET + "to " + String.valueOf(fC.get("player.rank.prefix")));
            return true;
        }
        return true;
    }
}
