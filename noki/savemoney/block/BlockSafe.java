package noki.savemoney.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import shift.mceconomy2.api.MCEconomyAPI;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noki.savemoney.ModInfo;
import noki.savemoney.SaveMoneyCore;
import noki.savemoney.tile.TileSafe;


/**********
 * @class BlockSafe
 *
 * @description
 * @description_en
 * 
 * @see TileSafe, GuiSafe.
 */
public class BlockSafe extends BlockContainer {
	
	//******************************//
	// define member variables.
	//******************************//
	private IIcon icon;
	private IIcon iconFront;
	
	
	//******************************//
	// define member methods.
	//******************************//
	public BlockSafe(String unlocalizedName, CreativeTabs tab) {
		
		super(Material.iron);
		this.setBlockName(unlocalizedName);
		this.setHardness(5.0F);
		this.setResistance(2000.0F);
		this.setStepSound(soundTypeMetal);
		this.setCreativeTab(tab);
		this.setBlockTextureName(ModInfo.ID.toLowerCase() + ":" + unlocalizedName);

	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		
		return new TileSafe();
		
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		
		// for textures.
		int dir = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, dir, 2);
		
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int facing, float hitX, float hitY, float hitZ) {
				
		return SaveMoneyCore.proxy.openBlockGuiScreen(0, world, x, y, z, player);
		
	}
	
	// called only on the server side.
	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int metadata, EntityPlayer player) {
		
		if(!world.isRemote) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile == null || !(tile instanceof TileSafe)) {
				return;
			}
			if(((TileSafe)tile).getMoney() != 0) {
				MCEconomyAPI.addPlayerMP(player, ((TileSafe)tile).getMoney(), false);
			}
/*			PacketHandler.instance.sendToDimension(
					new PacketSavingsBroken(world.provider.dimensionId, x, y, z), world.provider.dimensionId);*/
		}
//		super.dropBlockAsItem(world, x, y, z, 0, 0);
		
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		this.icon = iconRegister.registerIcon(this.getTextureName());
		this.iconFront = iconRegister.registerIcon(this.getTextureName() + "_front");
		
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		metadata = MathHelper.clamp_int(metadata, 0, 3);
		if((metadata == 2 && side == 2) || (metadata == 3 && side == 5)
				|| (metadata == 0 && side == 3) || (metadata == 1 && side == 4)) {
			return this.iconFront;
		}
		else {
			return this.icon;
		}
		
	}

}
