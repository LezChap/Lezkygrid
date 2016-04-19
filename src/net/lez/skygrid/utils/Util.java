package net.lez.skygrid.utils;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

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
    
    public static boolean isSafeLoc(Location loc, Boolean WaterOK) {
        if (loc == null) {
            return false;
        }
        
        Block below = loc.getBlock().getRelative(BlockFace.DOWN);
        Block level = loc.getBlock();
        Block upper = loc.getBlock();
        Block[] space = new Block[] {below, level, upper};
        
        if (below.getType().equals(Material.AIR)) {
            return false;
        }
             
        for (Block b : space) {
            if (b.getType().equals(Material.LAVA) || b.getType().equals(Material.STATIONARY_LAVA)) {
                return false;
            }
            if (b.getType().equals(Material.WATER) || b.getType().equals(Material.STATIONARY_WATER)) {
                if (!WaterOK) {
                    return false;
                } 
            }
            if (b.getType().equals(Material.PORTAL) || b.getType().equals(Material.ENDER_PORTAL)) {
                return false;
            }    
        }
        
        if (below.getType().equals(Material.CACTUS) || below.getType().equals(Material.FENCE) || below.getType().equals(Material.ACACIA_FENCE) || below.getType().equals(Material.BIRCH_FENCE) || below.getType().equals(Material.DARK_OAK_FENCE) || below.getType().equals(Material.IRON_FENCE) || below.getType().equals(Material.JUNGLE_FENCE) || below.getType().equals(Material.NETHER_FENCE) || below.getType().equals(Material.SPRUCE_FENCE) || below.getType().equals(Material.SIGN_POST) || below.getType().equals(Material.WALL_SIGN)) {
            return false;
        }
        
        if (level.getType().isSolid() && (!level.getType().equals(Material.SIGN_POST) || !level.getType().equals(Material.WALL_SIGN))) {
            return false;
        }
        if (upper.getType().isSolid() && (!upper.getType().equals(Material.SIGN_POST) || !upper.getType().equals(Material.WALL_SIGN))) {
            return false;
        } 
        return true;
    }
    
    public static void makeTempCage(Location loc, int ticks) {
        Block b1 = loc.getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.UP);
        Block b2 = loc.getBlock().getRelative(BlockFace.EAST).getRelative(BlockFace.UP);
        Block b3 = loc.getBlock().getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP);
        Block b4 = loc.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP);
        final Block[] blocks = new Block[] {b1, b2, b3, b4};
        
        for (Block b : blocks) {
            if (b.getType().equals(Material.AIR)) {
                b.setType(Material.BARRIER);
            }
        }
        
        new BukkitRunnable() {
            Block[] blocks2 = blocks;
            
            @Override
            public void run() {
                for (Block b : blocks2) {
                    if (b.getType().equals(Material.BARRIER)) {
                        b.setType(Material.AIR);
                    }
                }
            }
            
        }.runTaskLater(plugin, ticks);
    }
    
    public static void makeTempCage(Location loc) {
        makeTempCage(loc, 20);
    }
}
