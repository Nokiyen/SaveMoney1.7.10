package noki.savemoney.packet;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;


/**********
 * @class PacketPurseDrawResponse
 *
 * @description
 * @description_en
 * 
 * @see ItemPurse, GuiPurse, PacketPurseDrawResponseHander,
 */
public class PacketPurseDrawResponse implements IMessage {

	//******************************//
	// define member variables.
	//******************************//
	public ByteBuf data;
	public int dimensionID;
	public int money;
	public int playerID;
	public boolean success;

	
	//******************************//
	// define member methods.
	//******************************//
	public PacketPurseDrawResponse() {
		
	}
	
	public PacketPurseDrawResponse(int dimensionID, int money, int playerID, boolean success) {
		
		this.dimensionID = dimensionID;
		this.money = money;
		this.playerID = playerID;
		this.success = success;
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		
		this.data = buf;
		this.dimensionID = this.data.readInt();
		this.money = this.data.readInt();
		this.playerID = this.data.readInt();
		this.success = this.data.readBoolean();
		
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(this.dimensionID);
		buf.writeInt(this.money);
		buf.writeInt(this.playerID);
		buf.writeBoolean(this.success);
		
	}

}
