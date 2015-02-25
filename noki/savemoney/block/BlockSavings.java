package noki.savemoney.block;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import shift.mceconomy2.api.MCEconomyAPI;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noki.savemoney.ModInfo;
import noki.savemoney.SaveMoneyData;
import noki.savemoney.packet.PacketHandler;
import noki.savemoney.packet.PacketSavingsActivated;
import noki.savemoney.packet.PacketSavingsBroken;
import noki.savemoney.tile.TileSavings;


/**********
 * @class BlockSavings
 *
 * @description
 * @description_en
 * 
 * @see ItemBlockSavings, EventToolTip, TileSavings,
 * 			PacketSavingsActivated, PacketSavingsActivatedHandler, PacketSavingsBroken, PacketSavingsBrokenHandler.
 */
public class BlockSavings extends BlockContainer {
	
	//******************************//
	// define member variables.
	//******************************//
	public static final int savingAmount = 10;
	private IIcon iconIron;
	private IIcon iconIronTop1;
	private IIcon iconIronTop2;
	private IIcon iconIronFront;
	private IIcon iconIronBack;
	private IIcon iconGold;
	private IIcon iconGoldTop1;
	private IIcon iconGoldTop2;
	private IIcon iconGoldFront;
	private IIcon iconGoldBack;
	private IIcon iconDia;
	private IIcon iconDiaTop1;
	private IIcon iconDiaTop2;
	private IIcon iconDiaFront;
	private IIcon iconDiaBack;
	
	private static final String nbtType = "SaveMoney:type";
	private static final String nbtSavings = "SaveMoney:savings";
	public static final int[] maxSavings = {1000, 5000, 10000};
	
	
	//******************************//
	// define member methods.
	//******************************//
	public BlockSavings(String unlocalizedName, CreativeTabs tab) {
		
		super(Material.glass);
		this.setBlockName(unlocalizedName);
		this.setHardness(0.3F);
		this.setStepSound(soundTypeGlass);
		this.setCreativeTab(tab);
		this.setBlockTextureName(ModInfo.ID.toLowerCase() + ":" + unlocalizedName);

	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		
		return new TileSavings();
		
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		
		// for textures.
		int metadata = stack.getItemDamage();
		int newMetadata = 0;
		if(metadata >= 8) {
			newMetadata = 8;
		}
		else if(metadata >= 4) {
			newMetadata = 4;
		}
		else {
			newMetadata = 0;
		}
		int dir = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, dir+newMetadata, 2);
        
		// for tile.
		if(world.isRemote) {
			return;
		}
		
		if(stack.getItem() != Item.getItemFromBlock(SaveMoneyData.blockSavingsBank) || stack.hasTagCompound() == false) {
			return;
		}
		
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null || !(tile instanceof TileSavings)) {
			return;
		}
		
		TileSavings savings = (TileSavings)tile;
		savings.setType(getType(stack));
		savings.setSavings(getSavings(stack));
		world.markBlockForUpdate(x, y, z);
		
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int facing, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return false;
		}
		
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null || !(tile instanceof TileSavings)) {
			return false;
		}
		TileSavings savings = (TileSavings)tile;
		
		if(MCEconomyAPI.getPlayerMP(player) < savingAmount || savings.hasSpace(savingAmount) == false) {
			return false;
		}
		
		MCEconomyAPI.reducePlayerMP(player, savingAmount, false);
		savings.addSavings(savingAmount);
		world.markBlockForUpdate(x, y, z);
		tile.markDirty();
		PacketHandler.instance.sendToDimension(
				new PacketSavingsActivated(world.provider.dimensionId, x, y, z), world.provider.dimensionId);
		
		return true;
		
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileSavings) {
			TileSavings savings = (TileSavings)tile;
			stacks.add(getSavingsStack(savings.getType(), savings.getSavings()));
		}
		
		return stacks;
		
	}
	
	// called only on the server side.
	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int metadata, EntityPlayer player) {
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPickaxe) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile == null || !(tile instanceof TileSavings)) {
				return;
			}
			if(((TileSavings)tile).getSavings() != 0) {
				MCEconomyAPI.addPlayerMP(player, ((TileSavings)tile).getSavings(), false);
			}
			PacketHandler.instance.sendToDimension(
					new PacketSavingsBroken(world.provider.dimensionId, x, y, z), world.provider.dimensionId);
		}
		else {
			super.dropBlockAsItem(world, x, y, z, metadata, 0);;
		}
		
	}
	
	public float getBlockHardness(World world, int x, int y, int z) {
		
		float add = 0F;
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileSavings) {
			add = 1.7F * ( (float)((TileSavings)tile).getSavings() / (float)maxSavings[((TileSavings)tile).getType()] );
		}
		
		return this.blockHardness + add;
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })	//about List.
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		for(int i = 0; i < 3; i++) {
			list.add(getSavingsStack(i, 0));
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		this.iconIron = iconRegister.registerIcon(this.getTextureName() + "_iron");
		this.iconIronTop1 = iconRegister.registerIcon(this.getTextureName() + "_iron_top_1");
		this.iconIronTop2 = iconRegister.registerIcon(this.getTextureName() + "_iron_top_2");
		this.iconIronFront = iconRegister.registerIcon(this.getTextureName() + "_iron_front");
		this.iconIronBack = iconRegister.registerIcon(this.getTextureName() + "_iron_back");
		this.iconGold = iconRegister.registerIcon(this.getTextureName() + "_gold");
		this.iconGoldTop1 = iconRegister.registerIcon(this.getTextureName() + "_gold_top_1");
		this.iconGoldTop2 = iconRegister.registerIcon(this.getTextureName() + "_gold_top_2");
		this.iconGoldFront = iconRegister.registerIcon(this.getTextureName() + "_gold_front");
		this.iconGoldBack = iconRegister.registerIcon(this.getTextureName() + "_gold_back");
		this.iconDia = iconRegister.registerIcon(this.getTextureName() + "_dia");
		this.iconDiaTop1 = iconRegister.registerIcon(this.getTextureName() + "_dia_top_1");
		this.iconDiaTop2 = iconRegister.registerIcon(this.getTextureName() + "_dia_top_2");
		this.iconDiaFront = iconRegister.registerIcon(this.getTextureName() + "_dia_front");
		this.iconDiaBack = iconRegister.registerIcon(this.getTextureName() + "_dia_back");
		
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		metadata = MathHelper.clamp_int(metadata, 0, 11);
		int metaSide = metadata & 3;
		if(side == 1) {
			switch(metadata) {
				case 0:
				case 2:
					return this.iconIronTop1;
				case 1:
				case 3:
					return this.iconIronTop2;
				case 4:
				case 6:
					return this.iconGoldTop1;
				case 5:
				case 7:
					return this.iconGoldTop2;
				case 8:
				case 10:
					return this.iconDiaTop1;
				case 9:
				case 11:
					return this.iconDiaTop2;
				default:
					return this.iconIron;
			}
		}
		else if((metaSide == 2 && side == 2) || (metaSide == 3 && side == 5)
				|| (metaSide == 0 && side == 3) || (metaSide == 1 && side == 4)) {
			if(metadata >= 8) {
				return this.iconDiaFront;
			}
			else if(metadata >= 4) {
				return this.iconGoldFront;
			}
			else {
				return this.iconIronFront;
			}		
		}
		else if((metaSide == 2 && side == 3) || (metaSide == 3 && side == 4)
				|| (metaSide == 0 && side == 2) || (metaSide == 1 && side == 5)) {
/*		else if((metaSide == 2 && side == 3) || (metaSide == 3 && side == 2)
				|| (metaSide == 0 && side == 4) || (metaSide == 1 && side == 5)) {*/
			if(metadata >= 8) {
				return this.iconDiaBack;
			}
			else if(metadata >= 4) {
				return this.iconGoldBack;
			}
			else {
				return this.iconIronBack;
			}
		}
		else {
			if(metadata >= 8) {
				return this.iconDia;
			}
			else if(metadata >= 4) {
				return this.iconGold;
			}
			else {
				return this.iconIron;
			}
		}
		
	}
	
	//----------
	//Static Method.
	//----------
	public static ItemStack getSavingsStack(int type, int savings) {
		
		type = MathHelper.clamp_int(type, 0, 2);
		ItemStack stack = new ItemStack(SaveMoneyData.blockSavingsBank, 1, type*4);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger(nbtType, type);
		nbt.setInteger(nbtSavings, savings);
		stack.setTagCompound(nbt);
		return stack;
		
	}
	
	public static int getType(ItemStack stack) {
		
		if(stack.hasTagCompound() == false) {
			return 0;
		}
		else {
			return stack.getTagCompound().getInteger(nbtType);
		}
		
	}
	
	public static int getSavings(ItemStack stack) {
		
		if(stack.hasTagCompound() == false) {
			return 0;
		}
		else {
			return stack.getTagCompound().getInteger(nbtSavings);
		}
		
	}
	
	public static int getMaxSavings(int type) {
		
		return maxSavings[type];
		
	}
	
}
