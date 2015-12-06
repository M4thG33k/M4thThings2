package com.m4thg33k.m4ththings.init;

import com.m4thg33k.m4ththings.blocks.BlockIngotBlock;
import com.m4thg33k.m4ththings.blocks.tanks.BlockBaseTank;
import com.m4thg33k.m4ththings.helpers.NameHelper;
import com.m4thg33k.m4ththings.items.ItemBaseTank;
import com.m4thg33k.m4ththings.items.ItemIngotBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;

public class ModBlocks {

    public static final BlockBaseTank blockBaseTank = new BlockBaseTank(Material.glass);
    public static final BlockIngotBlock blockIngotBlock = new BlockIngotBlock(Material.iron);

    public static void init()
    {
        GameRegistry.registerBlock(blockBaseTank, ItemBaseTank.class, "blockBaseTank");
        GameRegistry.registerBlock(blockIngotBlock, ItemIngotBlock.class, NameHelper.getStrippedName(blockIngotBlock.getUnlocalizedName()));
    }
}
