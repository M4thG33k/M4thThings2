package com.m4thg33k.m4ththings.helpers;

import com.m4thg33k.m4ththings.M4thThings;

public class NameHelper {

    public static String blockItemName(String end)
    {
        return M4thThings.MOD_ID + "_" + end;
    }

    public static String textureName(String end)
    {
        return M4thThings.MOD_ID + ":" + end;
    }
}
