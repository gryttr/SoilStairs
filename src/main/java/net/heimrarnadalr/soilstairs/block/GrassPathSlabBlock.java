package net.heimrarnadalr.soilstairs.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class GrassPathSlabBlock extends SlabBlock {
	protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
	protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0D, 7.0D, 0.0D, 16.0D, 15.0D, 16.0D);
	protected static final VoxelShape DOUBLE_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

	public GrassPathSlabBlock(Settings settings) {
		super(settings);
	}

	public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
			SlabType slabType_1 = (SlabType)blockState_1.get(TYPE);
			switch(slabType_1) {
			case DOUBLE:
				return DOUBLE_SHAPE;
			case TOP:
				return TOP_SHAPE;
			default:
				return BOTTOM_SHAPE;
			}
		}
}
