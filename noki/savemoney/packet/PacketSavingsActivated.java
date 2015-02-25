package noki.savemoney.packet;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;


/**********
 * @class PacketSavingsActivated
 *
 * @description
 * @description_en
 * 
 * @see BlockSavings, PacketSavingsActivatedHandler, PacketHandler.
 */
public class PacketSavingsActivated implements IMessage {

	//******************************//
	// define member variables.
	//******************************//
	public ByteBuf data;
	public int dimensionID;
	public int posX;
	public int posY;
	public int posZ;

	
	//******************************//
	// define member methods.
	//******************************//
	public PacketSavingsActivated() {
		
	}
	
	public PacketSavingsActivated(int dimensionID, int posX, int posY, int posZ) {
		
		this.dimensionID = dimensionID;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		
		this.data = buf;
		this.dimensionID = this.data.readInt();
		this.posX = this.data.readInt();
		this.posY = this.data.readInt();
		this.posZ = this.data.readInt();
		
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(this.dimensionID);
		buf.writeInt(this.posX);
		buf.writeInt(this.posY);
		buf.writeInt(this.posZ);
		
	}

}
