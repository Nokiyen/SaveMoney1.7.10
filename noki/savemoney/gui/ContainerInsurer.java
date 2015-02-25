package noki.savemoney.gui;

import shift.mceconomy2.api.MCEconomyAPI;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noki.savemoney.SaveMoneyCore;
import noki.savemoney.SaveMoneyData;
import noki.savemoney.item.ItemInsurance;


/**********
 * @class ContainerInsurer.
 *
 * @description
 * @description_en
 * 
 * @see GuiContainerInsurer, EventInteract.
 */
public class ContainerInsurer extends Container {
	
	//******************************//
	// define member variables.
	//******************************//
	private EntityPlayer player;
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private EntityVillager villager;
	
	private IInventory sellingInventory;
	
	
	//******************************//
	// define member methods.
	//******************************//
	public ContainerInsurer(EntityPlayer player, World world, int x, int y, int z) {

		this.player = player;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		// bookrest's inventory.
		this.sellingInventory = new InventoryBasic("Insurer", true, 3) {
			public void markDirty() {
				super.markDirty();
//				ContainerInsurer.this.onCraftMatrixChanged(this);
			}
		};
		
		this.addSlotToContainer(new SlotInsurance(this.sellingInventory, 0, 35, 30, 10));
		this.addSlotToContainer(new SlotInsurance(this.sellingInventory, 1, 80, 30, 100));
		this.addSlotToContainer(new SlotInsurance(this.sellingInventory, 2, 125, 30, 500));
		
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, i * 18 + 84));
			}
		}
		for(int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}
		
		this.updateSelling();
		
	}
	
	public void setVillager(EntityVillager villager) {
		
		SaveMoneyCore.log("set villager.");
		this.villager = villager;
		villager.setCustomer(this.player);
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		
		return player.getDistanceSq(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D) <= 64D;
		
	}
	
/*	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		
		super.onCraftMatrixChanged(inventory);
		if(inventory == this.sellingInventory) {
			this.updateSelling();
		}
		
	}*/
	
	public void updateSelling() {
		
		if(SaveMoneyData.dependency_MPGuard == true) {
			this.sellingInventory.setInventorySlotContents(2, null);
			this.sellingInventory.setInventorySlotContents(1, null);
			this.sellingInventory.setInventorySlotContents(0, null);			
			return;
		}
		
		int currentMP = MCEconomyAPI.getPlayerMP(this.player);
		
		if(currentMP >= 500) {
			if(this.sellingInventory.getStackInSlot(2) == null || this.sellingInventory.getStackInSlot(2).stackSize == 0) {
				this.sellingInventory.setInventorySlotContents(2, ItemInsurance.getNewInsurance(30, this.world));
			}
		}
		else if(this.sellingInventory.getStackInSlot(2) != null) {
			this.sellingInventory.setInventorySlotContents(2, null);
		}
		
		if(currentMP >= 100) {
			if(this.sellingInventory.getStackInSlot(1) == null  || this.sellingInventory.getStackInSlot(1).stackSize == 0) {
				this.sellingInventory.setInventorySlotContents(1, ItemInsurance.getNewInsurance(7, this.world));
			}
		}
		else if(this.sellingInventory.getStackInSlot(1) != null) {
			this.sellingInventory.setInventorySlotContents(1, null);
		}
		
		if(currentMP >= 10) {
			if(this.sellingInventory.getStackInSlot(0) == null || this.sellingInventory.getStackInSlot(0).stackSize == 0) {
				this.sellingInventory.setInventorySlotContents(0, ItemInsurance.getNewInsurance(0, this.world));
			}
		}
		else if(this.sellingInventory.getStackInSlot(0) != null) {
			this.sellingInventory.setInventorySlotContents(0, null);
		}
		
		this.detectAndSendChanges();
		
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		
/*		if(3 <= index) {
			return null;
		}
		
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if(!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return null;
			}
			slot.onPickupFromSlot(playerIn, itemstack1);
			return itemstack;
		}
		
		this.onCraftMatrixChanged(this.sellingInventory);*/
		return null;
		
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		
		super.onContainerClosed(player);
		
		SaveMoneyCore.log("unset villager.");
		if(this.villager != null) {
			this.villager.setCustomer(null);
		}
		
	}
	
	private class SlotInsurance extends Slot {
		
		int price;
		
		public SlotInsurance(IInventory inventory, int slotID, int x, int y, int price) {
			
			super(inventory, slotID, x, y);
			this.price = price;
			
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			
			return false;
			
		}
		
		@Override
		public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
			
			this.onSlotChanged();
			MCEconomyAPI.reducePlayerMP(player, this.price, false);
			ContainerInsurer.this.updateSelling();
			
		}
		
/*		@Override
		public void onSlotChanged() {
			
		}*/
		
	}
	
}
