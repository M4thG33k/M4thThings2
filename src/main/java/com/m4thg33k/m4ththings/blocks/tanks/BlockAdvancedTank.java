package com.m4thg33k.m4ththings.blocks.tanks;

import com.m4thg33k.m4ththings.helpers.NameHelper;
import com.m4thg33k.m4ththings.tiles.tanks.TileAdvancedTank;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAdvancedTank extends BlockBaseTank {

    public BlockAdvancedTank(Material material)
    {
        super(material);
        setBlockName(NameHelper.blockItemName("blockAdvancedTank"));
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileAdvancedTank();
    }

    @Override
    protected boolean amAdvanced() {
        return true;
    }
}
