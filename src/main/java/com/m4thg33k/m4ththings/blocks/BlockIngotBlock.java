package com.m4thg33k.m4ththings.blocks;

import com.m4thg33k.m4ththings.helpers.NameHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class BlockIngotBlock extends Block{

    IIcon[] icons = new IIcon[1];

    public BlockIngotBlock(Material material)
    {
        super(material);
        setHardness(5.0f);
        setResistance(10.f);
        setBlockName(NameHelper.blockItemName("blockIngotBlock"));
        // TODO: 12/6/2015 set creative tab
        setStepSound(Blocks.iron_block.stepSound);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(NameHelper.textureName("goldPlatedIronBlock"));
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        switch (meta)
        {
            default:
                return icons[0];
        }
    }
}
