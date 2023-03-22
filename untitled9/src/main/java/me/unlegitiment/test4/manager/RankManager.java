package me.unlegitiment.test4.manager;

import me.unlegitiment.test4.objects.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        this.fileManager = fileManager;
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
    public void baseRank(Player p, Rank r){
        File f = fileManager.getFileFromPlayer(p);
        FileConfiguration fC = YamlConfiguration.loadConfiguration(f);
        if(!fC.contains("player.ranks")) {
            fC.createSection("player.ranks");
        }
        ConfigurationSection configurationSection = fC.getConfigurationSection("player.ranks");
        configurationSection.set("name",r.getName());
        configurationSection.set("prefix",r.getPrefix());
        configurationSection.set("prefixColor",r.getPrefixColor().toString());
        configurationSection.set("type",r.getType());
        configurationSection.set("rankval",r.getRankVal());
        try {
            fC.save(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Rank getRank(Player p){
        FileManager fM = new FileManager(FileManager.getTest4());
        FileConfiguration fC = fM.getFileConfFromFile(fM.getFileFromPlayer(p));
        RankManager rM = new RankManager(fM);

        ConfigurationSection configurationSection = fC.getConfigurationSection("player.ranks");
        String name = configurationSection.getString("name");
        String prefix = configurationSection.getString("prefix");
        Object prefixColor = configurationSection.get("prefixColor");
        String type = configurationSection.getString("type");
        int rankval = configurationSection.getInt("rankval");
        Rank r = new Rank(name,type,rankval,prefix, ChatColor.RED);
        return r;
    }
}
