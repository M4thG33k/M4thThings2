package com.m4thg33k.m4ththings.renderers.itemRenderers;

import com.m4thg33k.m4ththings.tiles.tanks.TileBaseTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class BaseTankItemRenderer implements IItemRenderer {

    private TileEntity tileEntity;

    public BaseTankItemRenderer(TileEntitySpecialRenderer tesr, TileEntity ent)
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
        if (item.hasTagCompound() && item.stackTagCompound.hasKey("FluidName") && FluidRegistry.getFluid(item.stackTagCompound.getString("FluidName"))!=null)
        {
            ((TileBaseTank)tileEntity).setFluid(new FluidStack(FluidRegistry.getFluid(item.stackTagCompound.getString("FluidName")), item.getTagCompound().getInteger("Amount")));
            ((TileBaseTank)tileEntity).setTimer(item.getTagCompound().getInteger("Timer"));
        }
        else
        {
            ((TileBaseTank)tileEntity).setEmpty();
        }

        if (type==ItemRenderType.ENTITY)
        {
            GL11.glTranslated(-0.5,-0.25,-0.5);
            GL11.glPushMatrix();

            TileEntityRendererDispatcher.instance.renderTileEntityAt(tileEntity,0.0,0.0,0.0,0.0f);
            GL11.glPopMatrix();
        }

        if (type==ItemRenderType.INVENTORY)
        {
            GL11.glTranslated(-0.5,-0.5,-0.5);
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
            GL11.glTranslated(0.0,0.0,0.0);
            GL11.glPushMatrix();
            TileEntityRendererDispatcher.instance.renderTileEntityAt(tileEntity,0.0,0.0,0.0,0.0f);
            GL11.glPopMatrix();
        }

        if (type==ItemRenderType.EQUIPPED_FIRST_PERSON)
        {
            GL11.glTranslated(0.0,0.0,0.0);
            GL11.glPushMatrix();
            TileEntityRendererDispatcher.instance.renderTileEntityAt(tileEntity,0.0,0.0,0.0,0.0f);
            GL11.glPopMatrix();
        }
    }
}
