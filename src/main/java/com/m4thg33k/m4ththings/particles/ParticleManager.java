package com.m4thg33k.m4ththings.particles;


import com.m4thg33k.m4ththings.helpers.MathHelper;
import com.m4thg33k.m4ththings.tiles.tanks.TileBaseTank;
import com.m4thg33k.m4ththings.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
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

        double centerSpeed = 0.04;
        double centerDistance;
        double radius = 0.375 * tileBaseTank.getPercentFilled();
        double rad;
        double d1;
        double d2;
        EntityFX fluidOrb;
        double radiusMultiplier = 10.0;
        double verticalOffset = tileBaseTank.getPercentFilled() * Math.sin((double) tileBaseTank.getTimer() * MathHelper.pi / (180.0));
        double randomHelper;
        double baseLifeLength = 10.0;

        switch (direction) {
            case 6: //UNKNOWN (from buckets) - no particles wanted
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

        }
    }
}
