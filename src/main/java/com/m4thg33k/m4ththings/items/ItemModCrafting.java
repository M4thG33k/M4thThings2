package com.m4thg33k.m4ththings.items;

import com.m4thg33k.m4ththings.helpers.NameHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemModCrafting extends Item {

    IIcon[] icons = new IIcon[1];

    public ItemModCrafting()
    {
        setUnlocalizedName(NameHelper.blockItemName("itemModCrafting"));
        // TODO: 12/6/2015 set creative tab
        setMaxStackSize(64);
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(NameHelper.textureName("itemTankValve"));
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        if (meta<icons.length)
        {
            return icons[meta];
        }

        return icons[0];
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + "_" + stack.getItemDamage();
    }

    @SideOnly(Side.CLIENT)

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add(EnumChatFormatting.ITALIC + "Crafting item - no other use");
    }
}
