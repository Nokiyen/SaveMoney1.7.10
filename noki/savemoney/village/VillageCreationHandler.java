package noki.savemoney.village;

import java.util.List;
import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageCreationHandler;


/**********
 * @class VillageCreationHandler
 *
 * @description
 * @description_en
 * 
 * @see ComponentInsurerHouse.
 */
public class VillageCreationHandler implements IVillageCreationHandler {
	
	//******************************//
	// define member variables.
	//******************************//
	
	
	//******************************//
	// define member methods.
	//******************************//
	@Override
	public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int i) {
		
		return new StructureVillagePieces.PieceWeight(this.getComponentClass(),
				10, MathHelper.getRandomIntegerInRange(random, i, i+1));
		
	}
	
	@Override
	public Class<?> getComponentClass() {
		
		return ComponentInsurerHouse.class;
			
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object buildComponent(StructureVillagePieces.PieceWeight villagePiece,
									 StructureVillagePieces.Start startPiece, List pieces, Random random,
				int p1, int p2, int p3, int p4, int p5) {
		
		return ComponentInsurerHouse.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
			
	}
	 
}
