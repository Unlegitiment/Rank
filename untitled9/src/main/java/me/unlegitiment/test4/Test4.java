package me.unlegitiment.test4;

import me.unlegitiment.test4.commands.conversionChatColor;
import me.unlegitiment.test4.commands.rank;
import me.unlegitiment.test4.commands.test;
import me.unlegitiment.test4.commands.test2;
import me.unlegitiment.test4.listeners.JoinandLeave;
import me.unlegitiment.test4.manager.FileManager;
import me.unlegitiment.test4.manager.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.Set;
import java.util.logging.Logger;

public final class Test4 extends JavaPlugin {
    private final FileManager fileManager;
    public final Logger LOGGER;
    private final RankManager rankManager;
    public Test4() {

        this.fileManager = new FileManager(this);
        this.rankManager = new RankManager(fileManager);

        LOGGER = Bukkit.getLogger();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new JoinandLeave(fileManager),this);
        getCommand("testDEV").setExecutor(new test(fileManager));
        getCommand("rank").setExecutor(new rank(rankManager));
        getCommand("test2").setExecutor(new test2(fileManager));
        getCommand("convertcolor").setExecutor(new conversionChatColor());
        fileManager.folderCreate();
        //LOGGER.log(Level.WARNING, getServer().getPluginManager().getPlugin("test4").getName()+" HAS START");
    }

    @Override
    public void onDisable() {
        onShutDown();
    }
    private void onShutDown() {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard sc = scoreboardManager.getMainScoreboard();
        Set<Team> teams = sc.getTeams();
        for(Team t : teams){
            t.unregister();
        }
    }

    private void convertColor(ConfigurationSection rank){
        String prefix = rank.getString("prefixColor");
        prefix = prefix.replaceAll("&0", ChatColor.BLACK + "");
        prefix = prefix.replaceAll("&1", ChatColor.DARK_BLUE + "");
        prefix = prefix.replaceAll("&2", ChatColor.DARK_GREEN + "");
        prefix = prefix.replaceAll("&3", ChatColor.DARK_AQUA + "");
        prefix = prefix.replaceAll("&4", ChatColor.DARK_RED + "");
        prefix = prefix.replaceAll("&5", ChatColor.DARK_PURPLE + "");
        prefix = prefix.replaceAll("&6", ChatColor.GOLD + "");
        prefix = prefix.replaceAll("&7", ChatColor.GRAY + "");
        prefix = prefix.replaceAll("&8", ChatColor.DARK_GRAY+ "");
        prefix = prefix.replaceAll("&9", ChatColor.BLUE + "");
        prefix = prefix.replaceAll("&a", ChatColor.GREEN + "");
        prefix = prefix.replaceAll("&b", ChatColor.AQUA + "");
        prefix = prefix.replaceAll("&c", ChatColor.RED + "");
        prefix = prefix.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
        prefix = prefix.replaceAll("&e", ChatColor.YELLOW + "");
        prefix = prefix.replaceAll("&f", ChatColor.WHITE + "");
        prefix = prefix.replaceAll("&g", ChatColor.MAGIC + "");
        rank.set("prefixColor",prefix);

    }
}
