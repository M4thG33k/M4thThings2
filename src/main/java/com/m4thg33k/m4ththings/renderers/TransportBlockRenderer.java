package com.m4thg33k.m4ththings.renderers;

import com.m4thg33k.m4ththings.init.ModBlocks;
import com.m4thg33k.m4ththings.init.ModItems;
import com.m4thg33k.m4ththings.tiles.TileTest;
import com.m4thg33k.m4ththings.tiles.TileTransportBlock;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TransportBlockRenderer extends TileEntitySpecialRenderer{

    public TransportBlockRenderer()
    {

    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        ItemStack held = this.field_147501_a.field_147551_g.getHeldItem();
        boolean flag = held == null || !(held.getItem()== ModItems.itemWrench || held.getItem() == Item.getItemFromBlock(ModBlocks.blockTransportBlock));
        //boolean flag = false; //swap comments to make it invisible when holding the correct item(s)
        if (flag)
        {
            return;
        }
        bindTexture(TextureMap.locationBlocksTexture);
        CubeRenderer.renderCube(x+0.5,y+0.5,z+0.5,0.0,0.0,0.0,0.05, Blocks.cobblestone.getIcon(0,0));

        if (tile!=null && tile instanceof TileTransportBlock){
            boolean[] sides = ((TileTransportBlock) tile).getConnections();
            ForgeDirection[] d = ForgeDirection.VALID_DIRECTIONS;
            for (int i=0;i<6;i++)
            {
                if (sides[i])
                {
                    CubeRenderer.renderCube(x+0.5+ 0.1*d[i].offsetX,y+0.5+0.1*d[i].offsetY,z+0.5+0.1*d[i].offsetZ,0.0,0.0,0.0,0.05,Blocks.diamond_block.getIcon(0,0));
                }
            }
        }
    }
}
