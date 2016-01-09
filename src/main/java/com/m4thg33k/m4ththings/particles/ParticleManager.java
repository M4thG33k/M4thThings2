package com.m4thg33k.m4ththings.particles;


import com.m4thg33k.m4ththings.helpers.MathHelper;
import com.m4thg33k.m4ththings.tiles.tanks.TileBaseTank;
import com.m4thg33k.m4ththings.utility.CubicSplineCreation;
import com.m4thg33k.m4ththings.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

public class ParticleManager {

    public static void tankFillParticles(World world, int xCoord, int yCoord, int zCoord, int direction, int isFilling, Fluid fluid,int amount, int tankSize) {
        if (!world.isRemote) {
            return;
        }

        EffectRenderer renderer = Minecraft.getMinecraft().effectRenderer;
        TileBaseTank tileBaseTank = (TileBaseTank) (world.getTileEntity(xCoord, yCoord, zCoord));

        double radius = 0.05 + 0.325*tileBaseTank.getPercentFilled();//0.375 * tileBaseTank.getPercentFilled();
        double rad;
        double d1;
        double d2;
        EntityFX fluidOrb;
        double verticalOffset = tileBaseTank.getPercentFilled() * Math.sin((double) tileBaseTank.getTimer() * MathHelper.pi / (180.0));
        double randomHelper;
        double baseLifeLength = 10.0;

        switch (direction) {
            case 6: //UNKNOWN (from buckets)
                if (isFilling==1) //we are filling from an unknown direction (normally buckets)
                {
                    double theta,phi,myLife,x1,y1,z1,xVel,yVel,zVel;
                    for (int i=0;i<amount;i+=10)
                    {
                        theta = MathHelper.randomRad();
                        phi = MathHelper.randomRad()/2.0;
                        randomHelper = 1.0 - 0.1*Math.random();
                        myLife = baseLifeLength*randomHelper;
                        x1 = xCoord+0.5+.4*Math.cos(theta)*Math.sin(phi);
                        y1 = yCoord+0.5+0.05*verticalOffset+0.4*Math.cos(phi);
                        z1 = zCoord+0.5+0.4*Math.sin(theta)*Math.sin(phi);
                        xVel = (0.4-radius)*Math.cos(theta)*Math.sin(phi)/myLife;
                        yVel = (0.4-radius)*Math.cos(phi)/myLife;
                        zVel = (0.4-radius)*Math.sin(theta)*Math.sin(phi)/myLife;
                        fluidOrb = new ParticleFluidOrb(world,x1,y1,z1,-xVel,-yVel,-zVel,fluid,tankSize,(int)myLife);
                        renderer.addEffect(fluidOrb);
                    }
                }
                else
                {
                    double theta,phi,myLife,x1,y1,z1,xVel,yVel,zVel;
                    for (int i=0;i<amount;i+=10)
                    {
                        theta = MathHelper.randomRad();
                        phi = MathHelper.randomRad()/2.0;
                        randomHelper = 1.0 - 0.1*Math.random();
                        myLife = baseLifeLength*randomHelper;
                        x1 = xCoord+0.5+radius*Math.cos(theta)*Math.sin(phi);
                        y1 = yCoord+0.5+0.05*verticalOffset+radius*Math.cos(phi);
                        z1 = zCoord+0.5+radius*Math.sin(theta)*Math.sin(phi);
                        xVel = (0.4-radius)*Math.cos(theta)*Math.sin(phi)/myLife;
                        yVel = (0.4-radius)*Math.cos(phi)/myLife;
                        zVel = (0.4-radius)*Math.sin(theta)*Math.sin(phi)/myLife;
                        fluidOrb = new ParticleFluidOrb(world,x1,y1,z1,xVel,yVel,zVel,fluid,tankSize,(int)myLife);
                        renderer.addEffect(fluidOrb);
                    }
                }
                break;
            case 0: //from DOWN (0)
                if (isFilling==1) // we are filling from DOWN
                {
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0 - 0.1*Math.random();
                        double myLife = baseLifeLength*randomHelper;
                        d1 = radius*Math.sin(rad);
                        d2 = radius*Math.cos(rad);
                        //LogHelper.info("(" + d1 + ", " + d2 + ")");
                        fluidOrb = new ParticleFluidOrb(world,xCoord+0.5,yCoord+0.05,zCoord+0.5,d1/myLife,(0.45+0.05*verticalOffset)/(myLife),d2/myLife,fluid,tankSize,(int)myLife);
                        renderer.addEffect(fluidOrb);
                    }
                }
                else
                {
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0 - 0.1*Math.random();
                        double myLife = baseLifeLength*randomHelper;
                        d1 = radius*Math.sin(rad);
                        d2 = radius*Math.cos(rad);
                        fluidOrb = new ParticleFluidOrb(world,xCoord+0.5+d1,yCoord+0.5+0.05*verticalOffset,zCoord+0.5+d2,-d1/myLife,-(0.45+0.05*verticalOffset)/(myLife),-d2/myLife,fluid,tankSize,(int)myLife);
                        renderer.addEffect(fluidOrb);
                    }
                }
                break;
            default: //from UP (1)
                if (isFilling==1) {
                    for (int i = 0; i < amount; i += 100) {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0 - 0.1 * Math.random();
                        double myLife = baseLifeLength * randomHelper;
                        d1 = radius * Math.sin(rad);
                        d2 = radius * Math.cos(rad);
                        //LogHelper.info("(" + d1 + ", " + d2 + ")");
                        fluidOrb = new ParticleFluidOrb(world, xCoord + 0.5, yCoord + 0.95, zCoord + 0.5, d1 / myLife, -(0.45 + 0.05 * verticalOffset) / (myLife), d2 / myLife, fluid, tankSize, (int) myLife);
                        renderer.addEffect(fluidOrb);
                    }
                }
                else
                {
                    for (int i=0;i<amount;i+=100)
                    {
                        rad = MathHelper.randomRad();
                        randomHelper = 1.0 - 0.1*Math.random();
                        double myLife = baseLifeLength*randomHelper;
                        d1 = radius*Math.sin(rad);
                        d2 = radius*Math.cos(rad);
                        fluidOrb = new ParticleFluidOrb(world,xCoord+0.5+d1,yCoord+0.5+0.05*verticalOffset,zCoord+0.5+d2,-d1/myLife,(0.45+0.05*verticalOffset)/(myLife),-d2/myLife,fluid,tankSize,(int)myLife);
                        renderer.addEffect(fluidOrb);
                    }
                }

        }
    }

    public static void fluidSplineParticles(World world, Vec3[] locs)
    {
        EffectRenderer renderer = Minecraft.getMinecraft().effectRenderer;
        EntityFX splineFX;
        Vec3[] derivs = CubicSplineCreation.generateNewDerivatives(locs);
        for (int i=0;i<locs.length-1;i++)
        {
            splineFX = new ParticleFluidTransfer(world,locs[i].xCoord,locs[i].yCoord,locs[i].zCoord,derivs[i].xCoord,derivs[i].yCoord,derivs[i].zCoord);
            renderer.addEffect(splineFX);
            //world.spawnParticle("reddust",locations[i].xCoord,locations[i].yCoord,locations[i].zCoord,0,0,0);
        }
    }
}
