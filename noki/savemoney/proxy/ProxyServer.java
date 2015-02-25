package noki.savemoney.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import noki.savemoney.packet.PacketHandler;


/**********
 * @class ProxyServer
 *
 * @description サーバ用proxyクラス。
 * @description_en Proxy class for Server.
 */
public class ProxyServer implements ProxyCommon {
	
	//******************************//
	// define member variables.
	//******************************//

	
	//******************************//
	// define member methods.
	//******************************//
	@Override
	public void registerVillagerSkins() {
		
	}
	
	@Override
	public void registerPackets() {
		
		PacketHandler.registerPacketsServer();
		
	}
	
	@Override
	public boolean openBlockGuiScreen(int guiId, World world, int x, int y, int z, EntityPlayer player) {
		
		return true;
		
	}
	
	@Override
	public void openItemGuiScreen(int guiId, ItemStack stack, World world, EntityPlayer player) {
		
	}
	
	@Override
	public World getClientWorld() {
		
		return null;
		
	}
	
	@Override
	public EntityPlayer getClientPlayer() {
		
		return null;
		
	}
	
	@Override
	public void updateClientGui(int value) {
		
	}

}
