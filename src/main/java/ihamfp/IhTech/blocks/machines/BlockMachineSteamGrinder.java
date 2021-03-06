package ihamfp.IhTech.blocks.machines;

import ihamfp.IhTech.TileEntities.machines.TileEntitySteamGrinder;
import ihamfp.IhTech.common.GuiHandler.EnumGUIs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMachineSteamGrinder extends BlockMachineBase<TileEntitySteamGrinder> {
	public static int GUI_ID = EnumGUIs.GUI_STGRINDER.ordinal();
	
	public BlockMachineSteamGrinder(String name) {
		super(name, new TileEntitySteamGrinder());
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ) || tryOpenGUI(world, pos, player, this.GUI_ID);
	}
}
