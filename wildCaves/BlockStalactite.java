package wildCaves;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockStalactite extends Block {
	private final int numOfStructures;
	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;

	public BlockStalactite(int num) {
		super(Material.field_151576_e);
		this.numOfStructures = num;
		this.func_149711_c(0.8F);
		this.func_149647_a(WildCaves.tabWildCaves);
	}

	@Override
	public boolean func_149718_j(World world, int x, int y, int z) {
		boolean result = false;
		int metadata = world.getBlockMetadata(x, y, z);
		if ((metadata != 0 && metadata < 4) || metadata == 7 || metadata == 11)
			result = connected(world, x, y, z, true);
		else if (metadata == 6 || (metadata > 7 && metadata < 11) || metadata == 12)
			result = connected(world, x, y, z, false);
		else if (metadata == 0 || metadata == 4 || metadata == 5)
			result = connected(world, x, y, z, true) || connected(world, x, y, z, false);
		return result;
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return true;
	}

	//aux funtion for canblockStay
	public boolean connected(World world, int x, int y, int z, boolean searchUp) {
		int increment;
		int i;
		if (searchUp)
			increment = 1;
		else
			increment = -1;
		i = increment;
		while (world.func_147439_a(x, y + i, z) == WildCaves.blockStoneStalactite || world.func_147439_a(x, y + i, z) == WildCaves.blockSandStalactite)
			i = i + increment;
		return world.func_147439_a(x, y + i, z).isNormalCube(world, x, y+i, z);
	}

	@Override
	public AxisAlignedBB func_149668_a(World par1World, int par2, int par3, int par4) {
		if (WildCaves.solidStalactites)
			return super.func_149668_a(par1World, par2, par3, par4);
		else
			return null;
	}

	@Override
	public int func_149643_k(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon func_149691_a(int side, int metadata) {
		if (metadata >= numOfStructures)
			metadata = numOfStructures - 1;
		return this.iconArray[metadata];
	}

	@Override
	public int func_149645_b() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149666_a(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < numOfStructures; ++i) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	@Override
	public boolean func_149686_d() {
		return false;
	}

	@Override
	public void func_149689_a(World world, int x, int y, int z, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		this.func_149674_a(world, x, y, z, null);
	}

    @Override
    public void func_149674_a(World world, int x, int y, int z, Random random) {
        if (!this.func_149718_j(world, x, y, z)){
            this.func_149697_b(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.func_147468_f(x, y, z);
        }
    }

	@Override
	public void func_149670_a(World world, int x, int y, int z, Entity entity) {
		entity.motionX *= 0.7D;
		entity.motionZ *= 0.7D;
	}

	@Override
	public void func_149746_a(World world, int par2, int par3, int par4, Entity entity, float par6) {
		if (WildCaves.damageWhenFallenOn && entity.isEntityAlive()) {
			entity.attackEntityFrom(DamageSource.generic, 5);
		}
	}

	@Override
	public void func_149695_a(World world, int x, int y, int z, Block blockID) {
		if (!world.isRemote && !this.func_149718_j(world, x, y, z)) {
			world.func_147480_a(x, y, z, true);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister iconRegister) {
		this.iconArray = new IIcon[numOfStructures];
		for (int i = 0; i < this.iconArray.length; ++i) {
			this.iconArray[i] = iconRegister.registerIcon(WildCaves.modid + func_149641_N() + i);
		}
	}

	@Override
	public boolean func_149662_c() {
		return false;
	}

	@Override
	public void func_149719_a(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int metadata = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		switch (metadata) {
		case 1:
			this.func_149676_a(0.25F, 0.2F, 0.25F, 0.75F, 1F, 0.75F);
			break;
		case 2:
			this.func_149676_a(0.25F, 0.5F, 0.25F, 0.75F, 1F, 0.75F);
			break;
		case 9:
			this.func_149676_a(0.25F, 0.0F, 0.25F, 0.75F, 0.8F, 0.75F);
			break;
		case 10:
			this.func_149676_a(0.25F, 0.0F, 0.25F, 0.75F, 0.4F, 0.75F);
			break;
		default:
			this.func_149676_a(0.25F, 0.0F, 0.25F, 0.75F, 1F, 0.75F);
			break;
		}
	}
}
