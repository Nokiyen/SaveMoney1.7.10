package noki.savemoney.packet;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;


/**********
 * @class PacketSafeDeposit
 *
 * @description
 * @description_en
 * 
 * @see BlockSafe, TileSafe, GuiSafe, PacketSafeDepositHandler.
 */
public class PacketSafeDeposit implements IMessage {

	//******************************//
	// define member variables.
	//******************************//
	public ByteBuf data;
	public int dimensionID;
	public int posX;
	public int posY;
	public int posZ;
	public int money;
	public int playerID;

	
	//******************************//
	// define member methods.
	//******************************//
	public PacketSafeDeposit() {
		
	}
	
	public PacketSafeDeposit(int dimensionID, int posX, int posY, int posZ, int money, int playerID) {
		
		this.dimensionID = dimensionID;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.money = money;
		this.playerID = playerID;
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		
		this.data = buf;
		this.dimensionID = this.data.readInt();
		this.posX = this.data.readInt();
		this.posY = this.data.readInt();
		this.posZ = this.data.readInt();
		this.money = this.data.readInt();
		this.playerID = this.data.readInt();
		
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(this.dimensionID);
		buf.writeInt(this.posX);
		buf.writeInt(this.posY);
		buf.writeInt(this.posZ);
		buf.writeInt(this.money);
		buf.writeInt(this.playerID);
		
	}

}
