package noki.savemoney.village;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import noki.savemoney.SaveMoneyData;


/**********
 * @class ComponentInsurerHouse
 *
 * @description
 * @description_en
 * 
 * @see VillageCreationHandler.
 */
public class ComponentInsurerHouse extends StructureVillagePieces.Village {
	
	protected ComponentInsurerHouse() {
		
	}
	
	protected ComponentInsurerHouse(StructureVillagePieces.Start startPiece, int par2, Random rand,
			StructureBoundingBox structureboundingbox, int p5) {
		
		super(startPiece, par2);		
		this.coordBaseMode = p5;
		this.boundingBox = structureboundingbox;
		
	}
	
	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureboundingbox) {
		
		if (this.field_143015_k < 0)
		{
			this.field_143015_k = this.getAverageGroundLevel(world, structureboundingbox);

			if (this.field_143015_k < 0)
			{
				return true;
			}

			this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
		}

		this.fillWithBlocks(world, structureboundingbox, 0, 0, 0, 4, 0, 4, Blocks.cobblestone, Blocks.cobblestone, false);
		this.fillWithBlocks(world, structureboundingbox, 0, 4, 0, 4, 4, 4, Blocks.log, Blocks.log, false);
		this.fillWithBlocks(world, structureboundingbox, 1, 4, 1, 3, 4, 3, Blocks.planks, Blocks.planks, false);
		this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, 0, 1, 0, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, 0, 2, 0, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, 0, 3, 0, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, 4, 1, 0, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, 4, 2, 0, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, 4, 3, 0, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, 0, 1, 4, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, 0, 2, 4, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, 0, 3, 4, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, 4, 1, 4, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, 4, 2, 4, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, 4, 3, 4, structureboundingbox);
		this.fillWithBlocks(world, structureboundingbox, 0, 1, 1, 0, 3, 3, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(world, structureboundingbox, 4, 1, 1, 4, 3, 3, Blocks.planks, Blocks.planks, false);
		this.fillWithBlocks(world, structureboundingbox, 1, 1, 4, 3, 3, 4, Blocks.planks, Blocks.planks, false);
		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 0, 2, 2, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 2, 2, 4, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 4, 2, 2, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 1, 1, 0, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 1, 2, 0, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 1, 3, 0, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 2, 3, 0, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 3, 3, 0, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 3, 2, 0, structureboundingbox);
		this.placeBlockAtCurrentPosition(world, Blocks.planks, 0, 3, 1, 0, structureboundingbox);

		if (this.getBlockAtCurrentPosition(world, 2, 0, -1, structureboundingbox).getMaterial() == Material.air && this.getBlockAtCurrentPosition(world, 2, -1, -1, structureboundingbox).getMaterial() != Material.air)
		{
			this.placeBlockAtCurrentPosition(world, Blocks.stone_stairs, this.getMetadataWithOffset(Blocks.stone_stairs, 3), 2, 0, -1, structureboundingbox);
		}

		this.fillWithBlocks(world, structureboundingbox, 1, 1, 1, 3, 3, 3, Blocks.air, Blocks.air, false);

		int i;

		this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 3, 1, structureboundingbox);

		for (i = 0; i < 5; ++i)
		{
			for (int j = 0; j < 5; ++j)
			{
				this.clearCurrentPositionBlocksUpwards(world, j, 6, i, structureboundingbox);
				this.func_151554_b(world, Blocks.cobblestone, 0, j, -1, i, structureboundingbox);
			}
		}

		this.spawnVillagers(world, structureboundingbox, 1, 1, 2, 1);
		return true;
		
	}
	
	@SuppressWarnings("rawtypes")
	public static Object buildComponent(StructureVillagePieces.Start startPiece, List pieces, Random random,
		int p1, int p2, int p3, int p4, int p5) {
		
		StructureBoundingBox structureboundingbox =
				StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, 5, 6, 5, p4);
		if(canVillageGoDeeper(structureboundingbox) &&
				StructureComponent.findIntersecting(pieces, structureboundingbox) == null) {
			return new ComponentInsurerHouse(startPiece, p5, random, structureboundingbox, p4);
		}
		else {
			return null;
		}
		
	}
	
	@Override
	protected int getVillagerType(int par1) {
		
		return SaveMoneyData.villagerIDInsurer;
		
	}
	
}
