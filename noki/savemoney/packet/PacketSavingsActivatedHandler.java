package noki.savemoney.packet;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import noki.savemoney.SaveMoneyCore;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;


/**********
 * @class PacketSavingsActivatedHandler
 *
 * @description
 * @description_en
 * 
 * @see BlockSavings, PacketSavingsActivated, PacketHandler.
 */
public class PacketSavingsActivatedHandler implements IMessageHandler<PacketSavingsActivated, IMessage> {

	//******************************//
	// define member variables.
	//******************************//
	
	
	//******************************//
	// define member methods.
	//******************************//
	public PacketSavingsActivatedHandler() {
		
	}
	
	@Override
	public IMessage onMessage(PacketSavingsActivated message, MessageContext ctx) {
		
//		World world = Minecraft.getMinecraft().theWorld;
		World world = SaveMoneyCore.proxy.getClientWorld();
		if(world == null || world.provider.dimensionId != message.dimensionID) {
			return null;
		}
		
		Block block = world.getBlock(message.posX, message.posY, message.posZ);
//		MCEconomyAPI.playCoinSoundEffect(world, message.posX, message.posY, message.posZ);
/*		world.playSound((double)message.posX+0.5D, (double)message.posY+0.5D, (double)message.posZ+0.5D,
				"mceconomy2:coin", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F, true);*/
		world.playSound((double)message.posX+0.5D, (double)message.posY+0.5D, (double)message.posZ+0.5D,
				"mceconomy2:coin", 0.6F, 1.0F, true);
		for (int i = 0; i < 5; i++) {
			double d0 = world.rand.nextGaussian() * 0.02D;
			double d1 = world.rand.nextGaussian() * 0.02D;
			double d2 = world.rand.nextGaussian() * 0.02D;
			world.spawnParticle("happyVillager",
					(double)((float)message.posX + world.rand.nextFloat()),
					(double)message.posY + (double)world.rand.nextFloat() * block.getBlockBoundsMaxY(),
					(double)((float)message.posZ + world.rand.nextFloat()), d0, d1, d2);
		}
		
		return null;
		
	}

}
