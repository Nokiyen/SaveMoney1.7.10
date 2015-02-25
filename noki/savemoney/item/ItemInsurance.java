package noki.savemoney.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noki.savemoney.ModInfo;
import noki.savemoney.SaveMoneyData;


/**********
 * @class ItemInsurance
 *
 * @description
 * @description_en
 * 
 * @see EventPlayerDeath, EventPlayerRespawn, EventToolTip.
 */
public class ItemInsurance extends Item {
	
	//******************************//
	// define member variables.
	//******************************//
	private static final String nbtContract = "SaveMoney:contract";
	private static final String nbtExpire = "SaveMoney:expire";
	
	
	//******************************//
	// define member methods.
	//******************************//
	public ItemInsurance(String unlocalizedName, CreativeTabs tab) {
		
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(tab);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setTextureName(ModInfo.ID.toLowerCase() + ":" + unlocalizedName);
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })	//about List.
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		
		for(int i=1; i<=4; i++) {
			list.add(getNewInsurance(0, (int)Math.pow(10, i)));
		}
		
	}
	
	//----------
	//Static Method.
	//----------
	public static ItemStack getNewInsurance(int contract, int expire) {
		
		ItemStack stack = new ItemStack((SaveMoneyData.itemInsurance));
		
		NBTTagCompound nbt;
		if(stack.hasTagCompound() == false) {
			nbt = new NBTTagCompound();
		}
		else {
			nbt = stack.getTagCompound();
		}
		
		nbt.setInteger(nbtContract, contract);
		nbt.setInteger(nbtExpire, expire);
		stack.setTagCompound(nbt);
		
		return stack;

	}
	
	public static ItemStack getNewInsurance(int addedExpire, World world) {
		
		int day = (int)Math.ceil(world.getTotalWorldTime() / 24000);

		return getNewInsurance(day, day + addedExpire);
		
	}
	
	public static int getContract(ItemStack stack) {
		
		if(stack.hasTagCompound() == false) {
			return 0;
		}
		else {
			return stack.getTagCompound().getInteger(nbtContract);
		}
		
	}
	
	public static int getExpire(ItemStack stack) {
		
		if(stack.hasTagCompound() == false) {
			return 0;
		}
		else {
			return stack.getTagCompound().getInteger(nbtExpire);
		}
		
	}
	
	public static boolean isValid(ItemStack stack, World world) {
		
		if(!(stack.getItem() instanceof ItemInsurance)) {
			return false;
		}
		if(stack.hasTagCompound() == false) {
			return false;
		}
		
		int day = (int)Math.ceil(world.getTotalWorldTime() / 24000);
		int expire = getExpire(stack);
		if(day <= expire) {
			return true;
		}
		else {
			return false;
		}

	}

}
