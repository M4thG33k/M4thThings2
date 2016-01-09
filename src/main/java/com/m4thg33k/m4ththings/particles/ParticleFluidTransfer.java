package com.m4thg33k.m4ththings.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class ParticleFluidTransfer extends EntityFX {

    public static final int MAX_AGE = 6;
    public static final int MAX_SCALE = 1;

    protected ParticleFluidTransfer(World world, double posX, double posY, double posZ)
    {
        this(world, posX, posY, posZ, 0, 0, 0, MAX_SCALE, MAX_AGE, 0.0f, 0.3f, 0.8f);
    }

    public ParticleFluidTransfer(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ)
    {
        this(world, posX, posY, posZ, motionX, motionY, motionZ, MAX_SCALE, MAX_AGE, 0.0f, 0.3f, 0.8f);
    }

    public ParticleFluidTransfer(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float scale, int maxAge, float red, float green, float blue)
    {
        super(world, posX, posY, posZ, 0, 0, 0);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.particleTextureIndexX = 4;
        this.particleTextureIndexY = 2;
        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        this.particleScale = scale;
        this.particleMaxAge = maxAge;
        this.noClip = true;
        this.particleGravity = 0.05f;
        this.particleAlpha = 0.9f;
    }

    @Override
    public void onUpdate() {
        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        this.moveEntity(this.motionX,this.motionY,this.motionZ);

        //this.particleScale = MAX_SCALE*(this.particleAge%3);
        this.particleAlpha -= 0.9f / (MAX_AGE*1.25f);
    }

    @Override
    public void renderParticle(Tessellator tess, float partialTicks, float x, float y, float z, float u, float v) {
        float umin = (float)this.particleTextureIndexX / 16.0F;
        float umax = umin + 0.25f;
        float vmin = (float)this.particleTextureIndexY / 16.0F;
        float vmax = vmin + 0.25f;
        float f10 = 0.1F * this.particleScale;

        if (this.particleIcon != null)
        {
            umin = this.particleIcon.getMinU();
            umax = this.particleIcon.getMaxU();
            vmin = this.particleIcon.getMinV();
            vmax = this.particleIcon.getMaxV();
        }
//
//        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)posX - interpPosX);
//        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)posX - interpPosY);
//        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)posX - interpPosZ);
//        tess.setColorRGBA_F(this.particleRed,this.particleGreen,this.particleBlue,this.particleAlpha);
//        tess.addVertexWithUV(f11 - posY * f10 - par6 * f10, f12 - posZ * f10, f13 - par5 * f10 - par7 * f10, uMax, vMax);
//        tess.addVertexWithUV(f11 - posY * f10 + par6 * f10, f12 + posZ * f10, f13 - par5 * f10 + par7 * f10, uMax, vMin);
//        tess.addVertexWithUV(f11 + posY * f10 + par6 * f10, f12 + posZ * f10, f13 + par5 * f10 + par7 * f10, uMin, vMin);
//        tess.addVertexWithUV(f11 + posY * f10 - par6 * f10, f12 - posZ * f10, f13 + par5 * f10 - par7 * f10, uMin, vMax);

        partialTicks = 0;

        float scale = 0.1F * this.particleScale;
        float xAdj = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
        float yAdj = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
        float zAdj = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);

        //Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        tess.setColorRGBA_F(this.particleRed,this.particleGreen,this.particleBlue,this.particleAlpha);
        tess.addVertexWithUV((double)(xAdj - x*scale - u*scale),(double)(yAdj - y*scale),(double)(zAdj - z*scale - v*scale),umax,vmax);
        tess.addVertexWithUV((double)(xAdj - x*scale + u*scale),(double)(yAdj + y*scale),(double)(zAdj - z*scale + v*scale),umax,vmin);
        tess.addVertexWithUV((double)(xAdj + x*scale + u*scale),(double)(yAdj + y*scale),(double)(zAdj + z*scale + v*scale),umin,vmin);
        tess.addVertexWithUV((double)(xAdj + x*scale - u*scale),(double)(yAdj - y*scale),(double)(zAdj + z*scale - v*scale),umin,vmax);

    }

}
