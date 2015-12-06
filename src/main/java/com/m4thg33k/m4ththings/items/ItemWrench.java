package com.m4thg33k.m4ththings.items;

import cofh.api.block.IDismantleable;
import cofh.api.item.IToolHammer;
import com.m4thg33k.m4ththings.helpers.NameHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemWrench extends Item implements IToolHammer{

    IIcon icon;

    public ItemWrench()
    {
        setUnlocalizedName(NameHelper.blockItemName("itemWrench"));
        //setCreativeTab() //// TODO: 12/3/2015 set creative tab
        setMaxStackSize(1);
        setTextureName(NameHelper.textureName("itemWrench"));
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x,y,z);
        if (player.isSneaking() && block!=null && block instanceof IDismantleable)
        {
            if (((IDismantleable)block).canDismantle(player,world,x,y,z) && !world.isRemote)
            {
                ((IDismantleable)block).dismantleBlock(player,world,x,y,z,false);
            }
        }
        return false;
    }

    //IToolHammer

    @Override
    public boolean isUsable(ItemStack itemStack, EntityLivingBase entityLivingBase, int i, int i1, int i2) {
        return true;
    }

    @Override
    public void toolUsed(ItemStack itemStack, EntityLivingBase entityLivingBase, int i, int i1, int i2) {

    }
}
