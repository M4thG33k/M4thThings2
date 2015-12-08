package com.m4thg33k.m4ththings.interfaces;

import net.minecraftforge.common.util.ForgeDirection;

public interface IConnectableSides {

    boolean isSideConnected(ForgeDirection side);

    void makeConnection(ForgeDirection side,boolean attempt);

    void breakConnection(ForgeDirection side,boolean attempt);

    void toggleConnection(ForgeDirection side,boolean attempt);

    boolean[] getConnections();
}
