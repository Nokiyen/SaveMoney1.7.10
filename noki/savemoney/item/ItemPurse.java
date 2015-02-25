package noki.savemoney.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import noki.savemoney.ModInfo;
import noki.savemoney.SaveMoneyCore;
import noki.savemoney.SaveMoneyData;


/**********
 * @class GuiPurse.
 *
 * @description
 * @description_en
 * 
 * @see GuiPurse
 */
public class ItemPurse extends Item {
	
	//******************************//
	// define member variables.
	//******************************//
	private static final int maxMoney = 10000;
	private static final String nbtMoney = "SaveMoney:money";
	
	
	//******************************//
	// define member methods.
	//******************************//
	public ItemPurse(String unlocalizedName, CreativeTabs tab) {
		
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(tab);
		this.setTextureName(ModInfo.ID.toLowerCase() + ":" + unlocalizedName);
		
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		SaveMoneyCore.proxy.openItemGuiScreen(0, stack, world, player);
		
		return stack;
		
	}
	
	//----------
	//Static Method.
	//----------
	public static ItemStack getPurseStack(int money) {
		
		ItemStack stack = new ItemStack(SaveMoneyData.itemPurse);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger(nbtMoney, money);
		stack.setTagCompound(nbt);
		return stack;
		
	}
	
	public static int getMoney(ItemStack stack) {
		
		int money = 0;
		if(stack.getItem() == SaveMoneyData.itemPurse && stack.hasTagCompound()) {
			money = stack.getTagCompound().getInteger(nbtMoney);
		}
		return money;
		
	}
	
	public static boolean hasSpace(ItemStack stack, int add) {
		
		if(stack.getItem() != SaveMoneyData.itemPurse) {
			return false;
		}
		
		int money = getMoney(stack);
		
		if(money+add > maxMoney) {
			return false;
		}
		
		return true;
		
	}
	
	public static boolean addMoney(ItemStack stack, int add) {
		
		if(hasSpace(stack, add)) {
			NBTTagCompound nbt;
			if(stack.hasTagCompound()) {
				nbt = stack.getTagCompound();
			}
			else {
				nbt = new NBTTagCompound();
			}
			nbt.setInteger(nbtMoney, nbt.getInteger(nbtMoney)+add);
			stack.setTagCompound(nbt);
			return true;
		}
		return false;
		
	}
	
}
