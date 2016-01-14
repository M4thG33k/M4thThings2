package com.m4thg33k.m4ththings.items;

import cofh.api.block.IDismantleable;
import cofh.api.item.IToolHammer;
import com.m4thg33k.m4ththings.helpers.NameHelper;
import com.m4thg33k.m4ththings.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemWrench extends Item implements IToolHammer{

    IIcon[] icons = new IIcon[2];

    public ItemWrench()
    {
        setUnlocalizedName(NameHelper.blockItemName("itemWrench"));
        //setCreativeTab() //// TODO: 12/3/2015 set creative tab
        setMaxStackSize(1);
        //setTextureName(NameHelper.textureName("itemWrench"));
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x,y,z);
        if (player.isSneaking() && block!=null && block instanceof IDismantleable)
        {
            if (((IDismantleable)block).canDismantle(player,world,x,y,z) && !world.isRemote)
            {
                ((IDismantleable) block).dismantleBlock(player, world, x, y, z, false);
            }
        }
//        if (player.isSneaking() && block==null)
//        {
//            stack.setItemDamage((stack.getItemDamage()+1)%2);
//            LogHelper.info("Damage set to: " + stack.getItemDamage());
//        }
        return false;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x,y,z);
        if (player.isSneaking() && (block==null || block.isAir(world,x,y,z)))
        {
            stack.setItemDamage((stack.getItemDamage()+1)%2);
            LogHelper.info("Damage set to: " + stack.getItemDamage());
            return true;
        }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (world.isRemote)
        {
            return stack;
        }

        //check if the player is looking at a block, if so, we abort
//        MovingObjectPosition position = player.rayTrace(1,1);
//        if (position==null || position.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
//        {
//            return stack;
//        }
        if (player.isSneaking())
        {
            stack.setItemDamage((stack.getItemDamage()+1)%2);
            //LogHelper.info("Damage set to: " + stack.getItemDamage());
        }
        return stack;
    }

    //IToolHammer

    @Override
    public boolean isUsable(ItemStack itemStack, EntityLivingBase entityLivingBase, int i, int i1, int i2) {
        return true;
    }

    @Override
    public void toolUsed(ItemStack itemStack, EntityLivingBase entityLivingBase, int i, int i1, int i2) {

    }

    @Override
    public void registerIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(NameHelper.textureName("itemWrench"));
        icons[1] = reg.registerIcon(NameHelper.textureName("itemWrenchOpposite"));
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        if (meta>=0 && meta<icons.length)
        {
            return icons[meta];
        }
        return icons[0];
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
