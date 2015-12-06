package com.m4thg33k.m4ththings.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemIngotBlock extends ItemBlockWithMetadata {

    public ItemIngotBlock(Block block)
    {
        super(block,block);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + "_" + stack.getItemDamage();
    }
}
