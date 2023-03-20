package me.unlegitiment.test4.objects;

import me.unlegitiment.test4.manager.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nullable;
import java.io.File;

public class Rank {
    private String name;
    private String type;
    private int rankVal;
    private String prefix;
    private String suffix;
    private ChatColor prefixColor;
    private ChatColor suffixColor;

    private FileManager fileManager;
    boolean suffixActive = false;
    public Rank(String name, String type, int rankVal, String prefix, @Nullable String suffix, ChatColor prefixColor, ChatColor suffixColor) {
        this.name = name;
        this.type = type;
        this.rankVal = rankVal;
        this.prefix = prefix;
        this.suffix = suffix;
        this.prefixColor = prefixColor;
        this.suffixColor = suffixColor;
        this.fileManager = new FileManager(FileManager.getTest4());
    }
    public Rank(String name, String type, int rankVal, String prefix, ChatColor prefixColor){
        this.name = name;
        this.type = type;
        this.rankVal = rankVal;
        this.prefix = prefix;
        this.prefixColor = prefixColor;
        this.fileManager = new FileManager(FileManager.getTest4());
    }
    public Rank(String name, String type, int rankVal){
        this.name = name;
        this.type = type;
        this.rankVal = rankVal;
        this.fileManager = new FileManager(FileManager.getTest4());
    }
    public Rank(String name, String type, int rankVal, String suffix, ChatColor suffixColor,boolean suffixActive){
        this.name = name;
        this.type = type;
        this.rankVal = rankVal;
        this.suffix = suffix;
        this.suffixColor = suffixColor;
        this.suffixActive = suffixActive;
        this.fileManager = new FileManager(FileManager.getTest4());
    }


    public String getName() {
        File f = fileManager.getRankManager().getRanksFile();
        FileConfiguration fC = YamlConfiguration.loadConfiguration(f);
        ConfigurationSection path = fC.getConfigurationSection("ranks."+name);
        name = path.getName();
        return name;
    }

    public Rank setName(String name) {
        this.name = name;
        File f  = fileManager.getRankManager().getRanksFile();
        FileConfiguration fC = YamlConfiguration.loadConfiguration(f);
        ConfigurationSection path = fC.createSection("ranks."+name);
        return this;
    }

    public String getType() {
        return type;
    }

    public Rank setType(String type) {
        this.type = type;
        return this;
    }

    public int getRankVal() {
        return rankVal;
    }

    public Rank setRankVal(int rankVal) {
        this.rankVal = rankVal;
        return this;
    }
}
