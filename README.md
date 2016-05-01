# Lezkygrid

A remake of the SkyGrid-style plugins, updated for Spigot 1.9.2.  To make a world, currently you need to use Multiverse (or other multiworld/generating plugin) and select "lezkygrid" as the world generator ("/mvc SkyGrid normal -g lezkygrid" on Multiverse 2.5).  Almost all generated blocks and loot tables can be configured.  If something doesn't work, post an issue so I can look into it.

Binary download available: https://www.spigotmc.org/resources/lezkygrid.22648/

## Skygrid's Player commands
 ```
    aliases: [skygrid, sg, lezkygrid, lezskygrid]
    usage: 
      /skygrid help (currently not implemented)
      /skygrid home --if only home is labeled "home", teleports you there, otherwise lists homes.
      /skygrid home <label> --teleports you to the labeled home location.
      /skygrid randomLoc --Teleports you to a random block in the configurated radius.  You may visually glitch for a second while the world loads, and are in an invisible cage for 20 ticks.
      /skygrid spawn -- Teleports you to Skygrid's world spawn.
      /skygrid sethome --if no homes are set, creates a home labeled "home"
      /skygrid sethome <label> --Sets a home to the given label.
      /skygrid delhome <label> --removes home with the given label from your homes.
```
## SkyGrid's Admin commands
```
    aliases: [sgadmin, sga, lezkygridadmin, lezskygridadmin]
    usage: |
      ****Player Only commands:****
      /sgadmin LoadChest (ChestLoot|BrewingStandLoot) (Common|Uncommon|Rare)
          Loads the referenced loot table into the chest you're looking at.  WARNING: Current chest contents will be deleted!
      /sgadmin (SaveChest|SaveBrewStand) (Common|Uncommon|Rare)
          Saves the contents of the chest you're looking at to the referenced Loot Table.
      /sgadmin LoadStartInv --loads the default starting inventory into your inventory.
      /sgadmin SaveStartInv --saves the default starting inventory to configs (from your inventory)
	  /sgadmin setspawn --Sets the Skygrid world spawn to where you're standing.
	  ****ServerConsole Capable Commands:****
	  /sgadmin randomLoc <Name> --send named player to a random block in the SkyGrid world.
	  /sgadmin reload --Reloads configs from file.
	  /sgadmin resetconfigs --Resets all config files to defaults
	  /sgadmin spawn <Name> --send named player to the SkyGrid world spawn.
```
## SkyGrid's Permission Nodes
```
lezkygrid.*:
        description: Gives permissions to all commands and features in Lezkygrid
        default: op
        children:
            lezkygrid.all: true
    lezkygrid.all:
        description: Gives permissions to all commands and features in Lezkygrid
        default: op
        children:
            lezkygrid.skygrid.*: true
            lezkygrid.sgadmin.*: true
    lezkygrid.skygrid.*:
        description: Gives permissions to the /skygrid command, and all sub-commands.
        default: true
        children:
            lezkygrid.skygrid.all: true
    lezkygrid.skygrid.all:
        description: Gives permissions to the /skygrid command, and all sub-commands.
        default: true
        children: 
            lezkygrid.skygrid.home: true
            lezkygrid.skygrid.randomloc: true
            lezkygrid.skygrid.spawn: true
            lezkygrid.skygrid.sethome: true
            lezkygrid.skygrid.sethome.unlimited: false
            lezkygrid.skygrid.delhome: true
    lezkygrid.skygrid.home:
        description: Allows use of /skygrid home to teleport to saved home coordinates.
        default: true
    lezkygrid.skygrid.randomloc:
        description: Allows user to teleport to a random location in the Skygrid world.
        default: true
    lezkygrid.skygrid.spawn:
        description: Allows user to teleport to the SkyGrid world spawnpoint.
        default: true
    lezkygrid.skygrid.sethome:
        description: Allows user to use the /skygrid sethome command to save home locations.
        default: true    
    lezkygrid.skygrid.sethome.unlimited:
        description: Allows user to set unlimited homes with /skygrid sethome.
        default: false
    lezkygrid.skygrid.delhome:
        description: Allows user to remove saved homes via /skygrid delhome command.
        default: true
    lezkygrid.sgadmin.*:
        description: Allows access to the /sgadmin command and sub commands for admin use.
        default: op
        children:
            lezkygrid.sgadmin.all: true
    lezkygrid.sgadmin.all:
        description: Allows you to see how many times others have burned to deathAllows access to the /sgadmin command and sub commands for admin use.
        default: op
        children:
            lezkygrid.sgadmin.loadchest: true
            lezkygrid.sgadmin.savestartinv: true
            lezkygrid.sgadmin.savechest: true
            lezkygrid.sgadmin.savebrewstand: true
            lezkygrid.sgadmin.savestartinv: true
            lezkygrid.sgadmin.setspawn: true
            lezkygrid.sgadmin.spawn: true
            lezkygrid.sgadmin.randomloc: true
            lezkygrid.sgadmin.reload: true
            lezkygrid.sgadmin.resetconfigs: true
    lezkygrid.sgadmin.loadstartinv:
        description: Allows the /sga loadstartinv subcommand, loading the default starting inventory into the current player's inventory.
        default: op
    lezkygrid.sgadmin.savestartinv:
        description: Allows th /sga savestartinv subcommand, saving the current player's inventory into the configs for default starting inventory.
        default: op
    lezkygrid.sgadmin.loadchest:
        description: Allows the /sga loadchest subcommand, loading one of the loot tables into a chest.
        default: op
    lezkygrid.sgadmin.savechest:
        description: Allows the /sga savechest subcommand, saving the contents of a chest to the loot tables.
        default: op
    lezkygrid.sgadmin.savebrewstand:
        description: Allows the /sga savebrewstand subcommand, saving the chest contents to the loot tables.
        default: op
    lezkygrid.sgadmin.setspawn:
        description: Allows the setting of the SkyGrid World Spawnpoint.
        default: op
    lezkygrid.sgadmin.spawn:
        description: Allows the teleportation of another user to spawn.
        default: op
    lezkygrid.sgadmin.randomloc:
        description: Allows the teleportation of another user to a random location.
        default: op
    lezkygrid.sgadmin.reload:
        description: Reload SkyGrid configs from file.
        default: op
    lezkygrid.sgadmin.resetconfigs:
        description: Replaces the config files with defaults from the jar.
        default: op
```
## Ideas, To-Dos, Goals and Pipedreams:
- [X] Permission Nodes
- [X] Auto-saving playerfiles on interval (5 min?)
- [ ] Economy Support?
- [ ] Random Teleport Delay
- [ ] Region Claiming and Protection
- [ ] Coop/team play
- [ ] Challenges
- [X] Configurable initial inventory
- [ ] Scoring/Top Scoreboard
- [ ] Nether/End worlds and integrtation
- [ ] Auto worldgeneration (on First Enable?)
- [ ] Default Spawn island? - Schematic?
- [ ] Localizations --Will need translators once implemented, I only know English.
- [ ] More configuration options for worldgen/loot tables
- [ ] API other plugins can access
- Want to add something to this list?  Post a feature request on the GitHub.

#### Shout Outs and Recognitions:
- [ASkyBlock](http://dev.bukkit.org/bukkit-plugins/skyblock/) by @[tastybento](https://github.com/tastybento) (Feature inspiration, ideas on the program-flow to make features work...I've done my best to not steal code, and used tastybento's code as inspiration and then wrote in my own style.)  Somehow I missed tastybento's SkyGrid fork: [ASkyGrid](https://github.com/tastybento/askygrid), so also adding it to this list of inspirational plugins.
- [Ultimate SkyGrid](http://dev.bukkit.org/bukkit-plugins/ultimate-skygrid/) by MadArkael.  (Most recent SkyGrid plugin I could find, and I tried to stay faithful to making a similar user end product when coding my plugin.)
- The Spigot and Bukkit forums, and the posts on them without which I'd not have figured most of this out.

This project uses and distributes the following libaries, unmodified:  
**Apache Commons IO**  
Copyright 2002-2012 The Apache Software Foundation  
  
This product includes software developed by  
The Apache Software Foundation (http://www.apache.org/).  
