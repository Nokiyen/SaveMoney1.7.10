package noki.savemoney.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


/**********
 * @class ProxyCommon
 *
 * @description 共通proxyクラス。
 * @description_en Interface of proxy classes.
 */
public interface ProxyCommon {
	
	//******************************//
	// define member variables.
	//******************************//

	
	//******************************//
	// define member methods.
	//******************************//
/*	abstract void registerTileEntities();
	abstract void registerEntities();*/
	abstract void registerVillagerSkins();
	abstract void registerPackets();
	abstract boolean openBlockGuiScreen(int guiId, World world, int x, int y, int z, EntityPlayer player);
	abstract void openItemGuiScreen(int guiId, ItemStack stack, World world, EntityPlayer player);
	abstract World getClientWorld();
	abstract EntityPlayer getClientPlayer();
	abstract void updateClientGui(int value);
	
}
