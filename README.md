# Lezkygrid

A remake of the SkyGrid-style plugins, updated for Spigot 1.9.2.  To make a world, currently you need to use Multiverse (or other multiworld/generating plugin) and select "lezkygrid" as the world generator ("/mvc SkyGrid normal -g lezkygrid" on Multiverse 2.5).  Almost all generated blocks and loot tables can be configured.  If something doesn't work, post an issue so I can look into it.

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
      /sgadmin setspawn --Sets the Skygrid world spawn to where you're standing.
      ****ServerConsole Capable Commands:****
      /sgadmin randomLoc <Name> --send named player to a random block in the SkyGrid world.
      /sgadmin reload --Reloads configs from file.
      /sgadmin resetconfigs --Resets all config files to defaults
      /sgadmin spawn <Name> --send named player to the SkyGrid world spawn.
```      
## Ideas, To-Dos, Goals and Pipedreams:
- [ ] Permission Nodes
- [ ] Economy Support?
- [ ] Random Teleport Delay
- [ ] Region Claiming and Protection
- [ ] Coop/team play
- [ ] Challenges
- [ ] Configurable initial inventory
- [ ] Scoring/Top Scoreboard
- [ ] Nether/End worlds and integrtation
- [ ] Auto worldgeneration (on First Enable?)
- [ ] Default Spawn island?
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
