package com.m4thg33k.m4ththings.init;

import com.m4thg33k.m4ththings.blocks.BlockFluidTransportEntry;
import com.m4thg33k.m4ththings.blocks.BlockIngotBlock;
import com.m4thg33k.m4ththings.blocks.BlockTest;
import com.m4thg33k.m4ththings.blocks.BlockTransportBlock;
import com.m4thg33k.m4ththings.blocks.tanks.BlockAdvancedTank;
import com.m4thg33k.m4ththings.blocks.tanks.BlockBaseTank;
import com.m4thg33k.m4ththings.helpers.NameHelper;
import com.m4thg33k.m4ththings.items.ItemAdvancedTank;
import com.m4thg33k.m4ththings.items.ItemBaseTank;
import com.m4thg33k.m4ththings.items.ItemIngotBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;

public class ModBlocks {

    public static final BlockBaseTank blockBaseTank = new BlockBaseTank(Material.glass);
    public static final BlockAdvancedTank blockAdvancedTank = new BlockAdvancedTank(Material.glass);
    public static final BlockIngotBlock blockIngotBlock = new BlockIngotBlock(Material.iron);
    public static final BlockTest blockTest = new BlockTest(Material.rock);
    public static final BlockTransportBlock blockTransportBlock = new BlockTransportBlock(Material.rock);
    public static final BlockFluidTransportEntry blockFluidTransportEntry = new BlockFluidTransportEntry(Material.rock);

    public static void init()
    {
        GameRegistry.registerBlock(blockBaseTank, ItemBaseTank.class, NameHelper.getStrippedName(blockBaseTank.getUnlocalizedName()));
        GameRegistry.registerBlock(blockAdvancedTank, ItemAdvancedTank.class, NameHelper.getStrippedName(blockAdvancedTank.getUnlocalizedName()));
        GameRegistry.registerBlock(blockIngotBlock, ItemIngotBlock.class, NameHelper.getStrippedName(blockIngotBlock.getUnlocalizedName()));
        GameRegistry.registerBlock(blockTransportBlock,NameHelper.getStrippedName(blockTransportBlock.getUnlocalizedName()));
        GameRegistry.registerBlock(blockFluidTransportEntry,NameHelper.getStrippedName(blockFluidTransportEntry.getUnlocalizedName()));
        GameRegistry.registerBlock(blockTest, NameHelper.getStrippedName(blockTest.getUnlocalizedName()));
    }
}
