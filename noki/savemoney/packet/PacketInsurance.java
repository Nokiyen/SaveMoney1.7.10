package noki.savemoney.packet;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;


/**********
 * @class PacketInsurance
 *
 * @description
 * @description_en
 * 
 * @see EventPlayerRespawn, PakcetInsuranceHandler.
 */
public class PacketInsurance implements IMessage {

	//******************************//
	// define member variables.
	//******************************//
	public ByteBuf data;
	public int playerID;	// not used.

	
	//******************************//
	// define member methods.
	//******************************//
	public PacketInsurance() {
		
	}
	
	public PacketInsurance(int playerID) {
		
		this.playerID = playerID;
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		
		this.data = buf;
		this.playerID = buf.readInt();
		
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(this.playerID);
		
	}

}
