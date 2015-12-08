package com.m4thg33k.m4ththings.tiles;

import com.m4thg33k.m4ththings.helpers.StringHelper;
import com.m4thg33k.m4ththings.interfaces.IConnectableSides;
import com.m4thg33k.m4ththings.interfaces.IM4thNBTSync;
import com.m4thg33k.m4ththings.packets.ModPackets;
import com.m4thg33k.m4ththings.packets.PacketNBT;
import com.m4thg33k.m4ththings.utility.BasicTools;
import com.m4thg33k.m4ththings.utility.LogHelper;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileConnectable extends TileEntity implements IConnectableSides,IM4thNBTSync {

    boolean[] sides = new boolean[6];


    public TileConnectable()
    {
        super();
        for (int i=0;i<6;i++)
        {
            sides[i] = false;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        if (tagCompound.hasKey("ConnectedSides"))
        {
            //LogHelper.info("Reading from NBT!!");
            sides = BasicTools.intToBoolArray(tagCompound.getIntArray("ConnectedSides"));
        }

    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        LogHelper.info("Writing to NBT!!");
        super.writeToNBT(tagCompound);
        tagCompound.setIntArray("ConnectedSides",BasicTools.boolToIntArray(sides));
    }

    //IConnectableSides

    @Override
    public boolean isSideConnected(ForgeDirection side) {
        return side.ordinal()<6 && sides[side.ordinal()];
    }

    @Override
    public void makeConnection(ForgeDirection side,boolean attempt) {
        //LogHelper.info("Making connection at " + StringHelper.coordinates(xCoord,yCoord,zCoord) + " on side: " + side.ordinal());
        if (side.ordinal()<6)
        {
            sides[side.ordinal()] = true;
        }
//        if (attempt)
//        {
//            attemptToConnectNeighbor(side);
//        }
        prepareSync();
    }

    @Override
    public void breakConnection(ForgeDirection side,boolean attempt) {
        //LogHelper.info("Breaking connection at " + StringHelper.coordinates(xCoord,yCoord,zCoord) + " on side: " + side.ordinal());
        if (side.ordinal()<6)
        {
            sides[side.ordinal()] = true;
        }
//        if (attempt)
//        {
//            attemptToBreakNeighbor(side);
//        }
        prepareSync();
    }

    @Override
    public void toggleConnection(ForgeDirection side,boolean attempt) {
        if (side.ordinal()<6)
        {
            //sides[side.ordinal()] = !sides[side.ordinal()];
            if (sides[side.ordinal()])
            {
                sides[side.ordinal()] = false;
//                if (attempt)
//                {
//                    attemptToBreakNeighbor(side);
//                }
            }
            else
            {
                sides[side.ordinal()] = true;
//                if (attempt)
//                {
//                    attemptToConnectNeighbor(side);
//                }
            }
        }
        prepareSync();
    }

    @Override
    public boolean[] getConnections() {
        return sides;
    }

    //IM4thNBTSync
    @Override
    public void receiveNBTPacket(NBTTagCompound tagCompound) {
        this.readFromNBT(tagCompound);
        this.markDirty();
    }

    public void prepareSync() {
        if (!worldObj.isRemote)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            this.writeToNBT(tagCompound);
            ModPackets.INSTANCE.sendToAllAround(new PacketNBT(xCoord,yCoord,zCoord,tagCompound),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
            this.markDirty();
        }
    }

    public void attemptToConnectNeighbor(ForgeDirection side)
    {
        TileEntity tileEntity = worldObj.getTileEntity(xCoord+side.offsetX,yCoord+side.offsetY,zCoord+side.offsetZ);
        if (tileEntity instanceof IConnectableSides)
        {
            ((IConnectableSides) tileEntity).makeConnection(side.getOpposite(),false);
        }
    }

    public void attemptToBreakNeighbor(ForgeDirection side)
    {
        TileEntity tileEntity = worldObj.getTileEntity(xCoord+side.offsetX,yCoord+side.offsetY,zCoord+side.offsetZ);
        if (tileEntity instanceof IConnectableSides)
        {
            ((IConnectableSides) tileEntity).breakConnection(side.getOpposite(),false);
        }
    }
}
