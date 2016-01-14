package com.m4thg33k.m4ththings.renderers.itemRenderers;

import com.m4thg33k.m4ththings.renderers.TransportBlockRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class TransportBlockItemRenderer implements IItemRenderer {

    private TileEntity tileEntity;

    public TransportBlockItemRenderer(TileEntitySpecialRenderer tesr, TileEntity ent)
    {
        this.tileEntity = ent;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return (type==ItemRenderType.ENTITY || type==ItemRenderType.INVENTORY || type==ItemRenderType.EQUIPPED || type==ItemRenderType.EQUIPPED_FIRST_PERSON);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (type==ItemRenderType.ENTITY)
        {
            GL11.glTranslated(-2.5,-2.5,-2.5);
            GL11.glScaled(5.0,5.0,5.0);
            GL11.glPushMatrix();

            TileEntityRendererDispatcher.instance.renderTileEntityAt(tileEntity,0.0,0.0,0.0,0.0f);
            GL11.glPopMatrix();
        }

        if (type==ItemRenderType.INVENTORY)
        {
            GL11.glTranslated(0,6.5,0.0);
            GL11.glRotated(90.0,1.0,0.0,0.0);
            GL11.glScaled(7.0,7.0,7.0);
            GL11.glPushMatrix();
            TileEntityRendererDispatcher.instance.renderTileEntityAt(tileEntity,0.0,0.0,0.0,0.0f);
            GL11.glPopMatrix();

/*
            //testing stuff
            GL11.glPushMatrix();
            EntityItem entityItem = new EntityItem(Minecraft.getMinecraft().theWorld,0D,0D,0D,new ItemStack(Items.ender_pearl,1));
            entityItem.hoverStart = 0.0F;
            RenderItem.renderInFrame = false;
            RenderManager.instance.renderEntityWithPosYaw(entityItem,0.0D,0.0D,0.0D,0.0F,0.0F);
            GL11.glPopMatrix();*/
        }

        if (type==ItemRenderType.EQUIPPED)
        {
            double scale = 5;
            GL11.glTranslated(-2,-2,-2);
            GL11.glScaled(scale,scale,scale);
            GL11.glPushMatrix();
            TileEntityRendererDispatcher.instance.renderTileEntityAt(tileEntity,0.0,0.0,0.0,0.0f);
            GL11.glPopMatrix();
        }

        if (type==ItemRenderType.EQUIPPED_FIRST_PERSON)
        {
            double scale = 5;
            GL11.glTranslated(-2,-2,-2);
            GL11.glScaled(scale,scale,scale);
            GL11.glPushMatrix();
            TileEntityRendererDispatcher.instance.renderTileEntityAt(tileEntity,0.0,0.0,0.0,0.0f);
            GL11.glPopMatrix();
        }
    }
}
