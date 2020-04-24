package net.heimrarnadalr.soilstairs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.heimrarnadalr.soilstairs.block.SoilStairsBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

public class SoilStairsMod implements ModInitializer, ClientModInitializer {
	public static final String MODID = "soilstairs";
	
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		SoilStairsBlocks.register();
	}

	@Override
	public void onInitializeClient() {
		ColorProviderRegistry.BLOCK.register(new BlockColorProvider() {
			@Override
			public int getColor(BlockState state, BlockRenderView world, BlockPos pos, int layer) {
				return world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.5D, 1.0D);
			}
		}, SoilStairsBlocks.GRASS_STAIRS, SoilStairsBlocks.GRASS_SLAB);

		ColorProviderRegistry.ITEM.register(new ItemColorProvider() {
			@Override
			public int getColor(ItemStack stack, int layer) {
				return GrassColors.getColor(0.5D, 1.0D);
			}
		}, SoilStairsBlocks.GRASS_STAIRS, SoilStairsBlocks.GRASS_SLAB);
		
		BlockRenderLayerMap.INSTANCE.putBlock(SoilStairsBlocks.GRASS_STAIRS, RenderLayer.getCutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(SoilStairsBlocks.GRASS_SLAB, RenderLayer.getCutoutMipped());
	}
}
