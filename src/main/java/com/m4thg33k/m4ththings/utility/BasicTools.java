package com.m4thg33k.m4ththings.utility;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.commons.lang3.StringUtils;

import java.util.Stack;

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

    public static Vec3 scaleVector(Vec3 vec,double scale)
    {
        return Vec3.createVectorHelper(vec.xCoord*scale,vec.yCoord*scale,vec.zCoord*scale);
    }

    public static Vec3 addVectors(Vec3[] vecs)
    {
        Vec3 sum = Vec3.createVectorHelper(0,0,0);
        if (vecs!=null)
        {
            for (int i=0;i<vecs.length;i++) {
                sum = sum.addVector(vecs[i].xCoord, vecs[i].yCoord, vecs[i].zCoord);
            }
        }
        return sum;
    }

    //takes a string and creates a vec3 out of it
    //if the given string is not one that is returned from vec3, this will cause an error
    public static Vec3 stringToVec3(String string)
    {
        int comma = string.indexOf(',');
        double x = Double.parseDouble(string.substring(1,comma));
        int comma2 = string.indexOf(',',comma+2);
        double y = Double.parseDouble(string.substring(comma+1,comma2));
        double z = Double.parseDouble(string.substring(comma2+2,string.length()-1));

        return Vec3.createVectorHelper(x,y,z);
    }

    //takes in a Vec3[] and returns a string with all the data embedded
    public static String Vec3ArrayToString(Vec3[] vecs)
    {
        String toReturn = "";

        if (vecs==null)
        {
            return toReturn;
        }

        for (int i=0;i<vecs.length;i++)
        {
            toReturn += vecs[i].toString();
            if (i!=(vecs.length-1))
            {
                toReturn += "$";
            }
        }

        return toReturn;
    }

    //takes in a string and returns a Vec3[]
    public static Vec3[] stringToVec3Array(String string)
    {
        if (string==null || string.length()==0)
        {
            return null;
        }

        String[] strings = StringUtils.split(string,"$");
        Vec3[] toReturn = new Vec3[strings.length];

        for (int i=0;i<strings.length;i++)
        {
            toReturn[i] = stringToVec3(strings[i]);
        }

        return toReturn;
    }


    public static int[] stackToIntArray(Stack<LocVec> stack)
    {
        int[] toReturn = new int[3*stack.size()];
        int j;
        int[] temp;

        for (int i=0;i<stack.size();i++)
        {
            temp = stack.elementAt(i).getLoc();
            System.arraycopy(temp,0,toReturn,3*i,3);
        }

        return toReturn;
    }

    public static Stack<LocVec> intArrayToStack(int[] locs)
    {
        Stack<LocVec> stack = new Stack<LocVec>();
        int[] temp = new int[3];

        for (int i=0;i<locs.length;i+=3)
        {
            System.arraycopy(locs,i,temp,0,3);
            stack.push(new LocVec(temp));
        }

        return stack;
    }
}
