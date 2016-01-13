package com.m4thg33k.m4ththings.particles;

import com.m4thg33k.m4ththings.helpers.StringHelper;
import com.m4thg33k.m4ththings.renderers.RendererHelper;
import com.m4thg33k.m4ththings.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ParticleFluidTransfer extends EntityFX {

    public static final int MAX_AGE = 10;
    public static final int MAX_SCALE = 1;

    private IIcon icon;
    private ResourceLocation textureMap;

    protected ParticleFluidTransfer(World world, double posX, double posY, double posZ)
    {
        this(world, posX, posY, posZ, 0, 0, 0, MAX_SCALE, MAX_AGE, 0.0f, 0.3f, 0.8f);
    }

    public ParticleFluidTransfer(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float RED, float GREEN, float BLUE)
    {
        this(world, posX, posY, posZ, motionX, motionY, motionZ, MAX_SCALE, MAX_AGE, RED, GREEN, BLUE);
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

        this.particleScale = 0.25f;
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
        //this.particleAlpha -= 0.05f / (MAX_AGE*1.25f);
    }

    @Override
    public void renderParticle(Tessellator tess, float posX, float posY, float posZ, float par5, float par6, float par7) {


        float uMin = (float)this.particleTextureIndexX / 16.0F;
        float uMax = uMin + 0.25f;
        float vMin = (float)this.particleTextureIndexY / 16.0F;
        float vMax = vMin + 0.25f;
        float f10 = 0.1F * this.particleScale;

        if (this.particleIcon != null)
        {
            //LogHelper.info("We have a particle!");
            uMin = this.particleIcon.getMinU();
            //umax = umin+1;
            uMax = this.particleIcon.getMaxU();
            vMin = this.particleIcon.getMinV();
            //vmax = vmin+1;
            vMax = this.particleIcon.getMaxV();
        }

        //LogHelper.info("UV: (" + umin + ", " + umax + ", " + vmin + ", " + vmax + ")");

        //par5 = 0;
        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)posX - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)posX - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)posX - interpPosZ);
        tess.setColorRGBA_F(this.particleRed,this.particleGreen,this.particleBlue,this.particleAlpha);
        //LogHelper.info(StringHelper.coordinates(this.particleRed,this.particleGreen, this.particleBlue));
        tess.addVertexWithUV(f11 - posY * f10 - par6 * f10, f12 - posZ * f10, f13 - par5 * f10 - par7 * f10, uMax, vMax);
        tess.addVertexWithUV(f11 - posY * f10 + par6 * f10, f12 + posZ * f10, f13 - par5 * f10 + par7 * f10, uMax, vMin);
        tess.addVertexWithUV(f11 + posY * f10 + par6 * f10, f12 + posZ * f10, f13 + par5 * f10 + par7 * f10, uMin, vMin);
        tess.addVertexWithUV(f11 + posY * f10 - par6 * f10, f12 - posZ * f10, f13 + par5 * f10 - par7 * f10, uMin, vMax);

//        partialTicks = 0;

//        float scale = 0.1F * this.particleScale;
//        float xAdj = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
//        float yAdj = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
//        float zAdj = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);

        //LogHelper.info(textureMap.getResourceDomain());
        //LogHelper.info(textureMap.getResourcePath());

//        BufferedImage img = null;
//        try {
//            img = ImageIO.read(new File(textureMap.getResourceDomain() + "/" + textureMap.getResourcePath()));
//        } catch (IOException e)
//        {
//            LogHelper.info("No such file");
//        }



        //Minecraft.getMinecraft().renderEngine.bindTexture(textureMap);
//        tess.setColorRGBA_F(this.particleRed,this.particleGreen,this.particleBlue,this.particleAlpha);
//        tess.addVertexWithUV((double)(xAdj - x*scale - u*scale),(double)(yAdj - y*scale),(double)(zAdj - z*scale - v*scale),umax,vmax);
//        tess.addVertexWithUV((double)(xAdj - x*scale + u*scale),(double)(yAdj + y*scale),(double)(zAdj - z*scale + v*scale),umax,vmin);
//        tess.addVertexWithUV((double)(xAdj + x*scale + u*scale),(double)(yAdj + y*scale),(double)(zAdj + z*scale + v*scale),umin,vmin);
//        tess.addVertexWithUV((double)(xAdj + x*scale - u*scale),(double)(yAdj - y*scale),(double)(zAdj + z*scale - v*scale),umin,vmax);

    }

}
