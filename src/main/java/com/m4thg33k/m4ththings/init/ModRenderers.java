package com.m4thg33k.m4ththings.init;

import com.m4thg33k.m4ththings.renderers.BaseTankRenderer;
import com.m4thg33k.m4ththings.renderers.itemRenderers.BaseTankItemRenderer;
import com.m4thg33k.m4ththings.tiles.tanks.TileBaseTank;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ModRenderers {

    public static void init()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileBaseTank.class, new BaseTankRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockBaseTank), new BaseTankItemRenderer(new BaseTankRenderer(),new TileBaseTank()));
    }

}
