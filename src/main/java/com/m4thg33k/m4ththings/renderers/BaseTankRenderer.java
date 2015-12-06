package com.m4thg33k.m4ththings.renderers;

import com.m4thg33k.m4ththings.tiles.tanks.TileBaseTank;
import com.m4thg33k.m4ththings.utility.LogHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTankInfo;
import org.lwjgl.opengl.GL11;

public class BaseTankRenderer extends TileEntitySpecialRenderer {

    private IModelCustom fluidPorts;
    private ResourceLocation fluidPortTexture;
    private ResourceLocation fluidTexture;

    public BaseTankRenderer()
    {
        fluidPorts = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/quantumTank.obj"));
        fluidPortTexture = new ResourceLocation("m4ththings","models/fluidTank3.png");
        fluidTexture = TextureMap.locationBlocksTexture;
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {

        TileBaseTank tank = (TileBaseTank)tileEntity;
        FluidTankInfo tankInfo = tank.getTankInfo(ForgeDirection.DOWN)[0];

        //Render the frame
        GL11.glPushMatrix();
        GL11.glTranslated(x+0.5,y+0.5,z+0.5);
        bindTexture(fluidPortTexture);
        fluidPorts.renderAll();
        GL11.glPopMatrix();

        //render the sphere as long as there is fluid in the tank
        if (tankInfo.fluid != null && tankInfo.fluid.amount > 0) {
            int timer = tank.getTimer();
            double percentage = tankInfo.fluid.amount*1.0/tankInfo.capacity;
            bindTexture(fluidTexture);
            SphereRenderer.renderSphere(x + 0.5, y + 0.5+0.05*percentage*Math.sin(timer*3.14159/180), z + 0.5, (double)timer, 0.75*percentage, tankInfo.fluid.getFluid().getIcon());// Blocks.water.getIcon(0, 0));
        }

    }
}
