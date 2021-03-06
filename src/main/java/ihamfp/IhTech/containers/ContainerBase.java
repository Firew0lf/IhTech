package ihamfp.IhTech.containers;

import ihamfp.IhTech.interfaces.ITileEntityInteractable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public abstract class ContainerBase<T extends TileEntity> extends Container {
	public static final int SLOTS_COUNT = 0;
	
	protected T te;
	
	public ContainerBase(IInventory playerInventory, T te) {
		this.te = te;
		this.addOwnSlots();
		this.addPlayerSlots(playerInventory);
	}
	
	protected abstract void addOwnSlots();
	
	protected int getOwnSlotsCount() {
		return SLOTS_COUNT;
	}
	
	private void addPlayerSlots(IInventory playerInventory) {
		this.addPlayerSlots(playerInventory, 8, 86); // default for "27" types GUIs
	}
	
	private void addPlayerSlots(IInventory playerInventory, int invX, int invY) {
		for (int row = 0; row < 9; ++row) {
			int x = 8 + row * 18;
			this.addSlotToContainer(new Slot(playerInventory, row, x, 144));
		}
		
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				int x = invX + col * 18;
				int y =invY + row * 18;
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
			}
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		Slot slot = this.inventorySlots.get(index);
		int slotsCount = this.inventorySlots.size()-1;
		
		if (slot == null || !slot.getHasStack()) return ItemStack.EMPTY;
		ItemStack itemStack1 = slot.getStack();
		ItemStack itemStack = itemStack1.copy();

		if (index < this.getOwnSlotsCount()) { // TE to inv.
			if (!mergeItemStack(itemStack1, this.getOwnSlotsCount(), slotsCount, false)) {
				return ItemStack.EMPTY;
			}
		} else { // inv to TE
			if (!mergeItemStack(itemStack1, 0, this.getOwnSlotsCount(), false)) {
				return ItemStack.EMPTY;
			}
		}
		
		if (itemStack1.isEmpty()) {
			slot.putStack(ItemStack.EMPTY);
		} else if (itemStack.getCount() == itemStack1.getCount()) {
			return ItemStack.EMPTY;
		} else {
			slot.onSlotChanged();
		}

		return itemStack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		if (te instanceof ITileEntityInteractable) {
			return ((ITileEntityInteractable)te).canInteractWith(playerIn);
		}
		return false;
	}
}
