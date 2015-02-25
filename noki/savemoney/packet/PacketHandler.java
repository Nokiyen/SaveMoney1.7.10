package noki.savemoney.packet;

import noki.savemoney.ModInfo;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;


/**********
 * @class PacketHandler
 *
 * @description
 * @description_en
 * 
 * @see noki.savemoney.packet.*
 */
public class PacketHandler {
	
	//******************************//
	// define member variables.
	//******************************//
	public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.ID);
	
	
	//******************************//
	// define member methods.
	//******************************//
	public static void registerPackets() {
		
		instance.registerMessage(PacketSavingsActivatedHandler.class, PacketSavingsActivated.class, 1, Side.CLIENT);
		instance.registerMessage(PacketSavingsBrokenHandler.class, PacketSavingsBroken.class, 2, Side.CLIENT);
		instance.registerMessage(PacketSafeDepositHandler.class, PacketSafeDeposit.class, 3, Side.SERVER);
		instance.registerMessage(PacketSafeDrawHandler.class, PacketSafeDraw.class, 4, Side.SERVER);
		instance.registerMessage(PacketPurseDepositHandler.class, PacketPurseDeposit.class, 5, Side.SERVER);
		instance.registerMessage(PacketPurseDrawHandler.class, PacketPurseDraw.class, 6, Side.SERVER);
		instance.registerMessage(PacketPurseDepositResponseHandler.class, PacketPurseDepositResponse.class, 7, Side.CLIENT);
		instance.registerMessage(PacketPurseDrawResponseHandler.class, PacketPurseDrawResponse.class, 8, Side.CLIENT);
		instance.registerMessage(PacketInsuranceHandler.class, PacketInsurance.class, 9, Side.CLIENT);
		
	}
	
	public static void registerPacketsClient() {
		
		instance.registerMessage(PacketSavingsActivatedHandler.class, PacketSavingsActivated.class, 1, Side.CLIENT);
		instance.registerMessage(PacketSavingsBrokenHandler.class, PacketSavingsBroken.class, 2, Side.CLIENT);
		instance.registerMessage(PacketPurseDepositResponseHandler.class, PacketPurseDepositResponse.class, 7, Side.CLIENT);
		instance.registerMessage(PacketPurseDrawResponseHandler.class, PacketPurseDrawResponse.class, 8, Side.CLIENT);
		instance.registerMessage(PacketInsuranceHandler.class, PacketInsurance.class, 9, Side.CLIENT);
		
	}
	
	public static void registerPacketsServer() {
		
		instance.registerMessage(PacketSafeDepositHandler.class, PacketSafeDeposit.class, 3, Side.SERVER);
		instance.registerMessage(PacketSafeDrawHandler.class, PacketSafeDraw.class, 4, Side.SERVER);
		instance.registerMessage(PacketPurseDepositHandler.class, PacketPurseDeposit.class, 5, Side.SERVER);
		instance.registerMessage(PacketPurseDrawHandler.class, PacketPurseDraw.class, 6, Side.SERVER);
		
	}

}
