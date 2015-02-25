package noki.savemoney.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import noki.savemoney.ModInfo;
import noki.savemoney.SaveMoneyCore;
import noki.savemoney.packet.PacketHandler;
import noki.savemoney.packet.PacketSafeDeposit;
import noki.savemoney.packet.PacketSafeDraw;
import noki.savemoney.tile.TileSafe;


/**********
 * @class GuiSafe
 *
 * @description
 * @description_en
 * 
 * @see BlockSafe, TileSafe,
 * 			PacketSafeDeposit, PacketSafeDepositHandler, PacketSafeDraw, PacketSafeDrawHandler.
 */
public class GuiSafe extends GuiScreen {
	
	//******************************//
	// define member variables.
	//******************************//
	private static final String domain = ModInfo.ID.toLowerCase();
	private static final ResourceLocation texture = new ResourceLocation(domain, "textures/gui/money.png");
	
	private static int pageWidth = 176;
	private static int pageHeight = 84;
	
	private GuiButton[] incrButton;
	private GuiButton[] decrButton;
	private int[] eachNum;
	private int num;
	private static final int maxNum = 99999;
	private GuiButton depositButton;
	private GuiButton drawButton;
	
	private TileSafe safe;
	private EntityPlayer player;
	
	
	//******************************//
	// define member methods.
	//******************************//
	public GuiSafe(TileSafe safe, EntityPlayer player) {
		
		this.safe = safe;
		this.player = player;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		
		this.buttonList.clear();
		this.incrButton = new GuiButton[5];
		this.decrButton = new GuiButton[5];
		for(int i=0; i<5; i++) {
			this.incrButton[i] = new ControlButton(i, (this.width-pageWidth)/2+108-i*13, 50+35, false);
			this.decrButton[i] = new ControlButton(i+5, (this.width-pageWidth)/2+108-i*13, 50+35+30, true);
			this.buttonList.add(this.incrButton[i]);
			this.buttonList.add(this.decrButton[i]);
		}
		
		this.eachNum = new int[5];
		this.num = 0;
		this.setEachNum();
		
		this.depositButton = new ResultButton(10, (this.width-pageWidth)/2+8, 50+59,
				I18n.format("gui.safe.deposit", new Object[0]));
		this.drawButton = new ResultButton(11, (this.width-pageWidth)/2+123, 50+59,
				I18n.format("gui.safe.draw", new Object[0]));
		this.buttonList.add(this.depositButton);
		this.buttonList.add(this.drawButton);
		
		this.updateButtons();
		
	}
	
	private void addNum(int add) {
		
		SaveMoneyCore.log("add is %s.", add);
		if(0 <= this.num+add && this.num+add <= maxNum) {
			this.num += add;
			SaveMoneyCore.log("num is %s.", this.num);
			this.setEachNum();
		}
		
	}
	
	private void setEachNum() {
		
		int num = this.num;
		int d = 1;
		int r = 0;
		
		for(int i=4; i>=0; i--) {
			
			d = (int)Math.pow(10D, (double)i);
			r = (int)(num % d);
			this.eachNum[i] = (num-r)/d;
			num = r;
			SaveMoneyCore.log("each%s is %s.", i, this.eachNum[i]);
		}
		
	}
	
	public void updateButtons() {
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		
		if(0 <= button.id && button.id <= 4) {
			this.addNum((int)Math.pow(10D, (double)button.id));
		}
		else if(5 <= button.id && button.id <= 9) {
			this.addNum(-1 * (int)Math.pow(10D, (double)button.id-5D));
		}
		else if(button.id == 10) {
			PacketHandler.instance.sendToServer(
					new PacketSafeDeposit(this.player.worldObj.provider.dimensionId,
							this.safe.xCoord, this.safe.yCoord, this.safe.zCoord, this.num, this.player.getEntityId()));
		}
		else if(button.id == 11) {
			PacketHandler.instance.sendToServer(
					new PacketSafeDraw(this.player.worldObj.provider.dimensionId,
							this.safe.xCoord, this.safe.yCoord, this.safe.zCoord, this.num, this.player.getEntityId()));			
		}
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		this.mc.getTextureManager().bindTexture(texture);
		this.drawTexturedModalRect((this.width-pageWidth)/2, 50, 0, 0, pageWidth, pageHeight);
				
		for(int i=0; i<5; i++) {
			this.drawTexturedModalRect((this.width-pageWidth)/2+108-i*13, 50+35+11, this.eachNum[i]*13, 86, 12, 18);
//			this.drawTexturedModalRect((this.width-pageWidth)/2+108-i*13, 50+35+11, 0, 0, 12, 18);
		}
		
		this.fontRendererObj.drawString(I18n.format("gui.safe.name", new Object[0]),
				(this.width-pageWidth)/2+48, 50+6, 4210752);
		this.fontRendererObj.drawString(I18n.format("gui.safe.money", new Object[0]),
				(this.width-pageWidth)/2+7, 50+19, 4210752);
		this.fontRendererObj.drawString(String.valueOf(this.safe.getMoney()),
				(this.width-pageWidth)/2+52, 50+19, 4210752);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		
		return false;
		
	}
	
	private class ControlButton extends GuiButton {
		
		private final boolean isDecr;
		public ControlButton(int buttonID, int x, int y, boolean isDecr) {
			
			super(buttonID, x, y, 12, 10, "control");
			this.isDecr = isDecr;
			
		}
		
		public void drawButton(Minecraft mc, int mouseX, int mouseY) {
			
			if(this.visible) {
/*				boolean mouseOver = false;
				if(mouseX >= this.xPosition && mouseY >= this.yPosition
						&& mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height) {
					mouseOver = true;
				}*/
				
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(GuiSafe.texture);
				int x = 0;
				int y = 105;
				
				if(isDecr) {
					y = 116;
				}
				
				this.drawTexturedModalRect(this.xPosition, this.yPosition, x, y, this.width, this.height);
			}
			
		}
		
	}
	
	private class ResultButton extends GuiButton {
		
		public ResultButton(int buttonID, int x, int y, String name) {
			
			super(buttonID, x, y, 45, 16, name);
			
		}
		
		public void drawButton(Minecraft mc, int mouseX, int mouseY) {
			
			if(this.visible) {
/*				boolean mouseOver = false;
				if(mouseX >= this.xPosition && mouseY >= this.yPosition
						&& mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height) {
					mouseOver = true;
				}*/
				
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(GuiSafe.texture);
	            GL11.glEnable(GL11.GL_BLEND);
	            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	            
				int x = 13;
				int y = 105;
				
				this.drawTexturedModalRect(this.xPosition, this.yPosition, x, y, this.width, this.height);
	            this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, this.displayString,
	            		this.xPosition + this.width / 2, this.yPosition + (this.height-8) / 2, 14737632);
			}
			
		}
		
	}
	
}
