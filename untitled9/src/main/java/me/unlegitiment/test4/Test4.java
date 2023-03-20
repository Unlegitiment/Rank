package me.unlegitiment.test4;

import me.unlegitiment.test4.commands.rank;
import me.unlegitiment.test4.commands.test;
import me.unlegitiment.test4.commands.test2;
import me.unlegitiment.test4.listeners.JoinPacket;
import me.unlegitiment.test4.listeners.JoinandLeave;
import me.unlegitiment.test4.manager.FileManager;
import me.unlegitiment.test4.manager.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Test4 extends JavaPlugin {
    private final FileManager fileManager;
    public final Logger LOGGER;
    private final RankManager rankManager;
    private final JoinPacket joinPacket;

    public Test4() {

        this.fileManager = new FileManager(this);
        this.rankManager = new RankManager(fileManager);
        this.joinPacket = new JoinPacket(fileManager);

        LOGGER = Bukkit.getLogger();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new JoinandLeave(fileManager),this);
        getCommand("testDEV").setExecutor(new test(fileManager));
        getCommand("rank").setExecutor(new rank(rankManager));
        getCommand("test2").setExecutor(new test2(fileManager));
        getServer().getPluginManager().registerEvents(new JoinPacket(fileManager),this);
        fileManager.folderCreate();

        for(Player p : Bukkit.getOnlinePlayers()){
            if(Bukkit.getOnlinePlayers().isEmpty()) break;
            File f  = fileManager.getFileFromPlayer(p);
            FileConfiguration fC = fileManager.getFileConfFromFile(f);
            ConfigurationSection rank = fC.getConfigurationSection("player.rank");
            p.setCustomName(ChatColor.translateAlternateColorCodes('&',rank.getString("prefix")) + p.getName());
        }


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
}
