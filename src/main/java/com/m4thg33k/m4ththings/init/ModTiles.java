package com.m4thg33k.m4ththings.init;

import com.m4thg33k.m4ththings.tiles.TileConnectable;
import com.m4thg33k.m4ththings.tiles.TileTest;
import com.m4thg33k.m4ththings.tiles.tanks.TileAdvancedTank;
import com.m4thg33k.m4ththings.tiles.tanks.TileBaseTank;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModTiles {

    public static void init()
    {
        GameRegistry.registerTileEntity(TileBaseTank.class,"tileBaseTank");
        GameRegistry.registerTileEntity(TileAdvancedTank.class,"tileAdvancedTank");
        GameRegistry.registerTileEntity(TileConnectable.class,"tileConnectable");
        GameRegistry.registerTileEntity(TileTest.class,"tileTest");
    }
}
