package noki.savemoney.packet;

import shift.mceconomy2.api.MCEconomyAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import noki.savemoney.tile.TileSafe;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;


/**********
 * @class PacketSafeDepositHandler
 *
 * @description
 * @description_en
 * 
 * @see BlockSafe, TileSafe, GuiSafe, PacketSafeDeposit.
 */
public class PacketSafeDepositHandler implements IMessageHandler<PacketSafeDeposit, IMessage> {

	//******************************//
	// define member variables.
	//******************************//
	
	
	//******************************//
	// define member methods.
	//******************************//
	@Override
	public IMessage onMessage(PacketSafeDeposit message, MessageContext ctx) {
		
		if(message.money == 0) {
			return null;
		}
		
		World world = MinecraftServer.getServer().worldServerForDimension(message.dimensionID);
		if(world == null) {
			return null;
		}
		
		Entity entity = world.getEntityByID(message.playerID);
		if(entity == null || !(entity instanceof EntityPlayer)) {
			return null;
		}
		TileEntity tile = world.getTileEntity(message.posX, message.posY, message.posZ);
		if(tile == null || !(tile instanceof TileSafe)) {
			return null;
		}
		
		if(message.money > MCEconomyAPI.getPlayerMP((EntityPlayer)entity) || !((TileSafe)tile).hasSpace(message.money)) {
			return null;
		}
		
		MCEconomyAPI.reducePlayerMP((EntityPlayer)entity, message.money, false);
		((TileSafe)tile).addMoney(message.money);
		world.markBlockForUpdate(message.posX, message.posY, message.posZ);
		tile.markDirty();
		
		return null;
		
	}

}
