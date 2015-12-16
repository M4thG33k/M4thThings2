package com.m4thg33k.m4ththings.utility;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BasicTools {

    public static int[] boolToIntArray(boolean[] vals)
    {
        int[] toReturn = new int[vals.length];
        for (int i=0;i<toReturn.length;i++)
        {
            toReturn[i] = vals[i] ? 1 : 0;
            //LogHelper.info(i + " " + toReturn[i] + " " + vals[i]);
        }
        return toReturn;
    }

    public static boolean[] intToBoolArray(int[] vals)
    {
        boolean[] toReturn = new boolean[vals.length];
        for (int i=0;i<toReturn.length;i++)
        {
            toReturn[i] = vals[i]==1;
            //LogHelper.info(i + " " + toReturn[i] + " " + vals[i]);
        }
        return toReturn;
    }

    public static TileEntity getTEOnSide(World world, int x, int y, int z, ForgeDirection side)
    {
        return world.getTileEntity(x+side.offsetX,y+side.offsetY,z+side.offsetZ);
    }

    public static TileEntity getTEAtRelLoc(World world, int x, int y, int z, LocVec locVec)
    {
        return world.getTileEntity(x+locVec.getX(),y+locVec.getY(),z+locVec.getZ());
    }
}
