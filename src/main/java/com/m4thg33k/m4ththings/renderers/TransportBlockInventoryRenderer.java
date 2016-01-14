package com.m4thg33k.m4ththings.renderers;

import cofh.api.item.IToolHammer;
import com.m4thg33k.m4ththings.init.ModBlocks;
import com.m4thg33k.m4ththings.players.M4thExtendedPlayer;
import com.m4thg33k.m4ththings.tiles.TileTransportBlock;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class TransportBlockInventoryRenderer extends TileEntitySpecialRenderer {

    private IModelCustom block;
    private ResourceLocation blockTexture;
    private double scale = 0.09;

    public TransportBlockInventoryRenderer()
    {
        block = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/TransportBlock.obj"));
        blockTexture = new ResourceLocation("m4ththings","models/TransportBlockUV.png");
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        bindTexture(blockTexture);
        GL11.glPushMatrix();
        GL11.glTranslated(x+0.5,y+0.5,z+0.5);
        GL11.glScaled(scale,scale,scale);
        block.renderAll();
        GL11.glPopMatrix();
    }

}
