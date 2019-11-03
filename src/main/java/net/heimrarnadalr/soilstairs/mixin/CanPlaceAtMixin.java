package net.heimrarnadalr.soilstairs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.heimrarnadalr.soilstairs.block.SoilStairsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ViewableWorld;

@Mixin(MushroomPlantBlock.class)
public class CanPlaceAtMixin {
	@Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
	public void canPlaceAt(BlockState bs, ViewableWorld viewableWorld, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		BlockPos posDown = pos.down();
		BlockState bsDown = viewableWorld.getBlockState(posDown);
		Block blockDown = bsDown.getBlock();
		if (blockDown == SoilStairsBlocks.MYCELIUM_STAIRS || blockDown == SoilStairsBlocks.PODZOL_STAIRS) {
			if (bsDown.get(StairsBlock.HALF) == BlockHalf.TOP) {
				info.setReturnValue(true);
			}
		} else if (blockDown == SoilStairsBlocks.MYCELIUM_SLAB || blockDown == SoilStairsBlocks.PODZOL_SLAB) {
			if (bsDown.get(SlabBlock.TYPE) == SlabType.TOP || bsDown.get(SlabBlock.TYPE) == SlabType.DOUBLE) {
				info.setReturnValue(true);
			}
		}
	}
}
