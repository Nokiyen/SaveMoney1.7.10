package noki.savemoney.event;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import noki.savemoney.SaveMoneyCore;
import noki.savemoney.SaveMoneyData;
import noki.savemoney.gui.ContainerInsurer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;


/**********
 * @class EventInteract
 *
 * @description
 * @description_en
 * 
 * @see GuiInsurer.
 */
public class EventInteract {
	
	//******************************//
	// define member variables.
	//******************************//
	
	
	//******************************//
	// define member methods.
	//******************************//
	@SubscribeEvent
	public void onEntityInteract(EntityInteractEvent event) {
		
		if(!(event.target instanceof EntityVillager)) {
			return;
		}
		if(((EntityVillager)event.target).getProfession() != SaveMoneyData.villagerIDInsurer) {
			return;
		}
		
		EntityPlayer player = event.entityPlayer;
		player.openGui(SaveMoneyCore.instance, 1, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
		if(player.openContainer != null && player.openContainer instanceof ContainerInsurer) {
			((ContainerInsurer)player.openContainer).setVillager((EntityVillager)event.target);
		}
/*		if(!player.worldObj.isRemote) {
			if(player.openContainer != null && player.openContainer instanceof ContainerInsurer) {
				((ContainerInsurer)player.openContainer).updateSelling();
			}
		}*/
		
		event.setCanceled(true);
		
	}

}
