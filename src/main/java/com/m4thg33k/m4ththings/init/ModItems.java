package com.m4thg33k.m4ththings.init;

import com.m4thg33k.m4ththings.helpers.NameHelper;
import com.m4thg33k.m4ththings.items.ItemModCrafting;
import com.m4thg33k.m4ththings.items.ItemModIngot;
import com.m4thg33k.m4ththings.items.ItemModNugget;
import com.m4thg33k.m4ththings.items.ItemWrench;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

    public static final ItemWrench itemWrench = new ItemWrench();
    public static final ItemModIngot itemModIngot = new ItemModIngot();
    public static final ItemModNugget itemModNugget = new ItemModNugget();
    public static final ItemModCrafting itemModCrafting = new ItemModCrafting();

    public static void init()
    {
        GameRegistry.registerItem(itemWrench,NameHelper.getStrippedName(itemWrench.getUnlocalizedName()));
        GameRegistry.registerItem(itemModIngot, NameHelper.getStrippedName(itemModIngot.getUnlocalizedName()));
        GameRegistry.registerItem(itemModNugget, NameHelper.getStrippedName(itemModNugget.getUnlocalizedName()));
        GameRegistry.registerItem(itemModCrafting, NameHelper.getStrippedName(itemModCrafting.getUnlocalizedName()));
    }
}
