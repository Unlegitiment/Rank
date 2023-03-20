package me.unlegitiment.test4.commands;

import me.unlegitiment.test4.manager.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class test implements CommandExecutor {
    private FileManager filemanager;

    public test(FileManager fileManager) {
        this.filemanager = fileManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!command.getName().equals("testDEV")) return false;
        if(commandSender instanceof Player){
            Player p = (((Player) commandSender).getPlayer());
            assert p != null;
            File f = filemanager.returnFileFromPlayer(p);
            FileConfiguration fC = filemanager.getFileConfFromFile(f);
            fC.set("player.loc", p.getLocation());
            try {
                fC.save(f);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            File folder = Bukkit.getPluginManager().getPlugin("test4").getDataFolder();
            for(File f : Objects.requireNonNull(folder.listFiles())){
                System.out.println(f.getAbsolutePath());

            }
            System.out.println("PATH OF DATA FOLDER: " + folder.getAbsolutePath());
        }
        return true;
    }
}
