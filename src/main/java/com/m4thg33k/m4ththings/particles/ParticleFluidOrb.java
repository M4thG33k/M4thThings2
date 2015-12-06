package com.m4thg33k.m4ththings.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;

public class ParticleFluidOrb extends EntityFX {

    private IIcon icon;

    protected ParticleFluidOrb(World world, double x, double y, double z)
    {
        super(world,x,y,z);
        this.particleRed = this.particleGreen = 0.0f;
        this.particleBlue = 1.0f;
        this.particleTextureJitterX = this.particleTextureJitterY = 0.0f;
    }

    public ParticleFluidOrb(World world, double x, double y, double z, double velX, double velY, double velZ, Fluid fluid, int tankSize, int life)
    {
        this(world,x,y,z);
        this.motionX = velX;
        this.motionY = velY;
        this.motionZ = velZ;
        this.icon = fluid.getIcon();
        this.particleScale = 0.125f * (1+tankSize*0.5f);
        this.particleMaxAge = life;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.moveEntity(this.motionX,this.motionY,this.motionZ);
    }

    @Override
    public void moveEntity(double xMotion, double yMotion, double zMotion) {
        this.posX += xMotion;
        this.posY += yMotion;
        this.posZ += zMotion;
    }

    @Override
    public void renderParticle(Tessellator tess, float partialTicks, float x, float y, float z, float u, float v)
    {
        double umin = icon.getMinU();
        double umax = icon.getMaxU();
        double vmin = icon.getMinV();
        double vmax = icon.getMaxV();

        float scale = 0.1F * this.particleScale;
        float xAdj = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
        float yAdj = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
        float zAdj = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);

        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        tess.startDrawingQuads();

        tess.addVertexWithUV((double)(xAdj - x*scale - u*scale),(double)(yAdj - y*scale),(double)(zAdj - z*scale - v*scale),umax,vmax);
        tess.addVertexWithUV((double)(xAdj - x*scale + u*scale),(double)(yAdj + y*scale),(double)(zAdj - z*scale + v*scale),umax,vmin);
        tess.addVertexWithUV((double)(xAdj + x*scale + u*scale),(double)(yAdj + y*scale),(double)(zAdj + z*scale + v*scale),umin,vmin);
        tess.addVertexWithUV((double)(xAdj + x*scale - u*scale),(double)(yAdj - y*scale),(double)(zAdj + z*scale - v*scale),umin,vmax);

        tess.draw();
        GL11.glPopMatrix();
    }

    @Override
    public int getFXLayer() {
        return 3;
    }


}
