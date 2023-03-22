package me.unlegitiment.test4.manager;

import me.unlegitiment.test4.Test4;
import me.unlegitiment.test4.objects.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public class FileManager {
    private  File file;
    private  FileConfiguration customFile;
    private static Test4 test4;
    //private Plugin PLUGIN = Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder();

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
    public void setup(String fileName){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder(),fileName +".yml");
        if(!file.exists()){
            try{
                file.createNewFile();
            } catch (IOException e) {
                Bukkit.getPluginManager().disablePlugin(test4);
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }
    public FileConfiguration getCustomFile(){
        return customFile;
    }
    @Deprecated
    public FileConfiguration getFilefromUUID(UUID u) {
        String fName = u.toString() + ".yml";
        File folder = Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder();
        for(File file : folder.listFiles()){
            String ffName = file.getName();
            if(ffName.equals(fName)){
                File f = new File(fName);
                FileConfiguration fC = YamlConfiguration.loadConfiguration(f);
                return fC;

            }
        }
        return null;
    }
    public File getFileFromPlayer(Player p){
        String s = p.getUniqueId() + ".yml";
        File folder = Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder();
        if(folder.listFiles().length == 0){
            File f = new File(folder,s);
            try {
                f.createNewFile();
                FileConfiguration fC = getFileConfFromFile(f);
                fC.save(f);
                fileSetup(fC, p);
                fC.save(f);
                return f;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            for(int i =0; i <= folder.listFiles().length; i++){
                File f = folder.listFiles()[i];
                if(f.getName().equals(s)){
                    return f;
                }
            }
        }
        return null;
    }
    @Deprecated
    public File getFileFromUUID(UUID uuid){
        String s = uuid + ".yml";
        File folder = Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder();
        if(folder.listFiles().length == 0 ) {
            File f = new File(folder, s);
            try {
                f.createNewFile();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for(File f : Objects.requireNonNull(folder.listFiles())){
            if(f.getName().equals(s)){ return f; } else {
                try {
                    f = new File(folder, s);
                    f.createNewFile();
                    return f;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public void saveCustomFile(){
        try {
            customFile.save(file);
        } catch (IOException e) {
            test4.LOGGER.log(Level.SEVERE, "Couldn't Save File Continuing Operation");
        }
    }
    public void saveYMLFile(File f){
        FileConfiguration fC = YamlConfiguration.loadConfiguration(f);
        try {
            fC.save(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public  void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }
    public File returnFileFromPlayer (Player p){
        File folder = Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder();
        for (File f : folder.listFiles()){
            if(f.getName().equals(String.valueOf(p.getUniqueId()) + ".yml")){
                p.sendMessage(f.getAbsolutePath());
            }
        }
        File f = new File(folder,String.valueOf(p.getUniqueId()) + ".yml");
        try {
            f.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return f;
    }
    public FileConfiguration returnFileConfFromPlayer(Player p){
        File folder = Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder();
        for (File f : folder.listFiles()){
            if(f.getName().equals(String.valueOf(p.getUniqueId()) + ".yml")){
                p.sendMessage(f.getAbsolutePath());
            }
        }
        File f = new File(folder,String.valueOf(p.getUniqueId()) + ".yml");
        try {
            f.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileConfiguration fC = YamlConfiguration.loadConfiguration(f);
        try {
            fC.save(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fC;
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
        if(p.getName().equals("Unlegitiment")){
            Rank r = new Rank("OWNER", "OWNER", 100, "[OWNER]", ChatColor.RED);
            rankManager.baseRank(p,r);
        }
        /*Need to rework above statement to create a new rank everytime that its initialized which is whenever a new
        * player joins*/
    }

    public RankManager getRankManager() {
        return rankManager;
    }
}
