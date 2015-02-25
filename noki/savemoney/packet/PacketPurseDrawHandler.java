package noki.savemoney.packet;

import shift.mceconomy2.api.MCEconomyAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import noki.savemoney.SaveMoneyData;
import noki.savemoney.item.ItemPurse;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;


/**********
 * @class PacketPurseDrawHandler
 *
 * @description
 * @description_en
 * 
 * @see ItemPurse, GuiPurse, PacketPurseDraw, PacketPurseDrawResponse, PacketPurseDrawResponseHander,
 */
public class PacketPurseDrawHandler implements IMessageHandler<PacketPurseDraw, PacketPurseDrawResponse> {

	//******************************//
	// define member variables.
	//******************************//
	
	
	//******************************//
	// define member methods.
	//******************************//
	@Override
	public PacketPurseDrawResponse onMessage(PacketPurseDraw message, MessageContext ctx) {
		
		if(message.money == 0) {
			return new PacketPurseDrawResponse(message.dimensionID, message.money, message.playerID, false);			
		}
		
		World world = MinecraftServer.getServer().worldServerForDimension(message.dimensionID);
		if(world == null) {
			return new PacketPurseDrawResponse(message.dimensionID, message.money, message.playerID, false);
		}
		
		Entity entity = world.getEntityByID(message.playerID);
		if(entity == null || !(entity instanceof EntityPlayer)) {
			return new PacketPurseDrawResponse(message.dimensionID, message.money, message.playerID, false);
		}
		EntityPlayer player = (EntityPlayer)entity;
		
		ItemStack stack = player.getHeldItem();
		if(stack == null || stack.getItem() != SaveMoneyData.itemPurse) {
			return new PacketPurseDrawResponse(message.dimensionID, message.money, message.playerID, false);
		}
		
		if(ItemPurse.getMoney(stack) < message.money) {
			return new PacketPurseDrawResponse(message.dimensionID, message.money, message.playerID, false);
		}
		
		MCEconomyAPI.addPlayerMP((EntityPlayer)entity, message.money, false);
		ItemPurse.addMoney(stack, -1 * message.money);
		
		return new PacketPurseDrawResponse(message.dimensionID, message.money, message.playerID, true);
		
	}

}
