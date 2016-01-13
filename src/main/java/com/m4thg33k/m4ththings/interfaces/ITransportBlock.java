package com.m4thg33k.m4ththings.interfaces;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public interface ITransportBlock {

    boolean isSideConnected(ForgeDirection side);

    void setConnection(ForgeDirection side,boolean makeThis,boolean attemptNeighbor);

    void attemptToSetNeighborConnection(ForgeDirection side,boolean makeThis);

    void toggleConnection(ForgeDirection side,boolean attemptNeighbor);

    boolean[] getConnections();

    int isValidFluidConnection(ForgeDirection side,FluidStack fluidStack);

    void breakInvalidConnections();
}
