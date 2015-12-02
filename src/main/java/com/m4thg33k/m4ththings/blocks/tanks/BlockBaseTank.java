package com.m4thg33k.m4ththings.blocks.tanks;

import com.m4thg33k.m4ththings.helpers.NameHelper;
import com.m4thg33k.m4ththings.tiles.tanks.TileBaseTank;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBaseTank extends Block implements ITileEntityProvider {

    public BlockBaseTank(Material material)
    {
        super (material);
        setHardness(2.0F);
        setResistance(5.0F);
        setBlockName(NameHelper.blockItemName("blockBaseTank"));
        setBlockTextureName(NameHelper.textureName("blank"));
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileBaseTank();
    }

    @Override
    public boolean isBlockNormalCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return Blocks.glass.getIcon(0,0);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        this.setBlockBounds(0.333f,0,0.333f,0.667f,1,0.667f);
    }
}
