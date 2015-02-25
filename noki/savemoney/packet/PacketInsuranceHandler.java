package noki.savemoney.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import noki.savemoney.SaveMoneyCore;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;


/**********
 * @class PacketInsuranceHandler
 *
 * @description
 * @description_en
 * 
 * @see EventPlayerRespawn, PacketInsurance.
 */
public class PacketInsuranceHandler implements IMessageHandler<PacketInsurance, IMessage> {

	//******************************//
	// define member variables.
	//******************************//
	
	
	//******************************//
	// define member methods.
	//******************************//
	@Override
	public IMessage onMessage(PacketInsurance message, MessageContext ctx) {
		
		ChatStyle style = new ChatStyle().setColor(EnumChatFormatting.GREEN);
		EntityPlayer player = SaveMoneyCore.proxy.getClientPlayer();
		if(player == null) {
			return null;
		}
		player.addChatComponentMessage(new ChatComponentTranslation("item.insurance.chat.apply").setChatStyle(style));
		
		return null;
		
	}

}
