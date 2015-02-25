package noki.savemoney.packet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noki.savemoney.SaveMoneyCore;
import noki.savemoney.SaveMoneyData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;


/**********
 * @class PacketPurseDrawResponseHandler
 *
 * @description
 * @description_en
 * 
 * @see ItemPurse, GuiPurse, PacketPurseDrawResponse,
 */
public class PacketPurseDrawResponseHandler implements IMessageHandler<PacketPurseDrawResponse, IMessage> {

	//******************************//
	// define member variables.
	//******************************//
	
	
	//******************************//
	// define member methods.
	//******************************//
	@Override
	public IMessage onMessage(PacketPurseDrawResponse message, MessageContext ctx) {
		
		if(message.success == false) {
			SaveMoneyCore.log("not success.");
			return null;
		}
		
//		World world = Minecraft.getMinecraft().theWorld;
		World world = SaveMoneyCore.proxy.getClientWorld();
		if(world == null || world.provider.dimensionId != message.dimensionID) {
			SaveMoneyCore.log("incorrect world.");
			return null;
		}
		
		Entity entity = world.getEntityByID(message.playerID);
		if(entity == null || !(entity instanceof EntityPlayer)) {
			SaveMoneyCore.log("not player.");
			return null;
		}
		EntityPlayer player = (EntityPlayer)entity;
		
		ItemStack stack = player.getHeldItem();
		if(stack == null || stack.getItem() != SaveMoneyData.itemPurse) {
			if(stack == null) {
				SaveMoneyCore.log("stack is null.");
			}
			else {
				SaveMoneyCore.log("stack is not purse.");
			}
			return null;
		}
		
		SaveMoneyCore.proxy.updateClientGui(-1*message.money);
		
		return null;
		
	}

}
