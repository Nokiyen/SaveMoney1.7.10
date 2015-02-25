package noki.savemoney;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import noki.savemoney.block.BlockSafe;
import noki.savemoney.block.BlockSavings;
import noki.savemoney.event.EventInteract;
import noki.savemoney.event.EventJoinWorld;
import noki.savemoney.event.EventPlayerDeath;
import noki.savemoney.event.EventPlayerRespawn;
import noki.savemoney.event.EventToolTip;
import noki.savemoney.gui.GuiHandler;
import noki.savemoney.item.ItemBlockSavings;
import noki.savemoney.item.ItemInsurance;
import noki.savemoney.item.ItemPurse;
import noki.savemoney.tile.TileSafe;
import noki.savemoney.tile.TileSavings;
import noki.savemoney.village.ComponentInsurerHouse;
import noki.savemoney.village.VillageCreationHandler;


/**********
 * @class SaveMoneyData
 *
 * @description
 * @description_en
 */
public class SaveMoneyData {
	
	//******************************//
	// define member variables.
	//******************************//
	public static Item itemInsurance;
	public static final String nameInsurance ="insurance";
	public static Item itemPurse;
	public static final String namePurse = "purse";
	
	public static Block blockSavingsBank;
	public static final String nameSavingsBank = "savings_bank";
	public static Block blockSafe;
	public static final String nameSafe = "safe";
	
	public static int villagerIDInsurer = 42;
	
	public static CreativeTabs tab;
	
	public static boolean dependency_MPGuard = false;
	public static String modName_MPGuard = "MPGuard";
	
	
	//******************************//
	// define member methods.
	//******************************//
	//----------
	//Static Method.
	//----------
	public static void setModData(FMLPreInitializationEvent event) {
		
		
		// configuration file.
		Property prop;
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		cfg.load();
		
		prop = cfg.get("village", "villagerIDInsurer", 42);
		villagerIDInsurer = prop.getInt();
		
		cfg.save();

		// creative tab.
		tab = new TabSaveMoney();
		
		// mod dependency.
		if(Loader.isModLoaded(modName_MPGuard)) {
			dependency_MPGuard = true;
		}
		
	}
	
	public static void registerBlocks() {
		
		blockSavingsBank = new BlockSavings(nameSavingsBank, tab);
		GameRegistry.registerBlock(blockSavingsBank, ItemBlockSavings.class, nameSavingsBank);
		blockSafe = new BlockSafe(nameSafe, tab);
		GameRegistry.registerBlock(blockSafe, nameSafe);
		
	}
	
	public static void registerTileEntities() {
		
		GameRegistry.registerTileEntity(TileSavings.class, nameSavingsBank);
		GameRegistry.registerTileEntity(TileSafe.class, nameSafe);
		
	}
	
	public static void registerItems() {
		
		itemInsurance = new ItemInsurance(nameInsurance, tab);
		GameRegistry.registerItem(itemInsurance, nameInsurance);
		itemPurse = new ItemPurse(namePurse, tab);
		GameRegistry.registerItem(itemPurse, namePurse);
		
	}
	
	public static void registerVillagers() {
		
		VillagerRegistry.instance().registerVillagerId(villagerIDInsurer);
		VillagerRegistry.instance().registerVillageCreationHandler(new VillageCreationHandler());
		MapGenStructureIO.registerStructure(ComponentInsurerHouse.class, "InsurerHouse");
		MapGenStructureIO.func_143031_a(ComponentInsurerHouse.class, "InsurerHouseP");
		
	}
	
	public static void registerRecipes() {
		
		// register recipes.
		GameRegistry.addRecipe(new ItemStack(blockSavingsBank, 1, 0),
				"   ", "xxx", "xyx", 'x', Items.brick, 'y', Items.iron_ingot);
		GameRegistry.addRecipe(new ItemStack(blockSavingsBank, 1, 4),
				"   ", "xxx", "xyx", 'x', Items.brick, 'y', Items.gold_ingot);
		GameRegistry.addRecipe(new ItemStack(blockSavingsBank, 1, 8),
				"   ", "xxx", "xyx", 'x', Items.brick, 'y', Items.diamond);
		GameRegistry.addRecipe(new ItemStack(blockSafe, 1, 0),
				"xyx", "y y", "xyx", 'x', Blocks.obsidian, 'y', Items.iron_ingot);
		GameRegistry.addRecipe(new ItemStack(itemPurse, 1, 0),
				" y ", "xxx", "   ", 'x', Items.leather, 'y', Items.iron_ingot);
		
	}
	
	public static void registerSmelting() {
		
		GameRegistry.registerFuelHandler(new IFuelHandler(){
			@Override
			public int getBurnTime(ItemStack fuel){
				if(fuel.getItem().equals(itemInsurance)){
					return 100;
				}
				return 0;
			}
		});
		
	}
	
	public static void registerEvents() {
		
		FMLCommonHandler.instance().bus().register(new EventPlayerRespawn());
		MinecraftForge.EVENT_BUS.register(new EventPlayerDeath());
		MinecraftForge.EVENT_BUS.register(new EventToolTip());
		MinecraftForge.EVENT_BUS.register(new EventJoinWorld());
		MinecraftForge.EVENT_BUS.register(new EventInteract());
		
	}
	
	public static void registerGuis() {
		
		NetworkRegistry.INSTANCE.registerGuiHandler(SaveMoneyCore.instance, new GuiHandler());
		
	}
	
	private static class TabSaveMoney extends CreativeTabs {

		//******************************//
		// define member variables.
		//******************************//
		public static final String label = "SaveMoney";

		
		//******************************//
		// define member methods.
		//******************************//
		public TabSaveMoney() {
			
			super(label);
			
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			
			return itemPurse;
//			return Items.apple;

		}

	}
	
}
