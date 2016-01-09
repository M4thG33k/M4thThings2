package com.m4thg33k.m4ththings.utility;

import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Arrays;
import java.util.Vector;

public class LocVec {

    int[] loc = new int[3];

    public LocVec(int x,int y,int z)
    {
        loc[0] = x;
        loc[1] = y;
        loc[2] = z;
    }

    public LocVec(int[] input)
    {
        System.arraycopy(input,0,loc,0,Math.min(3,input.length));
    }

    public void setX(int x)
    {
        loc[0] = x;
    }

    public int getX()
    {
        return loc[0];
    }

    public void setY(int y)
    {
        loc[1] = y;
    }

    public int getY()
    {
        return loc[1];
    }

    public void setZ(int z)
    {
        loc[2] = z;
    }

    public int getZ()
    {
        return loc[2];
    }

    public void setLoc(int[] input)
    {
        System.arraycopy(input,0,loc,0,Math.min(3,input.length));
    }

    public void setLoc(int x, int y, int z)
    {
        loc[0] = x;
        loc[1] = y;
        loc[2] = z;
    }

    public int[] getLoc()
    {
        return loc;
    }

    public void addVec(int x, int y, int z)
    {
        loc[0] += x;
        loc[1] += y;
        loc[2] += z;
    }

    public void addVec(int[] input)
    {
        for (int i=0;i<Math.min(3,input.length);i++)
        {
            loc[i] += input[i];
        }
    }

    public void addVec(LocVec locVec)
    {
        addVec(locVec.getLoc());
    }

    public void addVec(ForgeDirection side)
    {
        addVec(side.offsetX,side.offsetY,side.offsetZ);
    }

    public LocVec copy()
    {
        return new LocVec(this.getLoc());
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof LocVec && Arrays.equals(loc,((LocVec) obj).getLoc()));
    }

    public LocVec difference(LocVec input)
    {
        LocVec toReturn = this.copy();
        //int[] in = input.getLoc();
        int[] in = new int[3];
        System.arraycopy(input.getLoc(),0,in,0,3);
        for (int i=0;i<3;i++)
        {
            in[i] = -in[i];
        }
        toReturn.addVec(in);
        return toReturn;
    }

    //scans the indices and returns the first non-zero direction
    public ForgeDirection direction()
    {
        if (loc[1]==-1)
        {
            return ForgeDirection.DOWN;
        }
        if (loc[1]==1)
        {
            return ForgeDirection.UP;
        }
        if (loc[2]==-1)
        {
            return ForgeDirection.NORTH;
        }
        if (loc[2]==1)
        {
            return ForgeDirection.SOUTH;
        }
        if (loc[0]==-1)
        {
            return ForgeDirection.WEST;
        }
        if (loc[0]==1)
        {
            return ForgeDirection.EAST;
        }
        return ForgeDirection.UNKNOWN;
    }

    public Vec3 getVec3()
    {
        return Vec3.createVectorHelper(loc[0],loc[1],loc[2]);
    }

    public int oneNorm()
    {
        int total = 0;
        for (int i=0;i<3;i++)
        {
            total += Math.abs(loc[i]);
        }

        return total;
    }
}
