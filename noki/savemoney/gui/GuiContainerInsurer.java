package noki.savemoney.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import noki.savemoney.ModInfo;
import noki.savemoney.SaveMoneyData;


/**********
 * @class GuiContainerInsurer.
 *
 * @description
 * @description_en
 * 
 * @see ContainerInsurer, EventInteract.
 */
public class GuiContainerInsurer extends GuiContainer {
	
	//******************************//
	// define member variables.
	//******************************//
	private static final ResourceLocation texture = new ResourceLocation(ModInfo.ID.toLowerCase(), "textures/gui/insurer.png");
	private LanguageManager languageManager ;
	
	
	//******************************//
	// define member methods.
	//******************************//
	public GuiContainerInsurer(EntityPlayer player, World world, int x, int y, int z) {
		
		super(new ContainerInsurer(player, world, x, y, z));
		this.xSize = 176;
		this.ySize = 166;
		
		this.languageManager = Minecraft.getMinecraft().getLanguageManager();
		
	}
	
	@Override
	public void initGui() {
		
		super.initGui();
		this.guiTop = 10;
		
	}
	
	/*GUIの文字等の描画処理*/
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ) {
		
		String name = I18n.format("gui.insurer.name");
		this.fontRendererObj.drawString(name, this.xSize/2-this.fontRendererObj.getStringWidth(name)/2, 13, 4210752);
		
		String price = "10MP";
		int stringWidth = this.fontRendererObj.getStringWidth(price);
		this.fontRendererObj.drawString(price, 42-stringWidth/2, 50, 4210752);
		price = "100MP";
		stringWidth = this.fontRendererObj.getStringWidth(price);
		this.fontRendererObj.drawString(price, 87-stringWidth/2, 50, 4210752);
		price = "500MP";
		stringWidth = this.fontRendererObj.getStringWidth(price);
		this.fontRendererObj.drawString(price, 133-stringWidth/2, 50, 4210752);
		
		if(SaveMoneyData.dependency_MPGuard == true) {
			if(this.languageManager.getCurrentLanguage().getLanguageCode().equals("ja_JP")) {
				this.fontRendererObj.drawString(I18n.format("gui.insurer.mpguard.line1"), 12, 62, 4210752);
				this.fontRendererObj.drawString(I18n.format("gui.insurer.mpguard.line2"), 12, 72, 4210752);
			}
			else {
				GL11.glScalef(0.8F, 0.8F, 0.8F);
				this.fontRendererObj.drawString(I18n.format("gui.insurer.mpguard.line1"), 10, 85, 4210752);
				this.fontRendererObj.drawString(I18n.format("gui.insurer.mpguard.line2"), 10, 93, 4210752);
				GL11.glScalef(1.0F, 1.0F, 1.0F);
			}
		}

	}
	
	/*GUIの背景の描画処理*/
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseZ) {
		
		this.mc.renderEngine.bindTexture(texture);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
		
	}
	
	/*GUIが開いている時にゲームの処理を止めるかどうか。*/
	@Override
	public boolean doesGuiPauseGame() {
		
		return false;
		
	}
	
}
