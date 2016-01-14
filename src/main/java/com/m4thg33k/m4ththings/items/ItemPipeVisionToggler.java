package com.m4thg33k.m4ththings.items;

import com.m4thg33k.m4ththings.helpers.NameHelper;
import com.m4thg33k.m4ththings.players.M4thExtendedPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPipeVisionToggler extends Item{

    public ItemPipeVisionToggler()
    {
        setUnlocalizedName(NameHelper.blockItemName("itemPipeVisionToggler"));
        setMaxStackSize(1);
        setTextureName(NameHelper.textureName("itemPipeVisionToggler"));
    }

//    @Override
//    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
//        (M4thExtendedPlayer.get(player)).toggleSight();
//        return true;
//    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        (M4thExtendedPlayer.get(player)).toggleSight();
        return stack;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return itemStack;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack p_77630_1_) {
        return false;
    }
}
