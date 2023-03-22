package me.unlegitiment.test4;

import me.unlegitiment.test4.commands.rank;
import me.unlegitiment.test4.commands.test;
import me.unlegitiment.test4.commands.test2;
import me.unlegitiment.test4.listeners.JoinandLeave;
import me.unlegitiment.test4.manager.FileManager;
import me.unlegitiment.test4.manager.RankManager;
import me.unlegitiment.test4.objects.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.util.Objects;
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
        fileManager.folderCreate();

        for(Player p : Bukkit.getOnlinePlayers()){
            fileManager.fileSetup(fileManager.getFileConfFromFile(fileManager.getFileFromPlayer(p)), p);
            rankManager.baseRank(p,RankManager.getRank(p));
            //Startup(p);
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
    public void Startup(Player p){
        File f = fileManager.getFileFromPlayer(p);
        FileConfiguration fC = YamlConfiguration.loadConfiguration(f);
        ConfigurationSection rank = fC.getConfigurationSection("player.ranks");
        rank.get("prefix");
        Scoreboard scoreboard = p.getScoreboard();
        Team t = scoreboard.registerNewTeam(rank.getName());
        t.setColor((ChatColor) Objects.requireNonNull(rank.get("prefixColor")));
        t.setPrefix(Objects.requireNonNull(rank.getString("prefix")));
        t.addEntry(Objects.requireNonNull(fC.getString("player.names.basicName")));

    }

}
