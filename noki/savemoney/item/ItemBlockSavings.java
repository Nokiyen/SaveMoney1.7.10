package noki.savemoney.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import noki.savemoney.block.BlockSavings;


/**********
 * @class ItemBlockSavings
 *
 * @description
 * @description_en
 * 
 * @see BlockSavings.
 */
public class ItemBlockSavings extends ItemBlock {
	
	//******************************//
	// define member variables.
	//******************************//

	
	//******************************//
	// define member methods.
	//******************************//
	public ItemBlockSavings(Block block) {
		
		super(block);
		
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		
		return this.getUnlocalizedName() + "." + BlockSavings.getType(stack);
		
	}
	
	@Override
	public int getMetadata(int metadata) {
		
		return MathHelper.clamp_int(metadata, 0, 2);
		
	}
	
}
