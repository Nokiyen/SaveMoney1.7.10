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
 * @class PacketPurseDepositHandler
 *
 * @description
 * @description_en
 * 
 * @see ItemPurse, GuiPurse, PacketPurseDeposit, PacketPurseDepositResponse, PacketPursedepositResponseHander,
 */
public class PacketPurseDepositHandler implements IMessageHandler<PacketPurseDeposit, PacketPurseDepositResponse> {

	//******************************//
	// define member variables.
	//******************************//
	
	
	//******************************//
	// define member methods.
	//******************************//
	@Override
	public PacketPurseDepositResponse onMessage(PacketPurseDeposit message, MessageContext ctx) {
		
		if(message.money == 0) {
			return new PacketPurseDepositResponse(message.dimensionID, message.money, message.playerID, false);			
		}
		
		World world = MinecraftServer.getServer().worldServerForDimension(message.dimensionID);
		if(world == null) {
			return new PacketPurseDepositResponse(message.dimensionID, message.money, message.playerID, false);
		}
		
		Entity entity = world.getEntityByID(message.playerID);
		if(entity == null || !(entity instanceof EntityPlayer)) {
			return new PacketPurseDepositResponse(message.dimensionID, message.money, message.playerID, false);
		}
		EntityPlayer player = (EntityPlayer)entity;
		
		ItemStack stack = player.getHeldItem();
		if(stack == null || stack.getItem() != SaveMoneyData.itemPurse) {
			return new PacketPurseDepositResponse(message.dimensionID, message.money, message.playerID, false);
		}
		
		if(message.money > MCEconomyAPI.getPlayerMP((EntityPlayer)entity) || !ItemPurse.hasSpace(stack, message.money)) {
			return new PacketPurseDepositResponse(message.dimensionID, message.money, message.playerID, false);
		}
				
		MCEconomyAPI.reducePlayerMP((EntityPlayer)entity, message.money, false);
		ItemPurse.addMoney(stack, message.money);
		
		return new PacketPurseDepositResponse(message.dimensionID, message.money, message.playerID, true);
		
	}

}
