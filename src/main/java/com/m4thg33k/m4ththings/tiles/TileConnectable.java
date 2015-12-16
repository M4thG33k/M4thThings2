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
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
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
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setIntArray("Connections",BasicTools.boolToIntArray(sides));
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        if (tagCompound.hasKey("Connections"))
        {
            sides = BasicTools.intToBoolArray(tagCompound.getIntArray("Connections"));
        }
    }

    // IConnectableSides

    @Override
    public boolean isSideConnected(ForgeDirection side) {
        return side.ordinal()<6 && sides[side.ordinal()];
    }

    @Override
    public void makeConnection(ForgeDirection side, boolean attempt) {
        if (worldObj.isRemote)
        {
            return;
        }
        if (!isSideConnected(side) && side.ordinal()<6)
        {
            sides[side.ordinal()] = true;
            if (attempt)
            {
                attemptToMakeNeighborConnection(side);
            }
            prepareSync();
        }
    }

    @Override
    public void breakConnection(ForgeDirection side, boolean attempt) {
        if (worldObj.isRemote)
        {
            return;
        }
        if (isSideConnected(side))
        {
            sides[side.ordinal()] = false;
            if (attempt)
            {
                attemptToBreakNeighborConnection(side);
            }
            prepareSync();
        }
    }

    @Override
    public void toggleConnection(ForgeDirection side, boolean attempt) {
        if (worldObj.isRemote || side.ordinal()>=6 || side.ordinal()<0)
        {
            return;
        }

        if (isSideConnected(side))
        {
            breakConnection(side,attempt);
        }
        else
        {
            makeConnection(side,attempt);
        }
    }

    @Override
    public boolean[] getConnections() {
        return sides;
    }

    //IM4thNBTSync

    @Override
    public void receiveNBTPacket(NBTTagCompound tagCompound) {
        this.readFromNBT(tagCompound);
    }

    @Override
    public void prepareSync() {
        if (!worldObj.isRemote)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            this.writeToNBT(tagCompound);
            ModPackets.INSTANCE.sendToAllAround(new PacketNBT(xCoord,yCoord,zCoord,tagCompound),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
            this.markDirty();
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        prepareSync();
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(xCoord,yCoord,zCoord,1,tagCompound);
    }

    public void attemptToMakeNeighborConnection(ForgeDirection side)
    {
        TileEntity tileEntity = worldObj.getTileEntity(xCoord+side.offsetX,yCoord+side.offsetY,zCoord+side.offsetZ);
        if (tileEntity!=null && tileEntity instanceof TileConnectable)
        {
            ((TileConnectable) tileEntity).makeConnection(side.getOpposite(),false);
        }
    }

    public void attemptToBreakNeighborConnection(ForgeDirection side)
    {
        TileEntity tileEntity = worldObj.getTileEntity(xCoord+side.offsetX,yCoord+side.offsetY,zCoord+side.offsetZ);
        if (tileEntity!=null && tileEntity instanceof TileConnectable)
        {
            ((TileConnectable) tileEntity).breakConnection(side.getOpposite(),false);
        }
    }

    public void attemptToConnectToSameType()
    {
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
        TileEntity tileEntity;
        for (int i=0;i<6;i++)
        {
            tileEntity = worldObj.getTileEntity(xCoord+directions[i].offsetX,yCoord+directions[i].offsetY,zCoord+directions[i].offsetZ);
            if (tileEntity!=null && tileEntity instanceof TileConnectable)
            {
                makeConnection(directions[i],true);
            }
        }
    }

    public void breakAllConnections()
    {
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
        //LogHelper.info("Attempting to break all connections");
        for (int i=0;i<6;i++)
        {
            breakConnection(directions[i],true);
        }
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }
}
