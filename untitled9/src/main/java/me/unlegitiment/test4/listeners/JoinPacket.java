package me.unlegitiment.test4.listeners;

import me.unlegitiment.test4.manager.FileManager;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;

public class JoinPacket implements Listener {
    private final FileManager fileManager;

    public JoinPacket(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @EventHandler
    private void packetSend(PlayerJoinEvent e){
        Player p =  e.getPlayer();
        File f = fileManager.getFileFromPlayer(p);
        FileConfiguration fC = fileManager.getFileConfFromFile(f);
        ConfigurationSection rankCS = fC.getConfigurationSection("player.rank");
        Scoreboard sc = p.getScoreboard();
        Team rank = sc.registerNewTeam("OWNER");
        rank.setPrefix("[OWNER] ");
        String colorCode = ChatColor.stripColor(rankCS.getString("prefix"));
        p.sendMessage("Color: " + colorCode);
        //rank.setColor();
        rank.addEntry(p.getName());
        ClientboundPlayerInfoUpdatePacket


    }

}
