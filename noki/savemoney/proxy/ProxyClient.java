package noki.savemoney.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import noki.savemoney.ModInfo;
import noki.savemoney.SaveMoneyCore;
import noki.savemoney.SaveMoneyData;
import noki.savemoney.gui.GuiPurse;
import noki.savemoney.gui.GuiSafe;
import noki.savemoney.packet.PacketHandler;
import noki.savemoney.tile.TileSafe;
import cpw.mods.fml.common.registry.VillagerRegistry;


/**********
 * @class ProxyClient
 *
 * @description クライアント用proxyクラス。
 * @description_en Proxy class for Client.
 */
public class ProxyClient implements ProxyCommon {
	
	//******************************//
	// define member variables.
	//******************************//


	//******************************//
	// define member methods.
	//******************************//
	@Override
	public void registerVillagerSkins() {
		
		VillagerRegistry.instance().registerVillagerSkin(SaveMoneyData.villagerIDInsurer,
				new ResourceLocation(ModInfo.ID.toLowerCase(), "textures/entity/insurer.png"));

	}
	
	@Override
	public void registerPackets() {
		
		PacketHandler.registerPacketsClient();
		
	}
	
	@Override
	public boolean openBlockGuiScreen(int guiId, World world, int x, int y, int z, EntityPlayer player) {
		
		switch(guiId) {
			case 0:
				TileEntity tile = world.getTileEntity(x, y, z);
				if(tile == null || !(tile instanceof TileSafe)) {
					return false;
				}
				Minecraft.getMinecraft().displayGuiScreen(new GuiSafe((TileSafe)tile, player));
				return true;
			default:
				return true;
		}
		
	}
	
	@Override
	public void openItemGuiScreen(int guiId, ItemStack stack, World world, EntityPlayer player) {
		
		Minecraft.getMinecraft().displayGuiScreen(new GuiPurse(stack, player));
		
	}
	
	@Override
	public World getClientWorld() {
		
		return Minecraft.getMinecraft().theWorld;
		
	}
	
	@Override
	public EntityPlayer getClientPlayer() {
		
		return Minecraft.getMinecraft().thePlayer;
		
	}
	
	@Override
	public void updateClientGui(int value) {
		
//		ItemPurse.addMoney(stack, -1 * message.money);
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		if(screen != null && screen instanceof GuiPurse) {
			SaveMoneyCore.log("add money / %s.", value);
			((GuiPurse)screen).addCurrentMoney(value);
		}
		
	}
	
}
