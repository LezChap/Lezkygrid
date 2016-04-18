package net.lez.skygrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.MaterialData;

import net.lez.skygrid.populators.Populator;

public class SkyGridChunkGenerator extends ChunkGenerator {
    @SuppressWarnings("unused")
    private final Logger logger = Bukkit.getLogger();  
    private final Main plugin;  

    public SkyGridChunkGenerator(String id, Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public ChunkData generateChunkData(World world, Random rand, int x, int z, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);
        for (int x1 = 0; x1 < 15; x1 += 4) {
            for (int y = 1; y <= plugin.getConfig().getInt("WorldGenHeight"); y += 4) {
                for (int z1 = 0; z1 < 15; z1 += 4) {
                    MaterialData blockMatDat = getRandMaterial(rand);
                    chunk.setBlock(x1, y, z1, blockMatDat);
                }
            }
        }
        //logger.info("Chunk Created?");
        return chunk;
    }
    
    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        ArrayList<BlockPopulator> pops = new ArrayList<BlockPopulator>();
        //Add Block populators here
        if (world.getPopulators().isEmpty())
            pops.add(new Populator(plugin));

        return pops;
        
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
            return true;
    }
    
    @Override
    public Location getFixedSpawnLocation(World world, Random random){
        return new Location(world, 0, plugin.getConfig().getInt("SpawnHeights.Max") + 1, 0);
    }
    
    public MaterialData getRandMaterial(Random rand) {
        MaterialData material = null;
        int r = rand.nextInt(10000);
        if (r < plugin.getConfig().getInt("Chances.Uncommon")) {
            if (r < plugin.getConfig().getInt("Chances.Rare")) {
                if (r < plugin.getConfig().getInt("Chances.Epic")) {
                    if (r < plugin.getConfig().getInt("Chances.Legendary")) {
                        material = getRandomMaterialPath("BlockLists.Legendary", rand);
                        //logger.info("Spawn Legendary Block");
                    } else {
                        material = getRandomMaterialPath("BlockLists.Epic", rand);
                        //logger.info("Spawn Epic Block" );
                    }
                } else {
                    material = getRandomMaterialPath("BlockLists.Rare", rand);
                    //logger.info("spawn Rare block");
                }
            } else {
                material = getRandomMaterialPath("BlockLists.Uncommon", rand);
                //logger.info("Spawn Uncommon Block");
            }
        } else {
            material = getRandomMaterialPath("BlockLists.Common", rand);
        }
        return material;
    }
    
    @SuppressWarnings("deprecation")
    private MaterialData getRandomMaterialPath(String path, Random rand) {
        MaterialData material = null;
        List<String> blocklist = plugin.getConfig().getStringList(path);
        Material testMat = null;
        do {
            String randBlock = blocklist.get(rand.nextInt(blocklist.size()));
            if (randBlock.contains(":")) {
                String[] parts = randBlock.split(":");
                String mat = parts[0];
                Byte data = Byte.valueOf(parts[1]);
                Material mat2 = Material.matchMaterial(mat);
                if (mat2 != null) {
                    material = new MaterialData(mat2, data);
                }
            } else {
                Material mat2 = Material.matchMaterial(randBlock);
                if (mat2 != null) {
                    if (mat2 == Material.CHEST) {
                        byte r2 = (byte) (rand.nextInt(4) + 2);
                        material = new MaterialData(mat2, (byte) r2);
                    } else {
                        material = new MaterialData(mat2);
                    }
                }
            }
            testMat = material.getItemType();
        } while (material == null || !testMat.isBlock());
        return material;
    }
}
