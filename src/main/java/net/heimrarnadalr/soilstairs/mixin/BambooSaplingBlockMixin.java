package net.heimrarnadalr.soilstairs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.heimrarnadalr.soilstairs.block.SoilStairsBlocks;
import net.minecraft.block.BambooSaplingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

@Mixin(BambooSaplingBlock.class)
public class BambooSaplingBlockMixin {
	@Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
	public void canPlaceAt(BlockState bs, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		if (SoilStairsBlocks.isValidSoilStairForBamboo(world, pos)) {
			info.setReturnValue(true);
		}
	}
}
