package net.heimrarnadalr.soilstairs.mixin;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.heimrarnadalr.soilstairs.block.SoilStairsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

@Mixin(SugarCaneBlock.class)
public class SugarCanPlaceAtMixin {
	@Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
	public void canPlaceAt(BlockState bs, WorldView viewableWorld, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		BlockState bsDown = viewableWorld.getBlockState(pos.down());
		Block blockDown = bsDown.getBlock();
		boolean valid = false;
		if (blockDown == SoilStairsBlocks.GRASS_STAIRS || blockDown == SoilStairsBlocks.DIRT_STAIRS || blockDown == SoilStairsBlocks.COARSE_DIRT_STAIRS || blockDown == SoilStairsBlocks.PODZOL_STAIRS) {
			if (bsDown.get(StairsBlock.HALF) == BlockHalf.TOP) {
				valid = true;
			}
		} else if (blockDown == SoilStairsBlocks.GRASS_SLAB || blockDown == SoilStairsBlocks.DIRT_SLAB || blockDown == SoilStairsBlocks.COARSE_DIRT_SLAB || blockDown == SoilStairsBlocks.PODZOL_SLAB) {
			if (bsDown.get(SlabBlock.TYPE) == SlabType.TOP || bsDown.get(SlabBlock.TYPE) == SlabType.DOUBLE) {
				valid = true;
			}
		}
		
		if (valid) {
			BlockPos blockPos = pos.down();
			Iterator<Direction> horzIter = Direction.Type.HORIZONTAL.iterator();

			while (horzIter.hasNext()) {
				Direction direction = (Direction)horzIter.next();
				BlockState blockState = viewableWorld.getBlockState(blockPos.offset(direction));
				FluidState fluidState = viewableWorld.getFluidState(blockPos.offset(direction));
				if (fluidState.matches(FluidTags.WATER) || blockState.getBlock() == Blocks.FROSTED_ICE) {
					info.setReturnValue(true);
				}
			}
		}
	}
}
