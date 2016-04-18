package net.lez.skygrid;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import net.lez.skygrid.commands.CommandSGAdmin;
import net.lez.skygrid.commands.CommandSkygrid;
import net.lez.skygrid.listeners.JoinLeaveEventsListener;
import net.lez.skygrid.utils.Util;

public class Main extends JavaPlugin {
    public FileConfiguration config, lootConfig;
    private final Logger logger = getLogger();
    private PlayerCache players;
    @SuppressWarnings("unused")
    private Util util;
    private File plFolder = new File(getDataFolder() + File.separator + "Players");
    
     // Fired when plugin is first enabled
    @Override
    public void onEnable() {
        //Other Constructors
        this.players = new PlayerCache(this);
        this.util = new Util(this);
        
        if (this.players == null) {
            logger.severe("(PlayerCache) players is NULL in Main.onEnable()!");
        }
        //Get Configs:
        loadConfigs();
        // Register comands
        this.getCommand("sgadmin").setExecutor(new CommandSGAdmin(this));
        this.getCommand("skygrid").setExecutor(new CommandSkygrid(this));
        // Register Listener
        //getServer().getPluginManager().registerEvents(new GridListener(), this);
        getServer().getPluginManager().registerEvents(new JoinLeaveEventsListener(this), this);
    }

    // Fired when plugin is disabled
    @Override
    public void onDisable() {
        this.players.removeAllPlayers();
    }
    
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldname, String id) {
        return new SkyGridChunkGenerator(id, this);
    }
    
    public FileConfiguration getLootConfig() {
        return this.lootConfig;
    }
    
    public void saveContainerConfig() {
        File lootf = new File(getDataFolder(), "lootConfig.yml");
        try {
            lootConfig.save(lootf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
    public void loadConfigs() {
        File configf = new File(getDataFolder(), "config.yml");
        File lootf = new File(getDataFolder(), "lootConfig.yml");
        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            saveResource("config.yml", false);
        } 
        if (!lootf.exists()){
            lootf.getParentFile().mkdirs();
            saveResource("lootConfig.yml", false);
        }
        config = new YamlConfiguration();
        lootConfig = new YamlConfiguration();
        try {
            config.load(configf);
            lootConfig.load(lootf);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        if (!this.plFolder.exists()) {
            this.plFolder.mkdir();
        }
    }
    
    public void resetToDefaults() {
        saveResource("lootConfig.yml", true);
        saveResource("config.yml", true);
        loadConfigs();
    }
    
    @Override
    public void saveConfig() {
        logger.info("Attempted to use \"saveConfig()\". This feature is disabled for this mod.");
    }

    public PlayerCache getPlayers() {
        if (this.players == null) {
            logger.severe("(PlayerCache) players is NULL in Main.getPlayers()!");
        }
        return this.players;
    }

    public File getPlFolder() {
        return plFolder;
    }
}
