package com.m4thg33k.m4ththings.items;

import net.minecraft.block.Block;

public class ItemAdvancedTank extends ItemBaseTank {

    public ItemAdvancedTank(Block block)
    {
        super(block);
        capacity = 256000;
        setMaxStackSize(16);
    }
}
