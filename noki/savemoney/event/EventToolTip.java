package noki.savemoney.event;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import noki.savemoney.block.BlockSavings;
import noki.savemoney.item.ItemBlockSavings;
import noki.savemoney.item.ItemInsurance;
import noki.savemoney.item.ItemPurse;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;


/**********
 * @class EventTooTip
 *
 * @description
 * @description_en
 * 
 * @see ItemInsurance, ItemBlockSavings.
 */
public class EventToolTip {
	
	//******************************//
	// define member variables.
	//******************************//

	
	//******************************//
	// define member methods.
	//******************************//
	@SubscribeEvent
	public void onToolTip(ItemTooltipEvent event) {
		
		if(event.itemStack.getItem() instanceof ItemInsurance) {
			event.toolTip.add(I18n.format("item.insurance.info.contract", ItemInsurance.getContract(event.itemStack)));
			event.toolTip.add(I18n.format("item.insurance.info.expire", ItemInsurance.getExpire(event.itemStack)));
		}
		else if(event.itemStack.getItem() instanceof ItemPurse) {
			event.toolTip.add(I18n.format("item.purse.info.money", ItemPurse.getMoney(event.itemStack)));
		}
		else if(event.itemStack.getItem() instanceof ItemBlockSavings) {
			int savings = BlockSavings.getSavings(event.itemStack);
			int maxSavings = BlockSavings.getMaxSavings(BlockSavings.getType(event.itemStack));
			
			if(savings == maxSavings) {
				event.toolTip.add(I18n.format("tile.savings_bank.info.full"));
			}
			else if((float)savings/(float)maxSavings > 0.75F) {
				event.toolTip.add(I18n.format("tile.savings_bank.info.heavy"));
			}
			else if((float)savings/(float)maxSavings > 0.5F) {
				event.toolTip.add(I18n.format("tile.savings_bank.info.abitHeavy"));
			}
			else if((float)savings/(float)maxSavings > 0.25F) {
				event.toolTip.add(I18n.format("tile.savings_bank.info.abitLight"));
			}
			else if((float)savings/(float)maxSavings > 0F) {
				event.toolTip.add(I18n.format("tile.savings_bank.info.light"));
			}
			else if(savings == 0) {
				event.toolTip.add(I18n.format("tile.savings_bank.info.empty"));
			}
		}
		
	}

}
