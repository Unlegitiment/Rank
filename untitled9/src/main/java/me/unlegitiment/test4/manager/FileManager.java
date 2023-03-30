package me.unlegitiment.test4.manager;

import me.unlegitiment.test4.Test4;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class FileManager {
    private  File file;
    private  FileConfiguration customFile;
    private static Test4 test4;
    //private Plugin PLUGIN = Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder();
    /*
    MUST FIX FILESETUP!
    FILE SETUP NO LONGER DEALS WITH RANKS! EDIT RANKS IN RANKMANAGER!
     */
    private RankManager rankManager;

    public FileManager(Test4 core) {
        test4 = core;
        this.rankManager = new RankManager(this);
    }
    public static Test4  getTest4() {
        return test4;
    }
    public void folderCreate(){
        File f = Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder();
        if(f.exists()) {
            getTest4().LOGGER.log(Level.INFO, "Folder Exists Stopping Folder Create!");
            return;
        }
        Bukkit.getServer().getPluginManager().getPlugin("test4").getConfig().options().copyDefaults(true);
        Bukkit.getPluginManager().getPlugin("test4").saveDefaultConfig();


        for(File fz : f.listFiles()){
            if(fz.getName().equals("config.yml")){
                fz.delete();
            }
        }
    }
    public File getFileFromPlayer(Player p){
        String s = p.getUniqueId() + ".yml";
        File folder = Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder();
        File f = new File(folder,s);
        if(folder.listFiles() == null){
            Bukkit.getLogger().log(Level.WARNING, "I'm not sure what this means lmao XD");
            return null;
        }
        if(folder.listFiles().length == 0){
            Bukkit.getLogger().log(Level.SEVERE, "NO FILES FOUND!");
            if(!f.exists()){
                try {
                    f.createNewFile();
                    FileConfiguration fC = getFileConfFromFile(f);
                    fileSetup(fC,p);
                    fC.save(f);
                    return f;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for(File z : folder.listFiles()){
            if(z.getName().equals(s)){
                return z;
            }
        }
        if(!f.exists()){
            try {
                f.createNewFile();
                FileConfiguration fileConfFromFile = getFileConfFromFile(f);
                fileSetup(fileConfFromFile,p);
                fileConfFromFile.save(f);
                return f;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
        return null;
    }

    public FileConfiguration getFileConfFromFile(File f){
        FileConfiguration fC = YamlConfiguration.loadConfiguration(f);
        try {
            fC.save(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fC;
    }
    public void fileSetup(FileConfiguration fC,Player p ){
        ConfigurationSection player = fC.createSection("player");
        ConfigurationSection names = player.createSection("names");
        names.set("displayName",p.getDisplayName());
        names.set("basicName",p.getName());
        names.set("customName",p.getCustomName());
        names.set("tabName",p.getPlayerListName());
        ConfigurationSection location = player.createSection("location");
        location.set("world",p.getWorld().getEnvironment().name());
        location.set("coords.x",p.getLocation().getX());
        location.set("coords.y",p.getLocation().getY());
        location.set("coords.z",p.getLocation().getZ());
        location.set("coords.pitch",p.getLocation().getPitch());
        location.set("coords.yaw",p.getLocation().getYaw());
        rankManager.rankSet(p,null);
    }
    public RankManager getRankManager() {
        return rankManager;
    }
}
