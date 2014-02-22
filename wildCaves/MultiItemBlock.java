package wildCaves;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class MultiItemBlock extends ItemBlock {
	private final String[] subNames;
	private final Block block;

	public MultiItemBlock(Block block, String... names) {
		super(block);
		this.block = block;
		this.subNames = names;
		setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int damage) {
		if (damage > getNumOfStructures())
			damage = getNumOfStructures() - 1;
		return block.getIcon(0, damage);
	}

	@Override
	public int getMetadata(int damage) {
		if (damage > getNumOfStructures())
			damage = getNumOfStructures() - 1;
		return damage;
	}

	public int getNumOfStructures() {
		return subNames.length;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return subNames[itemstack.getItemDamage()];
	}
}
