package noki.savemoney.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import noki.savemoney.block.BlockSavings;


/**********
 * @class TileSavings
 *
 * @description
 * @description_en
 * 
 * @see BlockSavings.
 */
public class TileSavings extends TileEntity {
	
	//******************************//
	// define member variables.
	//******************************//
	private int type;
	private int maxSavings;
	private int savings;
	
	private static final String nbtType = "SaveMoney:type";
	private static final String nbtSavings = "SaveMoney:savings";
	
	
	//******************************//
	// define member methods.
	//******************************//
	public void setType(int type) {
		
		this.type = MathHelper.clamp_int(type, 0, 2);
		this.maxSavings = BlockSavings.maxSavings[this.type];
		
	}
	
	public int getType() {
		
		return this.type;
		
	}
	
	public void setSavings(int savings) {
		
		this.savings = savings;
		
	}
	
	public int getSavings() {
		
		return this.savings;
		
	}
	
	public boolean addSavings(int add) {
		
		if(this.hasSpace(add)) {
			this.savings += add;
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public boolean hasSpace(int add) {
		
		if(this.savings+add <= this.maxSavings) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		super.readFromNBT(nbt);
		this.setType(nbt.getInteger(nbtType));
		this.setSavings(nbt.getInteger(nbtSavings));
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
		super.writeToNBT(nbt);
		nbt.setInteger(nbtType, this.type);
		nbt.setInteger(nbtSavings, this.savings);
		
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
