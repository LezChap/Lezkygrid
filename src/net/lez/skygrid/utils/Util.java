package net.lez.skygrid.utils;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import net.lez.skygrid.Main;

public class Util {
    private static Main plugin;
    
    public Util(Main plugin) {
        Util.plugin = plugin;        
    }
    
    public static YamlConfiguration loadYamlFile(String file) {
        File dataFolder = plugin.getDataFolder();
        File yamlFile = new File(dataFolder, file);
          
        YamlConfiguration config = null;
        if (yamlFile.exists()) {
            try {
                config = new YamlConfiguration();
                config.load(yamlFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            config = new YamlConfiguration();
            plugin.getLogger().info("No " + file + " found. Creating it...");
            try {
                if (plugin.getResource(file) != null) {
                    plugin.getLogger().info("Using default found in jar file.");
                    plugin.saveResource(file, false);
                    config = new YamlConfiguration();
                    config.load(yamlFile);
                } else {
                    config.save(yamlFile);
                }
            } catch (Exception e) {
                plugin.getLogger().severe("Could not create the " + file + " file!");
            }
        }
        return config;
    }
    
    public static void saveYamlFile(YamlConfiguration yamlFile, String fileLocation) {
        File dataFolder = plugin.getDataFolder();
        File file = new File(dataFolder, fileLocation);
        try {
            yamlFile.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
