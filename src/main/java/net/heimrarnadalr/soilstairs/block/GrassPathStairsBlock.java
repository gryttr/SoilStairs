package net.heimrarnadalr.soilstairs.block;

import java.util.Random;
import java.util.stream.IntStream;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.entity.EntityContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;

public class GrassPathStairsBlock extends SoilStairsBlock {
	protected static final VoxelShape TOP_SHAPE;
	protected static final VoxelShape BOTTOM_SHAPE;
	protected static final VoxelShape BOTTOM_NORTH_WEST_CORNER_SHAPE;
	protected static final VoxelShape BOTTOM_SOUTH_WEST_CORNER_SHAPE;
	protected static final VoxelShape TOP_NORTH_WEST_CORNER_SHAPE;
	protected static final VoxelShape TOP_SOUTH_WEST_CORNER_SHAPE;
	protected static final VoxelShape BOTTOM_NORTH_EAST_CORNER_SHAPE;
	protected static final VoxelShape BOTTOM_SOUTH_EAST_CORNER_SHAPE;
	protected static final VoxelShape TOP_NORTH_EAST_CORNER_SHAPE;
	protected static final VoxelShape TOP_SOUTH_EAST_CORNER_SHAPE;
	protected static final VoxelShape[] TOP_SHAPES;
	protected static final VoxelShape[] BOTTOM_SHAPES;
	private static final int[] SHAPE_INDICES;

	protected GrassPathStairsBlock(BlockState blockState, Settings settings) {
		super(blockState, settings);
	}

	public VoxelShape getOutlineShape(BlockState bs, BlockView blockView, BlockPos pos, EntityContext entityContext) {
		return (bs.get(HALF) == BlockHalf.TOP ? TOP_SHAPES : BOTTOM_SHAPES)[SHAPE_INDICES[this.getShapeIndexIndex(bs)]];
	}
	
	private int getShapeIndexIndex(BlockState bs) {
		return ((StairShape)bs.get(SHAPE)).ordinal() * 4 + ((Direction)bs.get(FACING)).getHorizontal();
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState bs, Direction dir, BlockState bs2, IWorld iWorld, BlockPos pos, BlockPos pos2) {
		if (dir == Direction.UP && !bs.canPlaceAt(iWorld, pos)) {
			iWorld.getBlockTickScheduler().schedule(pos, this, 1);
		}

		return super.getStateForNeighborUpdate(bs, dir, bs2, iWorld, pos, pos);
	}

	@Override
	public void scheduledTick(BlockState bs, ServerWorld world, BlockPos pos, Random rand) {
		world.setBlockState(pos, SoilStairsBlocks.DIRT_STAIRS.getDefaultState()
				.with(FACING, bs.get(FACING))
				.with(HALF, bs.get(HALF))
				.with(SHAPE, bs.get(SHAPE))
				.with(WATERLOGGED, bs.get(WATERLOGGED))
			);
	}


	private static VoxelShape[] composeShapes(VoxelShape voxelShape_1, VoxelShape voxelShape_2, VoxelShape voxelShape_3, VoxelShape voxelShape_4, VoxelShape voxelShape_5) {
		return (VoxelShape[])IntStream.range(0, 16).mapToObj((int_1) -> {
			return composeShape(int_1, voxelShape_1, voxelShape_2, voxelShape_3, voxelShape_4, voxelShape_5);
		}).toArray((int_1) -> {
			return new VoxelShape[int_1];
		});
	}

	private static VoxelShape composeShape(int int_1, VoxelShape voxelShape_1, VoxelShape voxelShape_2, VoxelShape voxelShape_3, VoxelShape voxelShape_4, VoxelShape voxelShape_5) {
		VoxelShape voxelShape_6 = voxelShape_1;
		if ((int_1 & 1) != 0) {
			voxelShape_6 = VoxelShapes.union(voxelShape_1, voxelShape_2);
		}

		if ((int_1 & 2) != 0) {
			voxelShape_6 = VoxelShapes.union(voxelShape_6, voxelShape_3);
		}

		if ((int_1 & 4) != 0) {
			voxelShape_6 = VoxelShapes.union(voxelShape_6, voxelShape_4);
		}

		if ((int_1 & 8) != 0) {
			voxelShape_6 = VoxelShapes.union(voxelShape_6, voxelShape_5);
		}

		return voxelShape_6;
	}

	static {
		TOP_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 15.0D, 16.0D);
		BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
		BOTTOM_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
		BOTTOM_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
		TOP_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 7.0D, 0.0D, 8.0D, 15.0D, 8.0D);
		TOP_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 7.0D, 8.0D, 8.0D, 15.0D, 16.0D);
		BOTTOM_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
		BOTTOM_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
		TOP_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 7.0D, 0.0D, 16.0D, 15.0D, 8.0D);
		TOP_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 7.0D, 8.0D, 16.0D, 15.0D, 16.0D);
		TOP_SHAPES = composeShapes(TOP_SHAPE, BOTTOM_NORTH_WEST_CORNER_SHAPE, BOTTOM_NORTH_EAST_CORNER_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE, BOTTOM_SOUTH_EAST_CORNER_SHAPE);
		BOTTOM_SHAPES = composeShapes(BOTTOM_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE, TOP_NORTH_EAST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE);
		SHAPE_INDICES = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
	}
}
