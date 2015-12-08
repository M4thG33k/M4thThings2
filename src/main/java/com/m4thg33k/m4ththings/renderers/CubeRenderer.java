package com.m4thg33k.m4ththings.renderers;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

public class CubeRenderer {

    //make sure textures are bound before entering
    public static void renderCube(double x, double y, double z, double rotationX,double rotationY, double rotationZ, double scale, IIcon icon)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x,y,z);
        GL11.glRotated(rotationX,1.0,0.0,0.0);
        GL11.glRotated(rotationY,0.0,1.0,0.0);
        GL11.glRotated(rotationZ,0.0,0.0,1.0);
        GL11.glScaled(scale,scale,scale);
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();

        t.addVertexWithUV(-1.0,1.0,1.0,icon.getMaxU(),icon.getMinV());
        t.addVertexWithUV(-1.0,1.0,-1.0,icon.getMinU(),icon.getMinV());
        t.addVertexWithUV(-1.0,-1.0,-1.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-1.0,-1.0,1.0,icon.getMaxU(),icon.getMaxV());

        t.addVertexWithUV(-1.0,1.0,-1.0,icon.getMaxU(),icon.getMinV());
        t.addVertexWithUV(1.0,1.0,-1.0,icon.getMinU(),icon.getMinV());
        t.addVertexWithUV(1.0,-1.0,-1.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-1.0,-1.0,-1.0,icon.getMaxU(),icon.getMaxV());

        t.addVertexWithUV(1.0,1.0,-1.0,icon.getMaxU(),icon.getMinV());
        t.addVertexWithUV(1.0,1.0,1.0,icon.getMinU(),icon.getMinV());
        t.addVertexWithUV(1.0,-1.0,1.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(1.0,-1.0,-1.0,icon.getMaxU(),icon.getMaxV());

        t.addVertexWithUV(1.0,1.0,1.0,icon.getMaxU(),icon.getMinV());
        t.addVertexWithUV(-1.0,1.0,1.0,icon.getMinU(),icon.getMinV());
        t.addVertexWithUV(-1.0,-1.0,1.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(1.0,-1.0,1.0,icon.getMaxU(),icon.getMaxV());

        t.addVertexWithUV(-1.0,-1.0,1.0,icon.getMaxU(),icon.getMinV());
        t.addVertexWithUV(-1.0,-1.0,-1.0,icon.getMinU(),icon.getMinV());
        t.addVertexWithUV(1.0,-1.0,-1.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(1.0,-1.0,1.0,icon.getMaxU(),icon.getMaxV());

        t.addVertexWithUV(1.0,1.0,1.0,icon.getMaxU(),icon.getMinV());
        t.addVertexWithUV(1.0,1.0,-1.0,icon.getMinU(),icon.getMinV());
        t.addVertexWithUV(-1.0,1.0,-1.0,icon.getMinU(),icon.getMaxV());
        t.addVertexWithUV(-1.0,1.0,1.0,icon.getMaxU(),icon.getMaxV());

        t.draw();
        GL11.glPopMatrix();

    }
}
