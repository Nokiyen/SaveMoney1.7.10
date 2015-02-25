package noki.savemoney.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;


/**********
 * @class TileSafe
 *
 * @description
 * @description_en
 * 
 * @see BlockSafe, GuiSafe.
 */
public class TileSafe extends TileEntity {
	
	//******************************//
	// define member variables.
	//******************************//
	private static final int maxMoney = 1000000;
	private int money;
	
	private static final String nbtMoney = "SaveMoney:money";
	
	
	//******************************//
	// define member methods.
	//******************************//
	public void setMoney(int money) {
		
		this.money = money;
	}
	
	public int getMoney() {
		
		return this.money;
		
	}
	
	public boolean addMoney(int add) {
		
		if(this.hasSpace(add)) {
			this.money += add;
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public boolean hasSpace(int add) {
		
		if(this.money+add <= maxMoney) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		super.readFromNBT(nbt);
		this.setMoney(nbt.getInteger(nbtMoney));
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
		super.writeToNBT(nbt);
		nbt.setInteger(nbtMoney, this.money);
		
	}
	
	@Override
	public Packet getDescriptionPacket() {
		
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, nbttagcompound);
		
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		
		this.readFromNBT(packet.func_148857_g());
		
	}

}
