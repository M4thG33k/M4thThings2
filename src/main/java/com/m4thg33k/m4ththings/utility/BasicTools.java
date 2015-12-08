package com.m4thg33k.m4ththings.utility;

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
}
