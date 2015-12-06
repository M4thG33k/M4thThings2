package com.m4thg33k.m4ththings.items;

import com.m4thg33k.m4ththings.helpers.NameHelper;
import com.m4thg33k.m4ththings.helpers.StringHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemModIngot extends Item {
    IIcon[] icons = new IIcon[1];

    public ItemModIngot()
    {
        setUnlocalizedName(NameHelper.blockItemName("itemModIngot"));
        //// TODO: 12/6/2015 set creative tab
        setMaxStackSize(64);
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon(NameHelper.textureName("goldPlatedIron"));
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        switch (meta)
        {
            default:
                return icons[0];
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return this.getUnlocalizedName() + "_" + itemStack.getItemDamage();
    }
}
