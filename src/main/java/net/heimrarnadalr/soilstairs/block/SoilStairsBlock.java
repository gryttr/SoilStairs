package net.heimrarnadalr.soilstairs.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class SoilStairsBlock extends StairsBlock {
	public final Block baseBlock;
	
	protected SoilStairsBlock(BlockState blockState, Settings settings) {
		super(blockState, settings);
		this.baseBlock = blockState.getBlock();
	}

}
