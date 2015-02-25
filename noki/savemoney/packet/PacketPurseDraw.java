package noki.savemoney.packet;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;


/**********
 * @class PacketPurseDraw
 *
 * @description
 * @description_en
 * 
 * @see ItemPurse, GuiPurse, PacketPurseDrawHandler, PacketPurseDrawResponse, PacketPurseDrawResponseHander,
 */
public class PacketPurseDraw implements IMessage {

	//******************************//
	// define member variables.
	//******************************//
	public ByteBuf data;
	public int dimensionID;
	public int money;
	public int playerID;

	
	//******************************//
	// define member methods.
	//******************************//
	public PacketPurseDraw() {
		
	}
	
	public PacketPurseDraw(int dimensionID, int money, int playerID) {
		
		this.dimensionID = dimensionID;
		this.money = money;
		this.playerID = playerID;
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		
		this.data = buf;
		this.dimensionID = this.data.readInt();
		this.money = this.data.readInt();
		this.playerID = this.data.readInt();
		
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(this.dimensionID);
		buf.writeInt(this.money);
		buf.writeInt(this.playerID);
		
	}

}
