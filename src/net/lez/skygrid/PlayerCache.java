package net.lez.skygrid;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import com.google.common.collect.ImmutableList;

public class PlayerCache {
    private Main plugin;
    private HashMap<UUID, Players> playerCache = new HashMap<UUID, Players>();
    @SuppressWarnings("unused")
    private Logger logger = Bukkit.getLogger();

    public PlayerCache(Main plugin) {
        this.plugin = plugin;
        for (Player p : getOnlinePlayers()) {
            if (p.isOnline()) {
                Players playerTmp = new Players(plugin, p.getUniqueId());
                this.playerCache.put(p.getUniqueId(), playerTmp);
            }
        }
    }
    
    public static List<Player> getOnlinePlayers() {
        return ImmutableList.copyOf(Bukkit.getServer().getOnlinePlayers());
    }
    
    public void addPlayer(UUID uuid) {
        /*logger.info("uuid: " + uuid.toString());
        if (this.plugin == null) {
            logger.info("this.plugin is NULL in PlayerCache.addPlayer(uuid)");
        }
        if (plugin == null) {
            logger.info("plugin is NULL in PlayerCache.addPlayer(uuid)");
        }
        if (this.playerCache == null) {
            logger.info("this.playerCache is NULL in PlayerCache.addPlayer(uuid)");
        }
        if (playerCache == null) {
            logger.info("playerCache is NULL in PlayerCache.addPlayer(uuid)");
        }*/
        if (!this.playerCache.containsKey(uuid)) {
            Players player = new Players(this.plugin, uuid);
            this.playerCache.put(uuid, player);
        }
    }
    
    public void removeOnlinePlayer(UUID uuid) {
        if (this.playerCache.containsKey(uuid)) {
            ((Players) this.playerCache.get(uuid)).save();
            this.playerCache.remove(uuid);
        }
    }
    
    public void removeAllPlayers() {
        saveAll();
        this.playerCache.clear();    
    }
    
    public boolean isKnownPlayer(UUID uuid) {
        if (uuid == null) {
            return false;   
        }
        if (this.playerCache.containsKey(uuid)) {
            return true;
        }
        File plFolder = this.plugin.getPlFolder();
        Iterator<File> files = FileUtils.iterateFiles(plFolder, new WildcardFileFilter("*.yml"), null);
        while(files.hasNext()) {
            File f = files.next();
            if (UUID.fromString(FilenameUtils.removeExtension(f.getName())).equals(uuid)) {
                return true;
            }
        }
        return false;
    }
    
    public Players get(UUID uuid) {
        addPlayer(uuid);
        return (Players)this.playerCache.get(uuid);
    }
    
    public boolean hasSpawn(UUID uuid) {
        addPlayer(uuid);
        return ((Players)this.playerCache.get(uuid)).hasSpawn();
    }
    
    public void setHasSpawn(UUID uuid, boolean bool) {
        addPlayer(uuid);
        ((Players)this.playerCache.get(uuid)).setHasSpawn(bool);
    }
    
    public void clearPlayerData(UUID uuid) {
        addPlayer(uuid);
        ((Players)this.playerCache.get(uuid)).clearHomeLocs();
        ((Players)this.playerCache.get(uuid)).setHasSpawn(false);
        ((Players)this.playerCache.get(uuid)).save();
    }
    
    public void setHomeLoc(UUID uuid, Location loc, String homeName) {
        addPlayer(uuid);
        ((Players)this.playerCache.get(uuid)).setHomeLoc(loc, homeName);
    }
    
    public void setHomeLoc(UUID uuid, Location loc) {
        addPlayer(uuid);
        setHomeLoc(uuid, loc, "home");
    }
    
    public int getHomeCount(UUID uuid) {
        addPlayer(uuid);
        return ((Players)this.playerCache.get(uuid)).getHomeCount();
    }
    
    public boolean canAddHome(UUID uuid) {
        addPlayer(uuid);        
        return ((Players)this.playerCache.get(uuid)).canAddHome();
    }
    
    public void clearHomeLocs(UUID uuid) {
        addPlayer(uuid);
        ((Players)this.playerCache.get(uuid)).clearHomeLocs();
    }
    
    public void removeHome(UUID uuid, String homeName) {
        addPlayer(uuid);
        ((Players)this.playerCache.get(uuid)).removeHome(homeName);
    }
    
    public Location getHomeLoc(UUID uuid, String homeName) {
        addPlayer(uuid);
        return ((Players)this.playerCache.get(uuid)).getHomeLoc(homeName);
    }
    
    public boolean isHome(UUID uuid, String homeName) {
        addPlayer(uuid);
        return ((Players)this.playerCache.get(uuid)).isHome(homeName);
    }
    
    public Location getHomeLoc(UUID uuid) {
        addPlayer(uuid);
        return ((Players)this.playerCache.get(uuid)).getHomeLoc("home");
    }
    
    public TreeMap<String, Location> getHomeLocs(UUID uuid) {
        addPlayer(uuid);
        return ((Players)this.playerCache.get(uuid)).getHomeLocs();
    }
    
    public String getHomeList(UUID uuid) {
        addPlayer(uuid);
        return ((Players)this.playerCache.get(uuid)).getHomeList();
    }
    
    public void save(UUID uuid) {
        ((Players)this.playerCache.get(uuid)).save();
    }
    
    public void saveAll() {
        for (UUID uuid : this.playerCache.keySet()) {
            ((Players) this.playerCache.get(uuid)).save();
        }
    }
    
    @SuppressWarnings("deprecation")
    public UUID getUUID(String name) {
        for (UUID id : this.playerCache.keySet()) {
            String DBname = ((Players)this.playerCache.get(id)).getPlayerName();
            if ((DBname != null) && (DBname.equalsIgnoreCase(name))) {
                return id;
            }
        }
        if (this.plugin.getServer().getOfflinePlayer(name) != null) {
            return this.plugin.getServer().getOfflinePlayer(name).getUniqueId();
        }
        return null;
    }
    
    public void setPlayerName(UUID uuid, String name) {
        addPlayer(uuid);
        ((Players)this.playerCache.get(uuid)).setPlayerName(name);
    }
    
    public String getName(UUID uuid) {
        if (uuid == null) {
            return "";
        }
        addPlayer(uuid);
        return ((Players)this.playerCache.get(uuid)).getPlayerName();
    }
}
