package ihamfp.IhTech.TileEntities;

import ihamfp.IhTech.interfaces.ITileEntityEnergyStorage.EnumEnergySideTypes;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.EnumSkyBlock;

public class TileEntitySolarEnergyGenerator extends TileEntityEnergyStorage implements ITickable {
	public TileEntitySolarEnergyGenerator() {}
	
	public int energyPerTickPerLight = 10;
	
	@Override
	public void update() {
		int energyGenerated = this.getEnergyProducedPerTick();
		this.energy.receiveEnergy(energyGenerated, false);
		this.updateGlobalEnergySharing();
		this.markDirty();
	}
	
	public int getEnergyProducedPerTick() {
		int lightLevel = this.worldObj.getLightFor(EnumSkyBlock.SKY, this.getPos().up()) - this.worldObj.calculateSkylightSubtracted(1.0f);
		return this.energyPerTickPerLight * lightLevel;
	}
	
	@Override
	public EnumEnergySideTypes getEnergySideType(EnumFacing face) {
		if (face == EnumFacing.UP)
			return EnumEnergySideTypes.BLOCKED;
		return EnumEnergySideTypes.SEND;
	}
}