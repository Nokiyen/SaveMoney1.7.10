package noki.savemoney;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import noki.savemoney.packet.PacketHandler;
import noki.savemoney.proxy.ProxyCommon;


/**********
 * @Mod SaveMoney
 *
 * @author Nokiyen
 * 
 * @description
 */
@Mod(modid = ModInfo.ID, version = ModInfo.VERSION, name = ModInfo.NAME)
public class SaveMoneyCore {
	
	//******************************//
	// define member variables.
	//******************************//
	@Instance(value = ModInfo.ID)
	public static SaveMoneyCore instance;
	@Metadata
	public static ModMetadata metadata;	//	extract from mcmod.info file, not java internal coding.
	@SidedProxy(clientSide = ModInfo.PROXY_LOCATION+"ProxyClient", serverSide = ModInfo.PROXY_LOCATION+"ProxyServer")
	public static ProxyCommon proxy;

	public static VersionInfo versionInfo;
	public static boolean debugFlag = false;

	
	//******************************//
	// define member methods.
	//******************************//
	//----------
	//Core Event Methods.
	//----------
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		// for mod's specific data.
		SaveMoneyData.setModData(event);
		versionInfo = new VersionInfo(metadata.modId.toLowerCase(), metadata.version, metadata.updateUrl);
		
		// for registering.
		SaveMoneyData.registerBlocks();
		SaveMoneyData.registerTileEntities();
		SaveMoneyData.registerItems();
		PacketHandler.registerPackets();
//		proxy.registerPackets();
		
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		
		// for registering.
		SaveMoneyData.registerRecipes();
		SaveMoneyData.registerEvents();
		SaveMoneyData.registerSmelting();
		SaveMoneyData.registerVillagers();
		SaveMoneyData.registerGuis();
		proxy.registerVillagerSkins();
						
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		// nothing to do.
			
	}
	
	@EventHandler
	public void onServerStart(FMLServerStartingEvent event) {
		
		versionInfo.notifyUpdate(Side.SERVER);
		
	}
	
	//----------
	//Static Method.
	//----------
	public static void log(String message, Object... data) {
		
		if(debugFlag) {
			FMLLog.fine("[SaveMoney:LOG] " + message, data);
		}
		
	}
	
}
