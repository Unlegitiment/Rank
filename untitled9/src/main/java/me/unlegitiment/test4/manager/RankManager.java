package me.unlegitiment.test4.manager;

import me.unlegitiment.test4.objects.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.logging.Level;

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

    //That or mount the player? (try tomorrow as its NMS based and probably will take a while lmao) 4/1(Test unsuccessful. Sadly it takes too much time to mount a player however it would be very effective for tags that are
    // like smaller. I.E Silverfish, EnderMite etc.)
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
            String prefix = rank.getString("prefix");

            if(s.equals("DEFAULT")){
                t.setPrefix("");
                t.setColor(chatColor(rank.getString("prefix")));
                t.addEntry(p.getName());
                return;
            }
            Rank r = rankGet(p);
            ////////////YESSSSS
            t.setColor(chatColor(r.getPrefix()));
            t.setPrefix(ChatColor.translateAlternateColorCodes('&',r.getPrefix()));
            t.addEntry(p.getName());

        } else {
            Team t = scoreboard.getTeam(s);
            Rank r = rankGet(p);
            t.setColor(chatColor(r.getPrefix()));
            t.setPrefix(ChatColor.translateAlternateColorCodes('&',r.getPrefix()));
            t.addEntry(p.getName());
        }

    }
    public ChatColor chatColor(String s){
        //char colorChar = ChatColor.COLOR_CHAR;
        s=s.toLowerCase();
        if(s.toCharArray().length == 0){
            return ChatColor.GRAY;
        }
        String z = s.substring(0, 2);
        return switch (z) {
            case "&0" -> ChatColor.BLACK;
            case "&1" -> ChatColor.DARK_BLUE;
            case "&2" -> ChatColor.DARK_GREEN;
            case "&3" -> ChatColor.DARK_AQUA;
            case "&4" -> ChatColor.DARK_RED;
            case "&5" -> ChatColor.DARK_PURPLE;
            case "&6" -> ChatColor.GOLD;
            case "&7" -> ChatColor.GRAY;
            case "&8" -> ChatColor.DARK_GRAY;
            case "&9" -> ChatColor.BLUE;
            case "&a" -> ChatColor.GREEN;
            case "&b" -> ChatColor.AQUA;
            case "&c" -> ChatColor.RED;
            case "&d" -> ChatColor.LIGHT_PURPLE;
            case "&e" -> ChatColor.YELLOW;
            case "&f" -> ChatColor.WHITE;
            default -> ChatColor.valueOf(z);
        };
    }
    public Team returnRankTeam(String rankName){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if(scoreboard.getTeam(rankName) == null){
            Bukkit.getLogger().log(Level.SEVERE, "Could not get rank!");
            return null;
        } else {
            Team t = scoreboard.getTeam(rankName);
            return t;
        }
    }
}
