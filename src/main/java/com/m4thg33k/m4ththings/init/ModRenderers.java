package com.m4thg33k.m4ththings.init;

import com.m4thg33k.m4ththings.renderers.*;
import com.m4thg33k.m4ththings.renderers.itemRenderers.BaseTankItemRenderer;
import com.m4thg33k.m4ththings.renderers.itemRenderers.FluidTransferEntryItemRenderer;
import com.m4thg33k.m4ththings.renderers.itemRenderers.TransportBlockItemRenderer;
import com.m4thg33k.m4ththings.tiles.TileFluidTransportEntry;
import com.m4thg33k.m4ththings.tiles.TileTest;
import com.m4thg33k.m4ththings.tiles.TileTransportBlock;
import com.m4thg33k.m4ththings.tiles.TileTransportBlockInventory;
import com.m4thg33k.m4ththings.tiles.tanks.TileAdvancedTank;
import com.m4thg33k.m4ththings.tiles.tanks.TileBaseTank;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ModRenderers {

    public static void init()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileBaseTank.class, new BaseTankRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockBaseTank), new BaseTankItemRenderer(new BaseTankRenderer(),new TileBaseTank()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockAdvancedTank), new BaseTankItemRenderer(new BaseTankRenderer(), new TileAdvancedTank()));
        ClientRegistry.bindTileEntitySpecialRenderer(TileTransportBlock.class,new TransportBlockRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTransportBlockInventory.class, new TransportBlockInventoryRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockTransportBlock), new TransportBlockItemRenderer(new TransportBlockInventoryRenderer(), new TileTransportBlockInventory()));
        ClientRegistry.bindTileEntitySpecialRenderer(TileFluidTransportEntry.class,new FluidTransportEntryRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockFluidTransportEntry), new FluidTransferEntryItemRenderer(new FluidTransportEntryRenderer(), new TileFluidTransportEntry()));
        ClientRegistry.bindTileEntitySpecialRenderer(TileTest.class, new TestRenderer());
    }

}
