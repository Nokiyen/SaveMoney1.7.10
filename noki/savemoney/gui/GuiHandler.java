package noki.savemoney.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;


/**********
 * @class GuiHandler
 *
 * @description
 * @description_en
 * 
 * @see GuiContainerInsurer, ContainerInsurer.
 * @note GuiPurse and GuiSafe are not called through this class.
 */
public class GuiHandler implements IGuiHandler {
	
	//******************************//
	// define member variables.
	//******************************//
	
	
	//******************************//
	// define member methods.
	//******************************//
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		
		if(id == 1) {
			return new ContainerInsurer(player, world, x, y, z);
		}
		return null;
		
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		
		if(id == 1) {
			return new GuiContainerInsurer(player, world, x, y, z);
		}
		return null;
	}
	
}
