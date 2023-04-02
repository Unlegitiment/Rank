package me.unlegitiment.test4.listeners;

import me.unlegitiment.test4.manager.FileManager;
import me.unlegitiment.test4.manager.RankManager;
import me.unlegitiment.test4.objects.Rank;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;

public class JoinandLeave implements Listener {
    private final FileManager fileManager;
    private final RankManager rankManager;
    public JoinandLeave(FileManager fileManager) {
        this.fileManager = fileManager;
        this.rankManager = new RankManager(fileManager);

    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) throws IOException {
        Player p = e.getPlayer();
        onJoinFile(p);
    }
    @EventHandler
    private void onKick(PlayerKickEvent e){
        Player p = e.getPlayer();
        onQuitorExitFile(p);
    }
    @EventHandler
    private void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        onQuitorExitFile(p);
    }
    private void onQuitorExitFile(Player p){
        FileConfiguration fC = fileManager.getFileConfFromFile(fileManager.getFileFromPlayer(p));

        try {
            fC.save(fileManager.getFileFromPlayer(p));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void onJoinFile(Player p) {
        File folder = Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder();
        File f = fileManager.getFileFromPlayer(p);
        FileConfiguration fC = YamlConfiguration.loadConfiguration(f);
        Rank r = rankManager.rankGet(p);
        rankManager.teamSetup(p);
        //FileManager.getTest4().onStartUp();
        p.sendMessage(r.getName() + r.getType() + r.getPrefixColorz() + r.getPrefix() + r.getSuffixColorz() + r.getSuffix() + r.getRankVal());
    }


}
