package net.heimrarnadalr.soilstairs.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.heimrarnadalr.soilstairs.block.SoilStairsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.decorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.feature.AbstractTreeFeature;

@Mixin(AlterGroundTreeDecorator.class)
public class AlterGroundTreeDecoratorMixin {
	@Inject(at = @At("HEAD"), method = "method_23463")
	public void method_23463(ModifiableTestableWorld modifiableTestableWorld, Random random, BlockPos blockPos, CallbackInfo ci) {
		for (int i = 2; i >= -3; --i) {
			BlockPos posUp = blockPos.up(i);
			setPodzol(modifiableTestableWorld, posUp);

			if (!AbstractTreeFeature.isAir(modifiableTestableWorld, posUp) && i < 0) {
				break;
			}
		}
	}
	
	private boolean isNaturalDirtOrGrassStairs(ModifiableTestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (bs) -> {
			Block block = bs.getBlock();
			return block == SoilStairsBlocks.DIRT_STAIRS || block == SoilStairsBlocks.COARSE_DIRT_STAIRS || block == SoilStairsBlocks.PODZOL_STAIRS || block == SoilStairsBlocks.GRASS_STAIRS || block == SoilStairsBlocks.MYCELIUM_STAIRS;
		});
	}

	private boolean isNaturalDirtOrGrassSlab(ModifiableTestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (bs) -> {
			Block block = bs.getBlock();
			return block == SoilStairsBlocks.DIRT_SLAB || block == SoilStairsBlocks.COARSE_DIRT_SLAB || block == SoilStairsBlocks.PODZOL_SLAB || block == SoilStairsBlocks.GRASS_SLAB || block == SoilStairsBlocks.MYCELIUM_SLAB;
		});
	}
	
	private void setPodzol(ModifiableTestableWorld world, BlockPos pos) {
		if (isNaturalDirtOrGrassStairs(world, pos)) {
			BlockState podzolState = SoilStairsBlocks.PODZOL_STAIRS.getDefaultState();
			world.testBlockState(pos, (bs) -> {
				world.setBlockState(pos, 
									podzolState.with(StairsBlock.FACING, bs.get(StairsBlock.FACING))
												.with(StairsBlock.HALF, bs.get(StairsBlock.HALF))
												.with(StairsBlock.SHAPE, bs.get(StairsBlock.SHAPE))
												.with(StairsBlock.WATERLOGGED, bs.get(StairsBlock.WATERLOGGED)),
									19);
				return true;
			});
		} else if (isNaturalDirtOrGrassSlab(world, pos)) {
			BlockState podzolState = SoilStairsBlocks.PODZOL_SLAB.getDefaultState();
			world.testBlockState(pos, (bs) -> {
				world.setBlockState(pos, 
									podzolState.with(SlabBlock.TYPE, bs.get(SlabBlock.TYPE))
												.with(SlabBlock.WATERLOGGED, bs.get(SlabBlock.WATERLOGGED)),
									19);
				return true;
			});
		}
	}
}
