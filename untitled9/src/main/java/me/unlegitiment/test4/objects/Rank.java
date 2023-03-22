package me.unlegitiment.test4.objects;

import me.unlegitiment.test4.manager.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

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
        return name;
    }
    protected String getNamefromPlayer(Player p ){
        File f = fileManager.getFileFromPlayer(p);
        FileConfiguration fC = fileManager.getFileConfFromFile(f);
        ConfigurationSection ranks = fC.getConfigurationSection("player.ranks."+ getName());
        if(!ranks.contains(getName())) return "null";
        return ranks.getConfigurationSection(getName()).getName();
    }
    public static Rank getRank(Player p){
        FileManager fM = new FileManager(FileManager.getTest4());
        File f = fM.getFileFromPlayer(p);
        FileConfiguration fC = YamlConfiguration.loadConfiguration(f);
        ConfigurationSection rank = fC.getConfigurationSection("player.ranks." + Rank.getRank(p).getName());
        Rank r = new Rank(rank.getName(),rank.getString("type"), rank.getInt("rankval"),rank.getString("prefix"),(ChatColor) rank.get("prefixColor"));
        try {
            fC.save(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return r;
    }
    public Rank setName(String name) {
        this.name = name;
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

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public ChatColor getPrefixColor() {
        return prefixColor;
    }

    public ChatColor getSuffixColor() {
        return suffixColor;
    }
}
