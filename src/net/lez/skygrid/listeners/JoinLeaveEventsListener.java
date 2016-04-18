package net.lez.skygrid.listeners;

import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.lez.skygrid.Main;
import net.lez.skygrid.PlayerCache;

public class JoinLeaveEventsListener implements Listener {
    private Main plugin;    
    public PlayerCache players;
    private Logger logger = Bukkit.getLogger();
    
    public JoinLeaveEventsListener(Main plugin){
        this.plugin = plugin;
        players = this.plugin.getPlayers();
        /*if (players == null) {
            logger.severe("(PlayerCache) players is NULL in Listener Constructor");
        }*/
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player p = event.getPlayer();
        final UUID uuid = p.getUniqueId();
        /*logger.info("p.getName()=" + p.getName());
        if (players == null) {
            logger.severe("(PlayerCache) players is NULL");
        }*/
        
        if (!p.getName().isEmpty()) {
            players.setPlayerName(uuid, p.getName());
        } else {
            logger.warning("Player that just logged in has no name! " + uuid.toString());
        }
        players.save(uuid);
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        players.removeOnlinePlayer(event.getPlayer().getUniqueId());
    }
}
