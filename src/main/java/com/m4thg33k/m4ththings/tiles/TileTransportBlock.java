package com.m4thg33k.m4ththings.tiles;

import com.m4thg33k.m4ththings.interfaces.IM4thNBTSync;
import com.m4thg33k.m4ththings.interfaces.ITransportBlock;
import com.m4thg33k.m4ththings.packets.ModPackets;
import com.m4thg33k.m4ththings.packets.PacketNBT;
import com.m4thg33k.m4ththings.utility.BasicTools;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public class TileTransportBlock extends TileEntity implements ITransportBlock,IM4thNBTSync {

    boolean[] connections = new boolean[6];
    ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
    int timer;

    public TileTransportBlock()
    {
        super();
        for (int i=0;i<6;i++)
        {
            connections[i] = false;
        }
        timer = 0;
    }

    @Override
    public void updateEntity() {
        timer  = (timer+1)%2880;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        if (tagCompound.hasKey("Connections"))
        {
            connections = BasicTools.intToBoolArray(tagCompound.getIntArray("Connections"));
        }
        if (tagCompound.hasKey("Timer"))
        {
            timer = tagCompound.getInteger("Timer");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setIntArray("Connections",BasicTools.boolToIntArray(connections));
        tagCompound.setInteger("Timer",timer);
    }

    @Override
    public boolean isSideConnected(ForgeDirection side)
    {
        return side.ordinal()<6 && connections[side.ordinal()];
    }

    @Override
    public void setConnection(ForgeDirection side,boolean makeThis,boolean attemptNeighbor)
    {
        if (worldObj.isRemote || side.ordinal()>6 || connections[side.ordinal()]==makeThis)
        {
            return;
        }

        connections[side.ordinal()] = makeThis;
        if (attemptNeighbor)
        {
            attemptToSetNeighborConnection(side,makeThis);
        }
        prepareSync();
    }

    @Override
    public void attemptToSetNeighborConnection(ForgeDirection side,boolean makeThis)
    {
        TileEntity tileEntity = BasicTools.getTEOnSide(worldObj,xCoord,yCoord,zCoord,side);
        if (tileEntity!=null && tileEntity instanceof ITransportBlock)
        {
            ((ITransportBlock) tileEntity).setConnection(side.getOpposite(),makeThis,false);
        }
    }

    @Override
    public void toggleConnection(ForgeDirection side, boolean attemptNeighbor) {
        if (side.ordinal()>=6)
        {
            return;
        }
        this.setConnection(side,!connections[side.ordinal()],true);
    }

    @Override
    public boolean[] getConnections() {
        return connections;
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
            ModPackets.INSTANCE.sendToAllAround(new PacketNBT(xCoord,yCoord,zCoord,tagCompound), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
            this.markDirty();
        }
    }

    //End IM4thNBTSync


    @Override
    public Packet getDescriptionPacket() {
        prepareSync();
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(xCoord,yCoord,zCoord,1,tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }

    public void connectToAllTransportBlocks()
    {
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
        TileEntity tileEntity;
        for (int i=0;i<6;i++)
        {
            tileEntity = BasicTools.getTEOnSide(worldObj,xCoord,yCoord,zCoord,directions[i]);
            if (tileEntity!=null && tileEntity instanceof ITransportBlock)
            {
                setConnection(directions[i],true,true);
            }
        }
    }

    public void connectToAllIFluidHandlers()
    {
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
        TileEntity tileEntity;
        for (int i=0;i<6;i++)
        {
            tileEntity = BasicTools.getTEOnSide(worldObj,xCoord,yCoord,zCoord,directions[i]);
            if (tileEntity!=null && tileEntity instanceof IFluidHandler)
            {
                setConnection(directions[i],true,true);
            }
        }
    }

    public void breakAllConnections()
    {
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
        for (int i=0;i<6;i++)
        {
            setConnection(directions[i],false,true);
        }
    }

    /*checks to see if the given side leads to a valid connection
        if ANS is the return value for this method,
        ANS&1==1 if the side is connected
        ANS&2==2 if the side is connected to an IFluidHandler
        ANS&4==4 if the side is connected to an ITransportBlock
     */
    @Override
    public int isValidFluidConnection(ForgeDirection side, FluidStack fluidStack)
    {
        int ANS = 0;
        if (side.ordinal()>=6 || !connections[side.ordinal()])
        {
            return ANS;
        }
        TileEntity tileEntity = BasicTools.getTEOnSide(worldObj,xCoord,yCoord,zCoord,side);
        if (tileEntity!=null)
        {
            if (tileEntity instanceof IFluidHandler && ((IFluidHandler) tileEntity).fill(side.getOpposite(),fluidStack,false)>0)
            {
                ANS += 2;
            }
            if (tileEntity instanceof ITransportBlock)
            {
                ANS += 4;
            }
        }
        if (ANS>0)
        {
            ANS += 1;
        }
        return ANS;
    }

    @Override
    public void breakInvalidConnections()
    {
        TileEntity tileEntity;
        for (int i=0;i<6;i++)
        {
            if (connections[i])
            {
                tileEntity = BasicTools.getTEOnSide(worldObj,xCoord,yCoord,zCoord,directions[i]);
                if (tileEntity==null || !(tileEntity instanceof ITransportBlock || tileEntity instanceof IFluidHandler))
                {
                    toggleConnection(directions[i],false);
                }
            }
        }
    }

    public int getTimer()
    {
        return timer;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord,yCoord,zCoord,xCoord+1,yCoord+1,zCoord+1);
    }
}
