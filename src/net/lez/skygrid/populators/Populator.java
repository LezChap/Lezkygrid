package net.lez.skygrid.populators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Sapling;
import org.bukkit.material.Vine;
import org.bukkit.material.Wool;

import net.lez.skygrid.Main;

public class Populator extends BlockPopulator {
    private final Logger logger = Bukkit.getLogger();
    private final Main plugin;
    
    public Populator(Main plugin) {
        this.plugin = plugin;
        plugin.getConfig();
    }
    
    @Override
    public void populate(World world, Random rand, Chunk chunk) {
        for (int x = 0; x < 15; x += 4) {
            for (int y = 1; y <= plugin.getConfig().getInt("WorldGenHeight"); y += 4) {
                for (int z = 0; z < 15; z += 4){
                    Block block = chunk.getBlock(x, y, z);
                    Material mat = block.getType();
                    
                    switch (mat) {
                    case GRASS:
                        popGrass(block, rand);
                        break;
                    case DIRT:
                        popDirt(block, rand);
                        break;
                    case SAND:
                        popSand(block, rand);
                        break;
                    case MOSSY_COBBLESTONE:
                        popMossy(block, rand);
                        break;
                    case NETHERRACK:
                        popNetherrack(block, rand);
                        break;
                    case WOOL:
                        popWool(block, rand);
                        break;
                    case MYCEL:
                        popMycel(block, rand);
                        break;
                    case MOB_SPAWNER:
                        popSpawner(block, rand);
                        break;
                    case SOUL_SAND:
                        popSoulSand(block, rand);
                        break;
                    case STATIONARY_WATER:
                        popWater(block, rand);
                        break;
                    case CHEST:
                        popChest(block, rand);
                        break;
                    case BREWING_STAND:
                        popBrew(block, rand);
                        break;
                    default:  //do nothing
                        break;
                    }
                     
                }
            }
        }
        
    }

    @SuppressWarnings("deprecation")
    private void popGrass(Block block, Random rand) {
        if (block.getType() == Material.GRASS) {
            int chance = rand.nextInt(100) + 1;
            if (chance <= plugin.getConfig().getInt("DecoChances.Grass")) {
                Block newBlock = block.getRelative(BlockFace.UP);
                byte tmp = (byte) rand.nextInt(11);
                boolean done = false;
                if (tmp <= 5 && !done && (rand.nextInt(10) < 2)) {  //20% chance to make a double plant at this step
                    newBlock.setType(Material.DOUBLE_PLANT);
                    newBlock.setData(tmp);
                    Block secondBlock = newBlock.getRelative(BlockFace.UP);
                    secondBlock.setType(Material.DOUBLE_PLANT);
                    secondBlock.setData((byte) (tmp+8));
                    done = true;
                } else if (tmp <= 8 && !done) {
                    newBlock.setType(Material.RED_ROSE);
                    newBlock.setData(tmp);
                    done = true;
                } else if (tmp == 9 && !done) {
                    newBlock.setType(Material.YELLOW_FLOWER);
                    done = true;
                } else {
                    newBlock.setType(Material.LONG_GRASS);
                    if (rand.nextInt(10) < 2) {
                        newBlock.setData((byte) 2);
                    }
                }
            }  
        }
    }

    @SuppressWarnings("deprecation")
    private void popDirt(Block block, Random rand) {
        if (block.getType() == Material.DIRT) {
            int chance = rand.nextInt(100) + 1;
            if (chance <= plugin.getConfig().getInt("DecoChances.Dirt")) {
                Block newBlock = block.getRelative(BlockFace.UP);
                Sapling sapling = new Sapling();
                switch (rand.nextInt(7)) {
                case 0:
                    sapling.setSpecies(TreeSpecies.GENERIC);
                case 1:
                    sapling.setSpecies(TreeSpecies.BIRCH);
                case 2:
                    sapling.setSpecies(TreeSpecies.REDWOOD);
                case 3:
                    sapling.setSpecies(TreeSpecies.JUNGLE);
                case 4:
                    sapling.setSpecies(TreeSpecies.ACACIA);
                    newBlock.setType(Material.SAPLING);
                    newBlock.setData(sapling.getData());
                    break;
                case 5:
                case 6:
                    newBlock.setType(Material.LONG_GRASS);
                    newBlock.setData((byte) 0);
                    break;
                default: //do nothing
                    break;
                } 
            }  
        }
        
    }

    @SuppressWarnings("deprecation")
    private void popSand(Block block, Random rand) {
        if (block.getType() == Material.SAND) {
            int chance = rand.nextInt(100) + 1;
            if (chance <= plugin.getConfig().getInt("DecoChances.Sand")) {
                Block newBlock = block.getRelative(BlockFace.UP);
                Block underBlock = block.getRelative(BlockFace.DOWN);
                switch (rand.nextInt(2)) {
                case 0:  
                    underBlock.setType(Material.STEP, false);
                    underBlock.setData((byte) 9);
                    newBlock.setType(Material.CACTUS, false);
                    break;
                case 1:
                    newBlock.setType(Material.DEAD_BUSH);
                    break;
                default: //do nothing?
                    break;
                } 
            }  
        }
        
    }

    @SuppressWarnings("deprecation")
    private void popMossy(Block block, Random rand) {
        if (block.getType() == Material.MOSSY_COBBLESTONE) {
            int chance = rand.nextInt(100) + 1;
            if (chance <= plugin.getConfig().getInt("DecoChances.MossyCobblestone")) {
                Block newBlock = block.getRelative(BlockFace.UP);
                switch (rand.nextInt(3)) {
                case 0:  
                    newBlock.setType(Material.BROWN_MUSHROOM);
                    break;
                case 1:  
                    newBlock.setType(Material.RED_MUSHROOM);
                    break;
                case 2:
                    List<BlockFace> sides = new ArrayList<BlockFace>();
                    sides.add(BlockFace.EAST);
                    sides.add(BlockFace.WEST);
                    sides.add(BlockFace.NORTH);
                    sides.add(BlockFace.SOUTH);
                    BlockFace face = sides.get(rand.nextInt(sides.size()));
                    newBlock = block.getRelative(face);
                    newBlock.setType(Material.VINE);
                    MaterialData data = newBlock.getState().getData();
                    if(data instanceof Vine){
                        Vine vine = (Vine) data;
                        vine.putOnFace(face.getOppositeFace());
                        newBlock.setData(vine.getData());
                        newBlock.getState().update();
                    }
                    //logger.info("Vine set at: " + block.getLocation().toString());

                    break;
                default: //do nothing?
                    break;
                } 
            }  
        }
    }

    private void popNetherrack(Block block, Random rand) {
        if (block.getType() == Material.NETHERRACK) {
            int chance = rand.nextInt(100) + 1;
            if (chance <= plugin.getConfig().getInt("DecoChances.Netherrack")) {
                Block newBlock = block.getRelative(BlockFace.UP);
                newBlock.setType(Material.FIRE);
                //logger.info("Netherrack set on fire at: " + block.getLocation().toString());

            }
        }        
    }

    private void popWool(Block block, Random rand) {
        if (block.getType() == Material.WOOL) {
            int chance = rand.nextInt(100) + 1;
            if (chance <= plugin.getConfig().getInt("DecoChances.Wool")) {
                List<?> DyeColors = new ArrayList<DyeColor>();
                DyeColors = Arrays.asList(DyeColor.values());
                DyeColors.remove(Arrays.asList(DyeColor.WHITE));
                BlockState state = block.getState();
                Wool wool = (Wool)state.getData();
                wool.setColor((DyeColor) DyeColors.get(rand.nextInt(DyeColors.size())));
                state.setData(wool);
                state.update();
                //logger.info("Wool set to " + wool.getColor().toString() + " at: " + block.getLocation().toString());
            }
        }
        
    }

    private void popMycel(Block block, Random rand) {
        if (block.getType() == Material.MYCEL) {
            int chance = rand.nextInt(100) + 1;
            if (chance <= plugin.getConfig().getInt("DecoChances.Mycellium")) {
                Block newBlock = block.getRelative(BlockFace.UP);
                if (rand.nextBoolean()) {
                    newBlock.setType(Material.BROWN_MUSHROOM);
                } else {
                    newBlock.setType(Material.RED_MUSHROOM);
                }
            }
        }
    }

    private void popSpawner(Block block, Random rand) {
        // TODO Auto-generated method stub
        if (block.getType() == Material.MOB_SPAWNER) {
            int chance = rand.nextInt(100) + 1;
            boolean isSet = false;
            if (chance <= plugin.getConfig().getInt("SpawnerChances.Hostile.Epic") && !isSet) {
                setSpawner(block, "SpawnerMobs.Hostile.Epic", rand);
                isSet = true;
            }
            chance = rand.nextInt(100) + 1;
            if (chance <= plugin.getConfig().getInt("SpawnerChances.Passive.Epic") && !isSet) {
                setSpawner(block, "SpawnerMobs.Passive.Epic", rand);
                isSet = true;
            }
            chance = rand.nextInt(100) + 1;
            if (chance <= plugin.getConfig().getInt("SpawnerChances.Hostile.Rare") && !isSet) {
                setSpawner(block, "SpawnerMobs.Hostile.Rare", rand);
                isSet = true;
            }
            chance = rand.nextInt(100) + 1;
            if (chance <= plugin.getConfig().getInt("SpawnerChances.Passive.Rare") && !isSet) {
                setSpawner(block, "SpawnerMobs.Passive.Rare", rand);
                isSet = true;
            }
            chance = rand.nextInt(100) + 1;
            if (chance <= plugin.getConfig().getInt("SpawnerChances.Hostile.Common") && !isSet) {
                setSpawner(block, "SpawnerMobs.Hostile.Common", rand);
                isSet = true;
            }
            if (!isSet) {
                setSpawner(block, "SpawnerMobs.Passive.Common", rand);
                isSet = true;
            }
            if (!isSet) {
                logger.info("Something went wrong and was unable to set a MobSpawner at:" + block.getLocation().toString());
            }
        }
    }

    private void popSoulSand(Block block, Random rand) {
        if (block.getType() == Material.SOUL_SAND) {
            int chance = rand.nextInt(100) + 1;
            if (chance <= plugin.getConfig().getInt("DecoChances.SoulSand")) {
                Block newBlock = block.getRelative(BlockFace.UP);
                newBlock.setType(Material.NETHER_WARTS);
            }
        }
        
    }

    private void popWater(Block block, Random rand) {
        if (block.getType() == Material.STATIONARY_WATER) {
            int chance = rand.nextInt(100) + 1;
            if (chance <= plugin.getConfig().getInt("DecoChances.Water")) {
                Block newBlock = block.getRelative(BlockFace.UP);
                newBlock.setType(Material.WATER_LILY);
                //logger.info("Lilypad set at: " + block.getLocation().toString());
            }
        }        
    }

    private void popChest(Block block, Random rand) {
        if (block.getType() == Material.CHEST) {
            Chest chest = (Chest) block.getState();
            Inventory inv = chest.getInventory();
            int high = rand.nextInt(100) + 1;
            int maxI;
            if (high <= plugin.getConfig().getInt("ChestChances.HighItemChance")) {          
                maxI = getWeightedInt(plugin.getConfig().getInt("ChestChances.NumItemsHigh"), rand);
            } else {
                maxI = getWeightedInt(plugin.getConfig().getInt("ChestChances.NumItemsLow"), rand);
            }
            for (int i = 0; i < maxI; i++) {
                int chance = rand.nextInt(100) + 1;
                if (chance <= plugin.getConfig().getInt("ChestChances.Uncommon")) {
                    if (chance <= plugin.getConfig().getInt("ChestChances.Rare")) {
                        AddChestLoot(inv, "ChestLoot.Rare", rand);
                    } else {
                        AddChestLoot(inv, "ChestLoot.Uncommon", rand);
                    } 
                } else {
                AddChestLoot(inv, "ChestLoot.Common", rand);
                }
            }
            chest.update();
            //logger.info(block.getType().toString() + " at: " + block.getLocation().toString());
            //logger.info("Should have total items: " + maxI);
        }
        
    }

    private void popBrew(Block block, Random rand) {
        if (block.getType() == Material.BREWING_STAND) {
            BrewingStand stand = (BrewingStand) block.getState();
            int num = 1;
            int secondLoot = rand.nextInt(100) + 1;
            int thirdLoot = rand.nextInt(100) + 1;
            if (secondLoot <= plugin.getConfig().getInt("BrewingStandChance.SecondItemChance")) {
                num++;
                if (thirdLoot <= plugin.getConfig().getInt("BrewingStandChance.ThirdItemChance")) {
                    num++;
                }
            }
            for (int i = 0; i < num; i++){
                int chance = rand.nextInt(100) + 1;
                if (chance <= plugin.getConfig().getInt("BrewingStandChances.Uncommon")) {
                    if (chance <= plugin.getConfig().getInt("BrewingStandChances.Rare")) {
                        AddBrewLoot(stand, "BrewingStandLoot.Rare", rand);
                    } else {
                        AddBrewLoot(stand, "BrewingStandLoot.Uncommon", rand);
                    } 
                } else {
                    AddBrewLoot(stand, "BrewingStandLoot.Common", rand);

                }

            }
            //logger.info(block.getType().toString() + " at: " + block.getLocation().toString());
            stand.update();
        }
    }
    
    private EntityType selectEntity(String path, Random rand) {
        HashMap<String, EntityType> entityMap = new HashMap<String, EntityType>();
        entityMap.put("BAT", EntityType.BAT);
        entityMap.put("BLAZE", EntityType.BLAZE);
        entityMap.put("CAVE_SPIDER", EntityType.CAVE_SPIDER);
        entityMap.put("CHICKEN", EntityType.CHICKEN);
        entityMap.put("COW", EntityType.COW);
        entityMap.put("CREEPER", EntityType.CREEPER);
        entityMap.put("ENDERMAN", EntityType.ENDERMAN);
        entityMap.put("ENDERMITE", EntityType.ENDERMITE);
        entityMap.put("GHAST", EntityType.GHAST);
        entityMap.put("GIANT", EntityType.GIANT);
        entityMap.put("GUARDIAN", EntityType.GUARDIAN);
        entityMap.put("HORSE", EntityType.HORSE);
        entityMap.put("IRON_GOLEM", EntityType.IRON_GOLEM);
        entityMap.put("MAGMA_CUBE", EntityType.MAGMA_CUBE);
        entityMap.put("MUSHROOM_COW", EntityType.MUSHROOM_COW);
        entityMap.put("OCELOT", EntityType.OCELOT);
        entityMap.put("PIG", EntityType.PIG);
        entityMap.put("PIG_ZOMBIE", EntityType.PIG_ZOMBIE);
        entityMap.put("RABBIT", EntityType.RABBIT);
        entityMap.put("SHEEP", EntityType.SHEEP);
        entityMap.put("SILVERFISH", EntityType.SILVERFISH);
        entityMap.put("SKELETON", EntityType.SKELETON);
        entityMap.put("SLIME", EntityType.SLIME);
        entityMap.put("SNOWMAN", EntityType.SNOWMAN);
        entityMap.put("SPIDER", EntityType.SPIDER);
        entityMap.put("VILLAGER", EntityType.VILLAGER);
        entityMap.put("WITCH", EntityType.WITCH);
        entityMap.put("WOLF", EntityType.WOLF);
        entityMap.put("ZOMBIE", EntityType.ZOMBIE);
        entityMap.put(null, EntityType.PIG);
        
        List<String> entityList = plugin.getConfig().getStringList(path);
        int cnt = 0;
        EntityType entity = null;
        do {
            cnt ++;
            int ind = rand.nextInt(entityList.size());
            String entityName = (String) entityList.get(ind);
            entity = entityMap.get(entityName.toUpperCase());
            if (entity == null) {
                logger.info("Attempted to convert bad EntityType from Configs, fix config entry: " + entityName.toUpperCase());
            }
        } while ((entity == null) && cnt <= 5);
        if (entity == null) {
            entity = EntityType.PIG;
        }
        return entity;
    }
    
    private void setSpawner(Block block, String path, Random rand) {
        CreatureSpawner spawner = (CreatureSpawner) block.getState();
        EntityType entity = selectEntity(path, rand);
        spawner.setSpawnedType(entity);
        spawner.update();
        //logger.info("Set MobSpawner to " + entity.toString() + " at " + block.getLocation().toString());
    }
    
    @SuppressWarnings("unchecked")
    private List<ItemStack> getLootTable(String path) {
        List<ItemStack> list = new ArrayList<ItemStack>();
        list = (List<ItemStack>) plugin.lootConfig.get(path);
        //ItemStack[] contents;
        //contents = new ItemStack[list.size()];
        //contents = list.toArray(contents);
        return list;
    }
    
    private void AddChestLoot(Inventory inv, String path, Random rand) {
        int index = rand.nextInt(getLootTable(path).size());
        ItemStack loot = (ItemStack) getLootTable(path).get(index);
        int qty = loot.getAmount();
        if (qty > 1) {
            qty = getWeightedInt(qty, rand);
            loot.setAmount(qty);
        }
        if (loot.getType() == Material.MONSTER_EGG) {
            int ind = rand.nextInt(getLootTable("ChestLoot.SpawnEgg").size());
            loot = (ItemStack) getLootTable("ChestLoot.SpawnEgg").get(ind);
            loot.setAmount(qty);
        }
        inv.addItem(loot);
    }
    
    private boolean isPotion(ItemStack stack) {
        boolean bool = false;
        switch(stack.getType()) {
        case POTION:
        case SPLASH_POTION:
        case LINGERING_POTION:
            bool = true;
            break;
        default:
            bool = false;
            break;
        }
        return bool;
    }
    
    private boolean isBrewIngredient(ItemStack stack) {
        boolean bool = false;
        switch (stack.getType()) {
            case NETHER_STALK:
            case FERMENTED_SPIDER_EYE:
            case GLOWSTONE_DUST:
            case REDSTONE:
            case SUGAR:
            case SPIDER_EYE:
            case GHAST_TEAR:
            case RAW_FISH:
            case RABBIT_FOOT:
            case SULPHUR:
            case DRAGONS_BREATH:
            case SPECKLED_MELON:
            case GOLDEN_CARROT:
            case MAGMA_CREAM:
                bool = true;
                break;
            default:
                bool = false;
                break;
        }
        return bool;
    }
    
    private void AddBrewLoot(BrewingStand stand, String path, Random rand) {
        int index = rand.nextInt(getLootTable(path).size());
        ItemStack loot = (ItemStack) getLootTable(path).get(index);
        int qty = loot.getAmount();
        if (qty > 1) { 
            qty = getWeightedInt(qty, rand);
            //qty = rand.nextInt(loot.getAmount()) + 1;
            loot.setAmount(qty);
        }
        BrewerInventory inv = stand.getInventory();
        if (loot.getType() == Material.BLAZE_POWDER) {
            if (inv.getFuel() == null || inv.getFuel().getType() == Material.AIR) {
                inv.setFuel(loot);
            } else if (inv.getFuel().isSimilar(loot)) {
                loot.setAmount(inv.getFuel().getAmount() + loot.getAmount());
                inv.setFuel(loot);
            } else {
                logger.info("Fuel Type doesn't match in this Brewing Stand?  That's weird...");
            } 
        } else if (isBrewIngredient(loot)) {
            if (inv.getIngredient() == null || inv.getIngredient().getType() == Material.AIR) {
                inv.setIngredient(loot);
            } else if (inv.getIngredient().isSimilar(loot)) {
                loot.setAmount(loot.getAmount() + inv.getIngredient().getAmount());
                inv.setIngredient(loot);
            } else {
                logger.info("Failed to place item in Brewing Stand, slot already occupied.");
            }
        } else if (isPotion(loot)) {
            inv.addItem(loot);
        } else {
            logger.info("Invalid Item for Brewing Stand found in Loot Tables (" + loot.getType() + "), skipping...");
        }
    }
    
    private int getWeightedInt(int qty, Random rand) {
        int val;
        int cnt = 0;
        do {
            cnt ++;
            double Gaus = rand.nextGaussian();
            double Mean = plugin.getConfig().getDouble("Normalized.Mean");
            double Dev = plugin.getConfig().getDouble("Normalized.Dev");
            double result = Gaus * Dev + Mean;
            val = (int) Math.round(result);
        } while (val <= 0 || cnt < 5);
        if (cnt > 2) {
            val = 1;
        }
        //logger.info(qty + " went in, " + val + " came out.");
        return val;
    }
}
