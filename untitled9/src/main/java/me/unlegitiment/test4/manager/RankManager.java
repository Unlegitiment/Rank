package me.unlegitiment.test4.manager;

import me.unlegitiment.test4.objects.Rank;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class RankManager {
    private FileManager fileManager;
    //private File BUKKITFOLDER = Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder();
    private String RANKSYML = "ranks.yml";
    //private String RANKINPUT;
    private Rank rank;

    public RankManager(FileManager fileManager) {
        this.fileManager = new FileManager(fileManager.getTest4());
    }
    public File getRanksFile(){
            File f = new File(Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder(), RANKSYML);
            if(!f.exists()){
                setUpRanksFile();
                return f;
            } else {
                return f;
            }
    }
    public void setUpRanksFile(){
        File f = new File(Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder(), RANKSYML);
        if(!f.exists()){
            try {
                f.createNewFile();
                FileConfiguration fC = YamlConfiguration.loadConfiguration(f);
                fC.save(f);
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void setUpRankData(){
        File f = new File(Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder(),RANKSYML);
        if(!f.exists()) setUpRanksFile();
        FileConfiguration fC = YamlConfiguration.loadConfiguration(f);
        //fC.createSection(createRank());
        try {
            fC.save(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public FileManager getFileManager() {
        return fileManager;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
    public void baseRank(Player p){
        File f = fileManager.getFileFromPlayer(p);
        FileConfiguration fC = YamlConfiguration.loadConfiguration(f);
        Rank r = new Rank("DEFAULT","null",0);
        ConfigurationSection zf = fC.createSection("player.ranks."+r.getName());
        zf.set("type",r.getType());
        zf.set("rankval",r.getRankVal());
        zf.set("prefix",r.getPrefix());
        try {
            fC.save(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
