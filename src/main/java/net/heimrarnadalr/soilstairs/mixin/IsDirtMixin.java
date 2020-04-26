package net.heimrarnadalr.soilstairs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.heimrarnadalr.soilstairs.block.SoilStairsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;

@Mixin(AbstractTreeFeature.class)
public class IsDirtMixin {
	// Only dirt, coarse dirt, and podzol
	@Inject(at = @At("HEAD"), method = "isNaturalDirt", cancellable = true)
	private static void isNaturalDirt(TestableWorld world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		if (world.testBlockState(pos, (bs) -> {
			Block block = bs.getBlock();
			if ((block == SoilStairsBlocks.DIRT_SLAB || block == SoilStairsBlocks.COARSE_DIRT_SLAB || block == SoilStairsBlocks.PODZOL_SLAB) 
					&& bs.get(SlabBlock.TYPE) == SlabType.DOUBLE) {
				return true;
			} else {
				return false;
			}
		})) {
			info.setReturnValue(true);
		}
	}

	// Natural dirt + grass and mycelium
	@Inject(at = @At("HEAD"), method = {"isNaturalDirtOrGrass", "isDirtOrGrass"}, cancellable = true)
	private static void isNaturalDirtOrGrass(TestableWorld world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		if (world.testBlockState(pos, (bs) -> {
			Block block = bs.getBlock();
			if ((block == SoilStairsBlocks.DIRT_SLAB || block == SoilStairsBlocks.COARSE_DIRT_SLAB || block == SoilStairsBlocks.PODZOL_SLAB || block == SoilStairsBlocks.GRASS_SLAB || block == SoilStairsBlocks.MYCELIUM_SLAB) 
					&& bs.get(SlabBlock.TYPE) == SlabType.DOUBLE) {
				return true;
			} else {
				return false;
			}
		})) {
			info.setReturnValue(true);
		}
	}
}
