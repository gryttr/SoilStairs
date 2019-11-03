package net.heimrarnadalr.soilstairs.mixin;

import net.heimrarnadalr.soilstairs.block.GrassStairsBlock;
import net.heimrarnadalr.soilstairs.block.SoilStairsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpreadableBlock.class)
public class SpreadableBlockMixin {
	@Inject(at = @At("TAIL"), method = "onScheduledTick")
	private void onScheduledTick(BlockState bs, World world, BlockPos pos, Random rand, CallbackInfo info) {
		if (!world.isClient) {
			Block spreadingBlock = bs.getBlock();
			if (spreadingBlock == Blocks.GRASS_BLOCK || spreadingBlock == Blocks.MYCELIUM) {
				if (world.getLightLevel(pos.up()) >= 9) {
					for(int int_1 = 0; int_1 < 4; ++int_1) {
						BlockPos blockPos_2 = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
						BlockState bsSpread = world.getBlockState(blockPos_2);
						if ((bsSpread.getBlock() == SoilStairsBlocks.DIRT_STAIRS || bsSpread.getBlock() == SoilStairsBlocks.DIRT_SLAB) && GrassStairsBlock.canSpread(bsSpread, world, blockPos_2)) {
							GrassStairsBlock.spread(bsSpread, world, blockPos_2, spreadingBlock, false);
						}
					}
				}
			}
		}
	}
}
