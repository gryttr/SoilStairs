package net.heimrarnadalr.soilstairs.block;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GrassSlabBlock extends SlabBlock {
	public final Block baseBlock;

	public GrassSlabBlock(Settings settings, Block baseBlock) {
		super(settings);
		this.baseBlock = baseBlock;
	}

	@Override
	public void scheduledTick(BlockState bs, ServerWorld world, BlockPos pos, Random rand) {
		if (!world.isClient) {
			if (!GrassStairsBlock.canSurvive(bs, world, pos)) {
				world.setBlockState(pos, SoilStairsBlocks.DIRT_SLAB.getDefaultState()
						.with(TYPE, bs.get(TYPE))
						.with(WATERLOGGED, bs.get(WATERLOGGED))
					);
			} else {
				if (world.getLightLevel(pos.up()) >= 9) {
					for(int int_1 = 0; int_1 < 4; ++int_1) {
						BlockPos blockPos_2 = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
						BlockState bsSpread = world.getBlockState(blockPos_2);
						if (GrassStairsBlock.canSpread(bsSpread, world, blockPos_2)) {
							GrassStairsBlock.spread(bsSpread, world, blockPos_2, this.baseBlock, true);
						}
					}
				}
			}
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
		this.baseBlock.randomDisplayTick(blockState_1, world_1, blockPos_1, random_1);
	}
}
