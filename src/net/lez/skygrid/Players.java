package net.lez.skygrid;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import net.lez.skygrid.utils.Util;

public class Players {
    private Main plugin;
    private UUID uuid;
    private boolean hasSpawned;
    private TreeMap<String, Location> homeLocs;
    private String playerName;
    private YamlConfiguration playerData;
    private Logger logger = Bukkit.getLogger();
    
    
    public Players(Main plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.hasSpawned = false;
        this.homeLocs = new TreeMap<String, Location>();
        this.playerName = "";
        
        load(uuid);
    }

    private void load(UUID uuid) {
        this.playerData = Util.loadYamlFile("players" + File.separator + uuid.toString() + ".yml");
        
        this.playerName = this.playerData.getString("playerName", "");
        if (this.playerName.isEmpty()) {
            try {
                this.playerName = this.plugin.getServer().getOfflinePlayer(uuid).getName();
            } catch (Exception e) {
                logger.severe("Could not obtain a name for the player with UUID " + uuid.toString());
                this.playerName = "";
            }
            if (this.playerName == null) {
                logger.severe("Could not obtain a name for the player with UUID " + uuid.toString());
                this.playerName = "";
            }
        }
        
        this.hasSpawned = this.playerData.getBoolean("hasSpawned", false);
        
        TreeMap<String, Location> homeList = new TreeMap<String, Location>();
        List<String> HomeNameList = this.playerData.getStringList("HomeNameList");
        for (String name : HomeNameList) {
            homeList.put(name, (Location) this.playerData.get("homeLocs." + name));
        }
        if (!(homeList == null)) {
            this.homeLocs = homeList;
        }
    }

    public boolean hasSpawned() {
        return this.hasSpawned;
    }

    public void save() {
        this.playerData.set("playerName", this.playerName);
        this.playerData.set("hasSpawned", Boolean.valueOf(this.hasSpawned));
        
        List<String> HomeNameList = new ArrayList<String>();
        for (String name : homeLocs.keySet()) {
            HomeNameList.add(name);
            this.playerData.set("homeLocs." + name, homeLocs.get(name));
        }
        
        this.playerData.set("HomeNameList", HomeNameList.toArray());
        
        Util.saveYamlFile(this.playerData, "players" + File.separator + this.uuid.toString() + ".yml");
    }

    public void setHasSpawned(boolean bool) {
        this.hasSpawned = bool;       
    }
    
    public int getHomeCount() {
        return this.homeLocs.size();
    }
    
    public boolean canAddHome() {
        if (getHomeCount() < plugin.getConfig().getInt("MaxHomes")) {
            return true;
        }
        if (plugin.getServer().getPlayer(uuid).hasPermission("lezkygrid.skygrid.sethome.unlimited")){
            return true;
        }
        return false;
    }

    public void setHomeLoc(Location loc, String homeName) {
        if (loc == null) {
            this.homeLocs.remove(homeName);
        }
        this.homeLocs.put(homeName, loc);
    }

    public void clearHomeLocs() {
        this.homeLocs.clear();
    }

    public void removeHome(String homeName) {
        this.playerData.set("homeLocs." + homeName, null);
        this.homeLocs.remove(homeName);
    }
    
    public Location getHomeLoc() {
        return this.homeLocs.get("home");
    }

    public Location getHomeLoc(String homeName) {
        return this.homeLocs.get(homeName);
    }
    
    public boolean isHome(String homeName) {
        return this.homeLocs.containsKey(homeName);
    }

    public TreeMap<String, Location> getHomeLocs() {
        return this.homeLocs;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }
    
    public UUID getPlayerUUID() {
        return this.uuid;
    }
    
    public void setPlayerUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getHomeList() {
        String homeListStr = "";
        Iterator<String> homeList = new TreeSet<String>(homeLocs.keySet()).iterator();
        while (homeList.hasNext()) {
            homeListStr = homeListStr + homeList.next();
            if (homeList.hasNext()) {
                homeListStr = homeListStr + ", ";
            }
        }
        
        return homeListStr;
    }
}
