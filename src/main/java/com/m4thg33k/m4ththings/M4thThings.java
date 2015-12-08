package com.m4thg33k.m4ththings;


import com.m4thg33k.m4ththings.init.ModBlocks;
import com.m4thg33k.m4ththings.init.ModItems;
import com.m4thg33k.m4ththings.init.ModRecipes;
import com.m4thg33k.m4ththings.init.ModTiles;
import com.m4thg33k.m4ththings.packets.ModPackets;
import com.m4thg33k.m4ththings.proxies.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = M4thThings.MOD_ID, name = M4thThings.MOD_NAME, version = M4thThings.VERSION)
public class M4thThings {

    public static final String MOD_ID = "m4ththings";
    public static final String MOD_NAME = "M4th Things";
    public static final String VERSION = "@VERSION@";
    public static final String CHANNEL_NAME = MOD_ID;

    @Mod.Instance
    public static M4thThings instance = new M4thThings();

    @SidedProxy (clientSide = "com.m4thg33k.m4ththings.proxies.ClientProxy",serverSide = "com.m4ththings.proxies.ServerProxy")
    public static CommonProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        ModPackets.init();
        ModBlocks.init();
        ModItems.init();
        ModTiles.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        FMLInterModComms.sendMessage("Waila","register","com.m4thg33k.m4ththings.utility.WailaInteraction.load");
        ModRecipes.init();
        proxy.registerRenderers();

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {

    }
}
