package noki.savemoney.event;

import java.util.HashMap;
import java.util.UUID;

import shift.mceconomy2.api.MCEconomyAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import noki.savemoney.SaveMoneyCore;
import noki.savemoney.item.ItemInsurance;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;


/**********
 * @class EventPlayerDeath
 *
 * @description
 * @description_en
 * 
 * @see ItemInsurance, EventPlayerRespawn.
 */
public class EventPlayerDeath {
	
	//******************************//
	// define member variables.
	//******************************//
	public static HashMap<UUID, Integer> insurances = new HashMap<UUID, Integer>();
	
	
	//******************************//
	// define member methods.
	//******************************//
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event) {
		
		if(event.entityLiving.worldObj.isRemote) {
			return;
		}
		
		if(!(event.entityLiving instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer)event.entityLiving;	
		
/*		if(MCEconomyAPI.getPlayerMP(player) <= 0) {
			return;
		}*/
		
		InventoryPlayer inventory = player.inventory;
		
		int targetSlot = -1;
		int targetExpire = Integer.MAX_VALUE;
		for(int i=0; i<inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack != null && stack.getItem() instanceof ItemInsurance) {
				int tempExpire = ItemInsurance.getExpire(stack);
				if(ItemInsurance.isValid(stack, player.worldObj) && tempExpire < targetExpire) {
					targetSlot = i;
					targetExpire = tempExpire;
				}
			}
		}
		
		if(targetSlot == -1) {
			return;
		}
		insurances.put(player.getGameProfile().getId(), MCEconomyAPI.getPlayerMP(player));
		inventory.setInventorySlotContents(targetSlot, null);
		
	}
	
	// for debug.
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event) {
		
		if(SaveMoneyCore.debugFlag == false) {
			return;
		}
		
		if(event.entityLiving.worldObj.isRemote) {
			return;
		}
		if(event.entityLiving instanceof EntityPlayer) {
			return;
		}
		if(event.source.damageType.equals("player") == false) {
			return;
		}
		
		MCEconomyAPI.addPlayerMP((EntityPlayer)((EntityDamageSource)event.source).getEntity(), 1000, false);
		
	}

}
