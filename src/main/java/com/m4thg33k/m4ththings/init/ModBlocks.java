package com.m4thg33k.m4ththings.init;

import com.m4thg33k.m4ththings.blocks.tanks.BlockBaseTank;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;

public class ModBlocks {

    public static final BlockBaseTank blockBaseTank = new BlockBaseTank(Material.glass);

    public static void init()
    {
        GameRegistry.registerBlock(blockBaseTank, "blockBaseTank");
    }
}
