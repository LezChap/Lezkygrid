package net.lez.skygrid.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import net.lez.skygrid.Main;

public class CommandSGAdmin implements CommandExecutor {
    private final Main plugin;
    private final Logger logger = Bukkit.getLogger();
    
    public CommandSGAdmin(Main plugin) {
        this.plugin = plugin;
    }
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length < 1) {
            sender.sendMessage("Not enough arguments, check syntax and try again.");
            return false;
        }
        
        if (args[0].equalsIgnoreCase("setspawn")) {
            Location newSpawn = p.getLocation();
            if (newSpawn.getWorld().getName().equalsIgnoreCase(plugin.getConfig().getString("WorldName"))) {
                newSpawn.getWorld().setSpawnLocation(newSpawn.getBlockX(), newSpawn.getBlockY(), newSpawn.getBlockZ());
                sender.sendMessage("New spawn set to: " + newSpawn.getBlockX() + ", " + newSpawn.getBlockY() + ", " + newSpawn.getBlockZ() + ".");
                return true;
            } else {
                sender.sendMessage("You cannot run this command in this world.  Please enter the SkyGrid world or fix your configs.");
                return true;
            }
        }
        
        if (args[0].equalsIgnoreCase("reload")) {
            plugin.loadConfigs();
            sender.sendMessage("Configs Reloaded!");
        }
        
        if (args[0].equalsIgnoreCase("resetconfigs")) {
            plugin.resetToDefaults();
            sender.sendMessage("Configs reset to default, and reloaded!");
        }
        
        /*if (args[0].equalsIgnoreCase("testegg")) {
            ItemStack stack = new ItemStack(Material.MONSTER_EGG, 1);
            PotionMeta eff = (PotionMeta) stack.getItemMeta();
            PotionData potData = new PotionData(PotionType.WATER);
            eff.setBasePotionData(potData);
            Set<Material> transparent = null;
            Block block = player.getTargetBlock(transparent, 10);
            if (block.getType() == Material.BREWING_STAND){
                BrewingStand stand = (BrewingStand) block.getState();
                BrewerInventory inv = stand.getInventory();
                inv.addItem(stack);
                sender.sendMessage("Added waterbottle to the stand.");
            } else {
                sender.sendMessage("Look at a brewing stand, dip shit!");
            }
            return true;
        } */
        if (args.length < 2) {
            sender.sendMessage("Not enough arguments, check syntax and try again.");
            return false;
        }
        
        if (args[0].equalsIgnoreCase("savechest")) {
            String path = "ChestLoot.";
            switch(args[1].toUpperCase()) {
                case "COMMON":
                    path = path + "Common";
                    break;
                case "UNCOMMON":
                    path = path + "Uncommon";
                    break;
                case "RARE":
                    path = path + "Rare";
                    break;
                case "SPAWNEGG":
                    path = path + "SpawnEgg";
                    break;
                default:
                    sender.sendMessage("Invalid Command Syntax!");
                    return false;
            }
            Set<Material> transparent = null;
            Block block = p.getTargetBlock(transparent, 10);
            if (block.getType() == Material.CHEST) {
                plugin.lootConfig.set(path, getChestContents(block));
                plugin.saveContainerConfig();
                logger.info("Chest contents saved to config at: " + path);
                sender.sendMessage("Chest contents saved to config.");
            } else {
                sender.sendMessage("Not looking at a chest, cannot save anything!");
            }
            return true;
        } else if (args[0].equalsIgnoreCase("savebrewstand")) {
            String path = "BrewingStandLoot.";
            switch(args[1].toUpperCase()) {
                case "COMMON":
                    path = path + "Common";
                    break;
                case "UNCOMMON":
                    path = path + "Uncommon";
                    break;
                case "RARE":
                    path = path + "Rare";
                    break;
                default:
                    sender.sendMessage("Invalid Command Syntax!");
                    return false;
            }
            Set<Material> transparent = null;
            Block block = p.getTargetBlock(transparent, 10);
            if (block.getType() == Material.CHEST) {                
                plugin.lootConfig.set(path, getChestContents(block));
                plugin.saveContainerConfig();
                logger.info("Chest contents saved to config at: " + path);
                sender.sendMessage("Chest contents saved to config at: " + path);
            } else {
                sender.sendMessage("Not looking at a chest, cannot save anything!");
            }
            return true;
            
        }
        if (args.length < 3) {
            sender.sendMessage("Not enough arguments, check syntax and try again.");
            return false;
        }
        
        if (args[0].equalsIgnoreCase("loadchest")) {
            String path;
            if (args[1].equalsIgnoreCase("ChestLoot")) {
                path = "ChestLoot.";
            } else if (args[1].equalsIgnoreCase("BrewingStandLoot")) {
                path = "BrewingStandLoot.";
            } else {
                sender.sendMessage("Invalid Command Syntax!");
                return false;
            }
            switch(args[2].toUpperCase()) {
                case "COMMON":
                    path = path + "Common";
                    break;
                case "UNCOMMON":
                    path = path + "Uncommon";
                    break;
                case "RARE":
                    path = path + "Rare";
                    break;
                case "SPAWNEGG":
                    path = path + "SpawnEgg";
                    break;
                default:
                    sender.sendMessage("Invalid Command Syntax!");
                    return false;
            }
            Set<Material> transparent = null;
            Block block = p.getTargetBlock(transparent, 10);
            if (block.getType() == Material.CHEST) {
                Chest chest = (Chest) block.getState();
                List<?> list = new ArrayList<ItemStack>();
                list = (ArrayList<?>) plugin.lootConfig.get(path);
                ItemStack[] contents;
                contents = new ItemStack[list.size()];
                contents = list.toArray(contents);
                
                if (chest.getInventory().getHolder() instanceof DoubleChest) {
                    DoubleChest doubleChest = (DoubleChest) chest.getInventory().getHolder();
                   
                    doubleChest.getInventory().clear();
                    doubleChest.getInventory().setContents(contents);
                    sender.sendMessage("Pasted loot table from " + path + " into doublechest.");
                    logger.info(sender.getName() + "Pasted loot table from " + path + " into doublechest.");
                }
                // single chest too small!
                else if (!(chest.getInventory().getHolder() instanceof DoubleChest) && contents.length > 27) {    
                    sender.sendMessage("Loot table too big for a single chest, use doublechest!");
                }        
                // plenty of room, go ahead!
                else if (contents.length <= 27) {
                    chest.getInventory().clear();
                    chest.getInventory().setContents(contents);
                    sender.sendMessage("Pasted loot table from " + path + " into Chest.");
                    logger.info(sender.getName() + "Pasted loot table from " + path + " into Chest.");
                }
            } else {
                sender.sendMessage("Not looking at chest, cannot perform this command!");
            }
            return true;
        } else {
        sender.sendMessage("Syntax incorrect or something went wrong.");    
        return false;
        }
    }
    
    public List<ItemStack> getChestContents(Block block) {
        Chest chest = (Chest) block.getState();
        List<ItemStack> contents = new ArrayList<ItemStack>();
        if(chest.getInventory().getHolder() instanceof DoubleChest) {
            DoubleChest doubleChest = (DoubleChest) chest.getInventory().getHolder();
            contents.addAll(Arrays.asList(doubleChest.getInventory().getContents()));
        }
        else {
            contents.addAll(Arrays.asList(chest.getInventory().getContents()));
        }   
        contents.removeAll(Collections.singleton(null));
        return contents;
    }
}
