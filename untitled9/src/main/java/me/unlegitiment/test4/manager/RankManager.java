package me.unlegitiment.test4.manager;

import me.unlegitiment.test4.objects.Rank;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nullable;
import java.io.IOException;

public class RankManager {
    private final Rank DEFAULT = new Rank("DEFAULT","DEFAULT",0,"","","","");
    private final FileManager fileManager;
    /*
    FOR THIS CLASS PLEASE MAKE IT FOCUSED ON THE RANK AND NOT ON FILES!!!!!!!!!!!!!!!!!!!!!!!!!!

     */
    public RankManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }
    public void rankSetup(Player p){
        FileConfiguration fC = fileManager.getFileConfFromFile(fileManager.getFileFromPlayer(p));
        if(!fC.contains("player.ranks")){
            ConfigurationSection rank = fC.createSection("player.ranks");
            rankSet(p,null);
        } else {
            Rank r = rankGet(p);
            teamSetup(p);
        }
        try {
            fC.save(fileManager.getFileFromPlayer(p));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void rankSet(Player p, @Nullable Rank r){
        FileConfiguration fC = fileManager.getFileConfFromFile(fileManager.getFileFromPlayer(p));
        if(fC.getConfigurationSection("player.ranks") != null){
            ConfigurationSection ranks = fC.getConfigurationSection("player.ranks");
            if(r == null){
                ranks.set("name", DEFAULT.getName());
                ranks.set("type", DEFAULT.getType());
                ranks.set("rankval", DEFAULT.getRankVal());
                ranks.set("prefix", DEFAULT.getPrefix());
                ranks.set("suffix", DEFAULT.getSuffix());
                ranks.set("prefixColor", DEFAULT.getPrefixColorz());
                ranks.set("suffixColor", DEFAULT.getSuffixColorz());
                try {
                    fC.save(fileManager.getFileFromPlayer(p));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            ranks.set("name", r.getName());
            ranks.set("type", r.getType());
            ranks.set("rankval", r.getRankVal());
            ranks.set("prefix", r.getPrefix());
            ranks.set("suffix", r.getSuffix());
            ranks.set("prefixColor", r.getPrefixColorz());
            ranks.set("suffixColor", r.getSuffixColorz());
            try {
                fC.save(fileManager.getFileFromPlayer(p));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            ConfigurationSection ranks = fC.createSection("player.ranks");
            if(r==null){
                ranks.set("name", DEFAULT.getName());
                ranks.set("type", DEFAULT.getType());
                ranks.set("rankval", DEFAULT.getRankVal());
                ranks.set("prefix", DEFAULT.getPrefix());
                ranks.set("suffix", DEFAULT.getSuffix());
                ranks.set("prefixColor", DEFAULT.getPrefixColorz());
                ranks.set("suffixColor", DEFAULT.getSuffixColorz());
                try {
                    fC.save(fileManager.getFileFromPlayer(p));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            ranks.set("name", r.getName());
            ranks.set("type", r.getType());
            ranks.set("rankval", r.getRankVal());
            ranks.set("prefix", r.getPrefix());
            ranks.set("suffix", r.getSuffix());
            ranks.set("prefixColor", r.getPrefixColorz());
            ranks.set("suffixColor", r.getSuffixColorz());
            try {
                fC.save(fileManager.getFileFromPlayer(p));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Rank rankGet(Player p) {
        FileConfiguration fC = fileManager.getFileConfFromFile(fileManager.getFileFromPlayer(p));
        Rank r;
        if(fC.getConfigurationSection("player.ranks") == null){
            //ConfigurationSection ranks = fC.createSection("player.ranks");
            rankSet(p, null);
            return new Rank(DEFAULT.getName(), DEFAULT.getType(), DEFAULT.getRankVal(),null,null,null,null);
        } else {
            ConfigurationSection ranks = fC.getConfigurationSection("player.ranks");
            String name = ranks.getString("name");
            String type = ranks.getString("type");
            int rankval = ranks.getInt("rankval");
            String prefix = ranks.getString("prefix");
            String suffix = ranks.getString("suffix");
            String prefixColor = ranks.getString("prefixColor");
            String suffixColor = ranks.getString("suffixColor");
            r = new Rank(name,type,rankval,prefix,suffix,prefixColor,suffixColor);
            return r;
        }
    }

    //Might be easier to just create an armor stand and move it when the player moves?

    //That or mount the player? (try tomorrow as its NMS based and probably will take a while lmao)
    public void teamSetup(Player p){
        FileConfiguration fileConfiguration = fileManager.getFileConfFromFile(fileManager.getFileFromPlayer(p));
        if(!fileConfiguration.contains("player.ranks")) {
            rankSet(p,null);
            return;
        }
        ConfigurationSection rank = fileConfiguration.getConfigurationSection("player.ranks");
        String s = rank.getString("name");
        Scoreboard scoreboard = p.getScoreboard();
        if(scoreboard.getTeam(s) == null){
            Team t = scoreboard.registerNewTeam(s);
            t.setPrefix(rank.getString("prefix"));
            t.setColor(chatColor(rank.getString("prefix")));
            t.addEntry(p.getName());
        } else {
            Team t = scoreboard.getTeam(s);
            t.addEntry(p.getName());
        }
    }
    public ChatColor chatColor(String s){
        //char colorChar = ChatColor.COLOR_CHAR;
        if(s.toCharArray().length == 0){
            return ChatColor.GRAY;
        }
        String z = s.substring(0, 2);
        switch(z) {
            case "&0":
                return ChatColor.BLACK;
            case "&1":
                return ChatColor.DARK_BLUE;
            case "&2":
                return ChatColor.DARK_GREEN;
            case "&3":
                return ChatColor.DARK_AQUA;
            case "&4":
                return ChatColor.DARK_RED;
            case "&5":
                return ChatColor.DARK_PURPLE;
            case "&6":
                return ChatColor.GOLD;
            case "&7":
                return ChatColor.GRAY;
            case "&8":
                return ChatColor.DARK_GRAY;
            case "&9":
                return ChatColor.BLUE;
            case "&a":
                return ChatColor.GREEN;
            case "&b":
                return ChatColor.AQUA;
            case "&c":
                return ChatColor.RED;
            case "&d":
                return ChatColor.LIGHT_PURPLE;
            case "&e":
                return ChatColor.YELLOW;
            case "&f":
                return ChatColor.WHITE;
        }
        return ChatColor.valueOf(z);
    }
}
