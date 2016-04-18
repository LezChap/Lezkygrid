package net.lez.skygrid.commands;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.lez.skygrid.Main;

public class CommandSkygrid implements CommandExecutor {
    
    private final Main plugin;
    @SuppressWarnings("unused")
    private final Logger logger = Bukkit.getLogger();
    
    public CommandSkygrid(Main plugin) {
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
        
        if (args[0].equalsIgnoreCase("spawn")) {
            sender.sendMessage("Taking you to SkyGrid Spawn!");
            World world = plugin.getServer().getWorld(plugin.getConfig().getString("WorldName"));
            if (!world.isChunkLoaded(world.getSpawnLocation().getChunk())) {
                world.loadChunk(world.getSpawnLocation().getChunk());
            }
            p.teleport(world.getSpawnLocation());
            return true;
        }
        
        if (args[0].equalsIgnoreCase("randomLoc")) {
            Location spawn = plugin.getServer().getWorld(plugin.getConfig().getString("WorldName")).getSpawnLocation();
            Random rand = new Random();
            int xDist = rand.nextInt(Math.round((plugin.getConfig().getInt("SpawnRadius.x") / 4)));
            int zDist = rand.nextInt(Math.round(plugin.getConfig().getInt("SpawnRadius.z") / 4));
            double yCoord = (double) (plugin.getConfig().getInt("SpawnHeight.Min") + rand.nextInt(plugin.getConfig().getInt("SpawnHeight.Max") - plugin.getConfig().getInt("SpawnHeight.Min")));
            if (rand.nextBoolean()) { xDist = -xDist; }
            if (rand.nextBoolean()) { zDist = -zDist; }
            xDist = xDist * 4;
            zDist = zDist * 4;
            Location loc = new Location(spawn.getWorld(), (double) spawn.getBlockX() + xDist, yCoord, (double) spawn.getBlockZ() + zDist);
            loc.getWorld().loadChunk(loc.getChunk().getX(), loc.getChunk().getZ(), true);
            for (int i = 0; i < 8; i++) {
                Block testBlock = loc.getBlock().getRelative(BlockFace.DOWN);
                if (testBlock.isEmpty() || testBlock.isLiquid()) {
                    loc.setY(loc.getY() + 1);
                } else {
                    break;
                }
            }
            Block b = loc.getBlock().getRelative(BlockFace.DOWN);
            if (b.isEmpty() || b.isLiquid()) {
                sender.sendMessage("Could not find a solid teleport location.  Please try again.");
                return true;
            }
            sender.sendMessage("Finding and teleporting you to a random location within the configuration radius of: " + plugin.getConfig().getInt("SpawnRadius.x") + ", " + plugin.getConfig().getInt("SpawnRadius.x") + ".");
            if (!loc.getWorld().isChunkLoaded(loc.getChunk())) {
                loc.getWorld().loadChunk(loc.getChunk());
            }
            p.teleport(loc);
            return true;
        }
        
        if (args[0].equalsIgnoreCase("sethome")) {
            Location loc = p.getLocation();
            if (!(loc.getWorld().getName().equalsIgnoreCase(plugin.getConfig().getString("WorldName")))) {
                sender.sendMessage("Can only make a home location in the SkyGrid world: " + plugin.getConfig().getString("WorldName"));
                sender.sendMessage("You are currently in world: " + loc.getWorld().getName() + ".  Please move to the correct world, or have an admin fix your configs.");
                return true;
            } else {
                if (args.length <= 1 || args[1].isEmpty() || args[1]==null) {
                    if (plugin.getPlayers().getHomeCount(p.getUniqueId()) == 0) {
                        plugin.getPlayers().setHomeLoc(p.getUniqueId(), loc); 
                        sender.sendMessage("Set current location: " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + "for home: \"home\"");
                        return true;
                    } else {
                        sender.sendMessage("Must input label for this home. (example: /skygrid sethome <label>)");
                        return true;
                    }
                } else if (args[1].length() < 20) {
                        if (plugin.getPlayers().canAddHome(p.getUniqueId())) {
                            plugin.getPlayers().setHomeLoc(p.getUniqueId(), loc, args[1]);
                            sender.sendMessage("Set current location: " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + "for home: " + args[1] + ".");
                            return true;
                        } else {
                            sender.sendMessage("You have too many set homes.  Please remove or replace one of them.");
                            return true;
                        }
                } else {
                    sender.sendMessage("Home name too long, cannot set home.  Home label cannot be more than 20 characters.");
                    return true;
                }
            } 
        }
        
        if (args[0].equalsIgnoreCase("delhome")) {
            if (args.length <= 1 || args[1].isEmpty() || args[1]==null) {
                sender.sendMessage("Please input a home to remove.  (/skygrid delhome <label>)");
                return true;
            } else {
                if (plugin.getPlayers().isHome(p.getUniqueId(), args[1])) {
                    plugin.getPlayers().removeHome(p.getUniqueId(), args[1]);
                    sender.sendMessage("Removed the home labeled \"" + args[1] + "\" from your list of homes.");
                    return true;
                } else {
                    sender.sendMessage(args[1] + " is not a valid home location, please try again.");
                    return true;
                }
            }
        }
        
        if (args[0].equalsIgnoreCase("home")) {
            if (args.length <= 1 || args[1].isEmpty() || args[1]==null) {
                if (plugin.getPlayers().isHome(p.getUniqueId(), "home") && plugin.getPlayers().getHomeCount(p.getUniqueId()) == 1) {
                    sender.sendMessage("Teleporting you to your home location.");
                    Location loc = plugin.getPlayers().getHomeLoc(p.getUniqueId(), "home");
                    if (!loc.getWorld().isChunkLoaded(loc.getChunk())) {
                        loc.getWorld().loadChunk(loc.getChunk());
                    }
                    p.teleport(loc);
                    return true;
                } else if (plugin.getPlayers().getHomeCount(p.getUniqueId()) >= 1){
                    sender.sendMessage("Please input the home location you want to go to. (/skygrid home <label>)");
                    sender.sendMessage("List of homes: " + plugin.getPlayers().getHomeList(p.getUniqueId()));
                    return true;
                } else {
                    sender.sendMessage("Do you have any homes?  I can't find any!");
                    return true;
                }
            } else {
                if (plugin.getPlayers().isHome(p.getUniqueId(), args[1])) {
                    sender.sendMessage("Teleporting you to the home labeled \"" + args[1] + "\".");
                    Location loc = plugin.getPlayers().getHomeLoc(p.getUniqueId(), args[1]);
                    if (!loc.getWorld().isChunkLoaded(loc.getChunk())) {
                        loc.getWorld().loadChunk(loc.getChunk());
                    }
                    p.teleport(loc);
                    return true;
                } else if (plugin.getPlayers().getHomeCount(p.getUniqueId()) < 1) {
                    sender.sendMessage("Do you have any homes?  I can't find any!");
                } else {
                    sender.sendMessage(args[1] + " is not a valid home location, please try again.");
                    sender.sendMessage("List of homes: " + plugin.getPlayers().getHomeList(p.getUniqueId()));
                    return true;
                }
            }
        }
        return false;
    }
}
