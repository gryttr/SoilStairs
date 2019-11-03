package net.heimrarnadalr.soilstairs.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.SnowyBlock;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public class GrassStairsBlock extends SoilStairsBlock {

	protected GrassStairsBlock(BlockState blockState, Settings settings) {
		super(blockState, settings);
	}

	public static boolean canSurvive(BlockState bs, ViewableWorld viewableWorld, BlockPos pos) {
		BlockPos posUp = pos.up();
		BlockState bsUp = viewableWorld.getBlockState(posUp);
		if (bsUp.getBlock() == Blocks.SNOW && (Integer)bsUp.get(SnowBlock.LAYERS) == 1) {
			return true;
		} else {
			int light = ChunkLightProvider.method_20049(viewableWorld, bs, pos, bsUp, posUp, Direction.UP, bsUp.getLightSubtracted(viewableWorld, posUp));
			if (light < viewableWorld.getMaxLightLevel()) {
				return true;
			} else {
				return viewableWorld.getLightLevel(pos.up()) >= 4;
			}
		}
	}

	public static boolean canSpread(BlockState bs, ViewableWorld viewableWorld, BlockPos pos) {
		BlockPos posUp = pos.up();
		return canSurvive(bs, viewableWorld, pos) && !viewableWorld.getFluidState(posUp).matches(FluidTags.WATER);
	}
	
	public static void spread(BlockState bs, World world, BlockPos pos, Block spreadingBlock, boolean includeNormalDirt) {
		if (bs.getBlock() == Blocks.DIRT && includeNormalDirt) {
			world.setBlockState(pos,  spreadingBlock.getDefaultState().with(SnowyBlock.SNOWY, world.getBlockState(pos.up()).getBlock() == Blocks.SNOW));
		} else if (bs.getBlock() == SoilStairsBlocks.DIRT_STAIRS ) {
			if (spreadingBlock == Blocks.GRASS_BLOCK) {
				world.setBlockState(pos, SoilStairsBlocks.GRASS_STAIRS.getDefaultState()
						.with(FACING, bs.get(FACING))
						.with(HALF, bs.get(HALF))
						.with(SHAPE, bs.get(SHAPE))
						.with(WATERLOGGED, bs.get(WATERLOGGED))
					);
			} else if (spreadingBlock == Blocks.MYCELIUM) {
				world.setBlockState(pos, SoilStairsBlocks.MYCELIUM_STAIRS.getDefaultState()
						.with(FACING, bs.get(FACING))
						.with(HALF, bs.get(HALF))
						.with(SHAPE, bs.get(SHAPE))
						.with(WATERLOGGED, bs.get(WATERLOGGED))
					);
			}
		} else if (bs.getBlock() == SoilStairsBlocks.DIRT_SLAB) {
			if (spreadingBlock == Blocks.GRASS_BLOCK) {
				world.setBlockState(pos, SoilStairsBlocks.GRASS_SLAB.getDefaultState()
						.with(SlabBlock.TYPE, bs.get(SlabBlock.TYPE))
						.with(SlabBlock.WATERLOGGED, bs.get(SlabBlock.WATERLOGGED))
					);
			} if (spreadingBlock == Blocks.MYCELIUM) {
				world.setBlockState(pos, SoilStairsBlocks.MYCELIUM_SLAB.getDefaultState()
						.with(SlabBlock.TYPE, bs.get(SlabBlock.TYPE))
						.with(SlabBlock.WATERLOGGED, bs.get(SlabBlock.WATERLOGGED))
					);
			}
		}
	}
	
	@Override
	public void onScheduledTick(BlockState bs, World world, BlockPos pos, Random rand) {
		if (!world.isClient) {
			if (!canSurvive(bs, world, pos)) {
				world.setBlockState(pos, SoilStairsBlocks.DIRT_STAIRS.getDefaultState()
						.with(FACING, bs.get(FACING))
						.with(HALF, bs.get(HALF))
						.with(SHAPE, bs.get(SHAPE))
						.with(WATERLOGGED, bs.get(WATERLOGGED))
					);
			} else {
				if (world.getLightLevel(pos.up()) >= 9) {
					for(int int_1 = 0; int_1 < 4; ++int_1) {
						BlockPos blockPos_2 = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
						BlockState bsSpread = world.getBlockState(blockPos_2);
						if (canSpread(bsSpread, world, blockPos_2)) {
							spread(bsSpread, world, blockPos_2, this.baseBlock, true);
						}
					}
				}
			}
		}
	}
}
