package me.unlegitiment.test4.listeners;

import me.unlegitiment.test4.manager.FileManager;
import me.unlegitiment.test4.objects.Rank;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Team;

public class ChatManage implements Listener {
    private final FileManager filemanager;

    public ChatManage(FileManager filemanager) {
        this.filemanager = filemanager;
    }

    @EventHandler
    public void asyncChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        Rank r = filemanager.getRankManager().rankGet(p);
        Team team = filemanager.getRankManager().returnRankTeam(r.getName());

        String finals = ChatColor.translateAlternateColorCodes('&',r.getPrefix());
        if(finals.equals("")){
            e.setFormat(team.getPrefix() + p.getName() + ChatColor.RESET + ": " + e.getMessage());
            return;
        }
        e.setFormat(team.getPrefix() + p.getName() + ChatColor.RESET + ": " + e.getMessage());
        filemanager.updateAllRanks(filemanager.getFileFromPlayer(p));
    }
}
