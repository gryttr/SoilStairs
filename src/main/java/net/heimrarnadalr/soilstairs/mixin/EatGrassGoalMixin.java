package net.heimrarnadalr.soilstairs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.heimrarnadalr.soilstairs.block.SoilStairsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

@Mixin(EatGrassGoal.class)
public class EatGrassGoalMixin {
	@Shadow
	private World world;
	@Shadow
	private MobEntity mob;
	
	@Inject(at = @At(args="log=true",value = "INVOKE", target = "java/util/function/Predicate.test(Ljava/lang/Object;)Z"), method = "canStart", cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
	public void canStart(CallbackInfoReturnable<Boolean> ci, BlockPos pos) {
		boolean betweenBlocks = (Math.round(mob.getY()) - mob.getY()) > 0;
		BlockPos posDown = betweenBlocks ? pos : pos.down();
		Block block = world.getBlockState(posDown).getBlock();
		if (block == SoilStairsBlocks.GRASS_STAIRS || block == SoilStairsBlocks.GRASS_SLAB) {
			ci.setReturnValue(true);
		}
	}

	@Inject(at = @At(args="log=true",value = "INVOKE", target = "java/util/function/Predicate.test(Ljava/lang/Object;)Z"), method = "tick", cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
	public void tick(CallbackInfo ci, BlockPos pos) {
		boolean betweenBlocks = (Math.round(mob.getY()) - mob.getY()) > 0;
		BlockPos posDown = betweenBlocks ? pos : pos.down();
		BlockState bs = world.getBlockState(posDown);
		Block block = bs.getBlock();
		if (block == SoilStairsBlocks.GRASS_STAIRS || block == SoilStairsBlocks.GRASS_SLAB) {
			if (this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
				this.world.playLevelEvent(2001, posDown, Block.getRawIdFromState(Blocks.GRASS_BLOCK.getDefaultState()));
				if (block == SoilStairsBlocks.GRASS_STAIRS) {
					this.world.setBlockState(posDown, SoilStairsBlocks.DIRT_STAIRS.getDefaultState()
							.with(StairsBlock.FACING, bs.get(StairsBlock.FACING))
							.with(StairsBlock.HALF, bs.get(StairsBlock.HALF))
							.with(StairsBlock.SHAPE, bs.get(StairsBlock.SHAPE))
							.with(StairsBlock.WATERLOGGED, bs.get(StairsBlock.WATERLOGGED))
						, 2);
				} else {
					this.world.setBlockState(posDown, SoilStairsBlocks.DIRT_SLAB.getDefaultState()
							.with(SlabBlock.TYPE, bs.get(SlabBlock.TYPE))
							.with(SlabBlock.WATERLOGGED, bs.get(SlabBlock.WATERLOGGED))
						, 2);
				}
			}

			this.mob.onEatingGrass();
		}
	}
}
