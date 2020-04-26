package net.heimrarnadalr.soilstairs.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.heimrarnadalr.soilstairs.block.SoilStairsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

@Mixin(StemBlock.class)
public class StemBlockMixin {
	@Inject(at = @At(args="log=true", value = "INVOKE_ASSIGN", target = "net/minecraft/block/BlockState.getBlock()Lnet/minecraft/block/Block;", ordinal = 0), method = "scheduledTick", locals = LocalCapture.CAPTURE_FAILSOFT)
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci, Direction direction, BlockPos posDir, Block groundBlock) {
		System.out.println("Attempting to plant gourd on " + groundBlock.getName());
		BlockState groundState = world.getBlockState(posDir.down());
		boolean validGourdSpot = false;
		if (groundBlock == SoilStairsBlocks.DIRT_SLAB || groundBlock == SoilStairsBlocks.COARSE_DIRT_SLAB || groundBlock == SoilStairsBlocks.PODZOL_SLAB || groundBlock == SoilStairsBlocks.GRASS_SLAB) {
			if (groundState.get(SlabBlock.TYPE) == SlabType.TOP || groundState.get(SlabBlock.TYPE) == SlabType.DOUBLE) {
				validGourdSpot = true;
			}
		} else if (groundBlock == SoilStairsBlocks.DIRT_STAIRS || groundBlock == SoilStairsBlocks.COARSE_DIRT_STAIRS || groundBlock == SoilStairsBlocks.PODZOL_STAIRS || groundBlock == SoilStairsBlocks.GRASS_STAIRS) {
			if (groundState.get(StairsBlock.HALF) == BlockHalf.TOP) {
				validGourdSpot = true;
			}
		}
		
		if (validGourdSpot && world.getBlockState(posDir).isAir()) {
			StemBlock stem = (StemBlock)(Object)this;
			world.setBlockState(posDir, stem.getGourdBlock().getDefaultState());
			world.setBlockState(pos, stem.getGourdBlock().getAttachedStem().getDefaultState().with(HorizontalFacingBlock.FACING, direction));
		}
	}
}
