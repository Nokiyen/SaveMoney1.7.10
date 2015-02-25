package noki.savemoney.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import noki.savemoney.packet.PacketHandler;
import noki.savemoney.packet.PacketInsurance;
import shift.mceconomy2.api.MCEconomyAPI;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;


/**********
 * @class EventPlayerRespawn
 *
 * @description
 * @description_en
 * 
 * @see ItemInsurance, EventPlayerDeath.
 */
public class EventPlayerRespawn {
	
	//******************************//
	// define member variables.
	//******************************//
	
	
	//******************************//
	// define member methods.
	//******************************//
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		
		EntityPlayer player = event.player;
		
		if(player.worldObj.isRemote) {
			return;
		}
		
		if(EventPlayerDeath.insurances.containsKey(player.getGameProfile().getId()) == false) {
			return;
		}
		
		int insurance = EventPlayerDeath.insurances.get(player.getGameProfile().getId());
		EventPlayerDeath.insurances.remove(player.getGameProfile().getId());
		MCEconomyAPI.addPlayerMP(event.player, insurance, false);
		PacketHandler.instance.sendTo(new PacketInsurance(event.player.getEntityId()), (EntityPlayerMP)player);
		
	}

}
