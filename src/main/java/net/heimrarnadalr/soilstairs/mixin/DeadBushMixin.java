package net.heimrarnadalr.soilstairs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.heimrarnadalr.soilstairs.block.SoilStairsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

@Mixin(DeadBushBlock.class)
public class DeadBushMixin {
	@Inject(at = @At("HEAD"), method = "canPlantOnTop", cancellable = true)
	public void canPlantOnTop(BlockState bs, BlockView view, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		Block block = bs.getBlock();
		if (block == SoilStairsBlocks.DIRT_STAIRS || block == SoilStairsBlocks.COARSE_DIRT_STAIRS || block == SoilStairsBlocks.PODZOL_STAIRS) {
			if (bs.get(StairsBlock.HALF) == BlockHalf.TOP) {
				info.setReturnValue(true);
			}
		} else if (block == SoilStairsBlocks.DIRT_SLAB || block == SoilStairsBlocks.COARSE_DIRT_SLAB || block == SoilStairsBlocks.PODZOL_SLAB) {
			if (bs.get(SlabBlock.TYPE) == SlabType.TOP || bs.get(SlabBlock.TYPE) == SlabType.DOUBLE) {
				info.setReturnValue(true);
			}
		}
	}
}
