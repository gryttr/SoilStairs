package net.heimrarnadalr.soilstairs.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.heimrarnadalr.soilstairs.SoilStairsMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoilStairsBlocks {

	public static final Block DIRT_STAIRS = new SoilStairsBlock(Blocks.DIRT.getDefaultState(), FabricBlockSettings.copy(Blocks.DIRT).breakByTool(FabricToolTags.SHOVELS).build());
	public static final Block COARSE_DIRT_STAIRS = new SoilStairsBlock(Blocks.COARSE_DIRT.getDefaultState(), FabricBlockSettings.copy(Blocks.COARSE_DIRT).breakByTool(FabricToolTags.SHOVELS).build());
	public static final Block GRASS_STAIRS = new GrassStairsBlock(Blocks.GRASS_BLOCK.getDefaultState(), FabricBlockSettings.copy(Blocks.GRASS_BLOCK).breakByTool(FabricToolTags.SHOVELS).build());
	public static final Block GRASS_PATH_STAIRS = new GrassPathStairsBlock(Blocks.GRASS_PATH.getDefaultState(), FabricBlockSettings.copy(Blocks.GRASS_PATH).breakByTool(FabricToolTags.SHOVELS).build());
	public static final Block MYCELIUM_STAIRS = new GrassStairsBlock(Blocks.MYCELIUM.getDefaultState(), FabricBlockSettings.copy(Blocks.MYCELIUM).breakByTool(FabricToolTags.SHOVELS).build());
	public static final Block PODZOL_STAIRS = new SoilStairsBlock(Blocks.PODZOL.getDefaultState(), FabricBlockSettings.copy(Blocks.PODZOL).breakByTool(FabricToolTags.SHOVELS).build());
	
	public static final Block DIRT_SLAB = new SlabBlock(FabricBlockSettings.copy(Blocks.DIRT).breakByTool(FabricToolTags.SHOVELS).build());
	public static final Block COARSE_DIRT_SLAB = new SlabBlock(FabricBlockSettings.copy(Blocks.COARSE_DIRT).breakByTool(FabricToolTags.SHOVELS).build());
	public static final Block GRASS_SLAB = new GrassSlabBlock(FabricBlockSettings.copy(Blocks.GRASS_BLOCK).breakByTool(FabricToolTags.SHOVELS).build(), Blocks.GRASS_BLOCK);
	public static final Block GRASS_PATH_SLAB = new GrassPathSlabBlock(FabricBlockSettings.copy(Blocks.GRASS_PATH).breakByTool(FabricToolTags.SHOVELS).build());
	public static final Block MYCELIUM_SLAB = new GrassSlabBlock(FabricBlockSettings.copy(Blocks.MYCELIUM).breakByTool(FabricToolTags.SHOVELS).build(), Blocks.MYCELIUM);
	public static final Block PODZOL_SLAB = new SlabBlock(FabricBlockSettings.copy(Blocks.PODZOL).breakByTool(FabricToolTags.SHOVELS).build());
	
	private static void registerBuildingBlock(Block block, String name) {
		Registry.register(Registry.BLOCK, new Identifier(SoilStairsMod.MODID, name), block);
		Registry.register(Registry.ITEM, new Identifier(SoilStairsMod.MODID, name), new BlockItem(block, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
	}
	
	public static void register() {
		registerBuildingBlock(DIRT_STAIRS, "dirt_stairs");
		registerBuildingBlock(COARSE_DIRT_STAIRS, "coarse_dirt_stairs");
		registerBuildingBlock(GRASS_STAIRS, "grass_stairs");
		registerBuildingBlock(GRASS_PATH_STAIRS, "grass_path_stairs");
		registerBuildingBlock(MYCELIUM_STAIRS, "mycelium_stairs");
		registerBuildingBlock(PODZOL_STAIRS, "podzol_stairs");
		
		registerBuildingBlock(DIRT_SLAB, "dirt_slab");
		registerBuildingBlock(COARSE_DIRT_SLAB, "coarse_dirt_slab");
		registerBuildingBlock(GRASS_SLAB, "grass_slab");
		registerBuildingBlock(GRASS_PATH_SLAB, "grass_path_slab");
		registerBuildingBlock(MYCELIUM_SLAB, "mycelium_slab");
		registerBuildingBlock(PODZOL_SLAB, "podzol_slab");
	}
}
