package net.heimrarnadalr.soilstairs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.heimrarnadalr.soilstairs.block.SoilStairsBlocks;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

@Mixin(BambooBlock.class)
public class BambooBlockMixin {
	@Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
	public void canPlaceAt(BlockState bs, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		if (SoilStairsBlocks.isValidSoilStairForBamboo(world, pos)) {
			info.setReturnValue(true);
		}
	}
	
	@Inject(at = @At("HEAD"), method = "getPlacementState", cancellable = true)
	public void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> info) {
		if (SoilStairsBlocks.isValidSoilStairForBamboo(ctx.getWorld(), ctx.getBlockPos())) {
			info.setReturnValue(Blocks.BAMBOO_SAPLING.getDefaultState());
		}
	}
}
