package me.unlegitiment.test4.listeners;

import me.unlegitiment.test4.manager.FileManager;
import me.unlegitiment.test4.manager.RankManager;
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
import java.util.logging.Level;

public class JoinandLeave implements Listener {
    private final FileManager fileManager;
    private final RankManager rankManager;
    public JoinandLeave(FileManager fileManager) {
        this.fileManager = fileManager;
        this.rankManager = new RankManager(fileManager);

    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        onJoinFile(p);
        File f = fileManager.getFileFromPlayer(p);
        FileConfiguration fC = YamlConfiguration.loadConfiguration(f);
        fileManager.fileSetup(fC,p);
        FileManager.getTest4().Startup(p);
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
        File folder = Bukkit.getPluginManager().getPlugin("test4").getDataFolder();
        File f = new File(folder, p.getUniqueId() + ".yml");
        FileConfiguration fC;
        if(!f.exists()){
            try {
                f.createNewFile();
                fC = YamlConfiguration.loadConfiguration(f);
                fC.save(f);
                fileManager.fileSetup(fC,p);
                fC.save(f);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            try {
                fC = YamlConfiguration.loadConfiguration(f);
                fC.save(f);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
    private void onJoinFile(Player p){
        File folder = Bukkit.getServer().getPluginManager().getPlugin("test4").getDataFolder();
        File f = new File(folder, p.getUniqueId() + ".yml");
        FileConfiguration fC;
        if(!f.exists()){
            try {
                f.createNewFile();
                fC = YamlConfiguration.loadConfiguration(f);
                fC.save(f);
                fileManager.fileSetup(fC, p);
                fC.save(f);
            } catch (IOException ex) {
                Bukkit.getLogger().log(Level.SEVERE, "Could Not Get Make File for Player UUID: " + p.getUniqueId());
            }
        } else {
            fC = YamlConfiguration.loadConfiguration(f);
            try {
                fC.save(f);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


}
