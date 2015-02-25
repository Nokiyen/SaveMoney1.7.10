package noki.savemoney.packet;

import net.minecraft.world.World;
import noki.savemoney.SaveMoneyCore;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;


/**********
 * @class PacketSavingsBrokenHandler
 *
 * @description
 * @description_en
 * 
 * @see BlockSavings, PacketSavingsBroken, PacketHandler.
 */
public class PacketSavingsBrokenHandler implements IMessageHandler<PacketSavingsBroken, IMessage> {

	//******************************//
	// define member variables.
	//******************************//
	
	
	//******************************//
	// define member methods.
	//******************************//
	@Override
	public IMessage onMessage(PacketSavingsBroken message, MessageContext ctx) {
		
//		World world = Minecraft.getMinecraft().theWorld;
		World world = SaveMoneyCore.proxy.getClientWorld();
		if(world == null || world.provider.dimensionId != message.dimensionID) {
			return null;
		}
		
//		MCEconomyAPI.playCoinSoundEffect(world, message.posX, message.posY, message.posZ);
/*		world.playSound((double)message.posX+0.5D, (double)message.posY+0.5D, (double)message.posZ+0.5D, "mceconomy2:coin",
				1.0F, 1.0F, true);*/
		world.playSound((double)message.posX+0.5D, (double)message.posY+0.5D, (double)message.posZ+0.5D,
				"mob.pig.death", 2.0F, world.rand.nextFloat() * 0.1F + 0.9F, true);
		
		return null;
		
	}

}
