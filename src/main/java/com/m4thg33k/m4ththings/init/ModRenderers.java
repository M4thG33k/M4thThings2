package com.m4thg33k.m4ththings.init;

import com.m4thg33k.m4ththings.renderers.BaseTankRenderer;
import com.m4thg33k.m4ththings.tiles.tanks.TileBaseTank;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ModRenderers {

    public static void init()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileBaseTank.class, new BaseTankRenderer());
    }

}
