package com.m4thg33k.m4ththings.tiles;

import com.m4thg33k.m4ththings.M4thThings;
import com.m4thg33k.m4ththings.interfaces.IM4thNBTSync;
import com.m4thg33k.m4ththings.interfaces.ITransportBlock;
import com.m4thg33k.m4ththings.packets.ModPackets;
import com.m4thg33k.m4ththings.packets.PacketNBT;
import com.m4thg33k.m4ththings.packets.PacketSpline;
import com.m4thg33k.m4ththings.utility.BasicTools;
import com.m4thg33k.m4ththings.utility.CubicSplineCreation;
import com.m4thg33k.m4ththings.utility.LocVec;
import com.m4thg33k.m4ththings.utility.LogHelper;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.Stack;
import java.util.Vector;

public class TileFluidTransportEntry extends TileFluidHandler implements IM4thNBTSync,ITransportBlock {

    private boolean[] connections = new boolean[6];
    private int attachedSide;
    ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
    private int timer;
    private int randomocity;

    public TileFluidTransportEntry()
    {
        super();
        attachedSide = ForgeDirection.DOWN.ordinal();
        tank = new FluidTank(8000);
        timer = 0;
        randomocity = 0;
    }

    public TileFluidTransportEntry(int meta)
    {
        super();
        attachedSide = ForgeDirection.VALID_DIRECTIONS[meta].ordinal();
        tank = new FluidTank(8000);
        timer = 0;
        randomocity = 0;
    }

    @Override
    public void updateEntity() {
        timer = (timer+1)%5;
        randomocity = (randomocity+1)%6;

        if (!worldObj.isRemote && timer==0)
        {
            attemptFill();
            attemptPlace();
        }
    }

    public void attemptFill()
    {
        TileEntity tileEntity = BasicTools.getTEOnSide(worldObj,xCoord,yCoord,zCoord,directions[attachedSide]);
        if (tileEntity!=null && tileEntity instanceof IFluidHandler)
        {
            if (fill(directions[attachedSide],((IFluidHandler) tileEntity).drain(directions[attachedSide].getOpposite(),100,false),false)>0)
            {
                fill(directions[attachedSide],((IFluidHandler) tileEntity).drain(directions[attachedSide].getOpposite(),100,true),true);
            }
        }
    }

    public void attemptPlace()
    {
        if (tank.getFluidAmount()==0)
        {
            return;
        }
        //int randomocity = M4thThings.random.nextInt(6);
        Stack<LocVec> stack = findPath();
        if (stack.size()<2)
        {
            return;
        }
        TileEntity tileEntity = BasicTools.getTEAtRelLoc(worldObj,xCoord,yCoord,zCoord,stack.lastElement());
        ForgeDirection fillDir = ((stack.elementAt(stack.size()-2)).difference(stack.lastElement())).direction();
        ForgeDirection drainDir = ((stack.elementAt(1)).difference(stack.firstElement())).direction();
        FluidStack drained;
        if (tileEntity!=null && tileEntity instanceof IFluidHandler)
        {
            drained = drain(drainDir,((IFluidHandler) tileEntity).fill(fillDir,tank.getFluid(),true),true);
            if (drained != null && drained.amount>0)
            {
                LocVec init = new LocVec(xCoord,yCoord,zCoord);
                ModPackets.INSTANCE.sendToAllAround(new PacketSpline(BasicTools.stackToIntArray(stack),init.getLoc(),attachedSide), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));

                //check to make sure each step in our path is adjacent. remove this line before releasing
                LogHelper.info("Checking my work...");
                checkWork(stack);



                //CubicSplineCreation cubicSpline = new CubicSplineCreation(stack,attachedSide,new LocVec(xCoord,yCoord,zCoord));
                //Vec3[] particleLocations = cubicSpline.allLocations(0.5);
                //ModPackets.INSTANCE.sendToAllAround(new PacketSpline(BasicTools.Vec3ArrayToString(particleLocations)), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("AttachedSide"))
        {
            attachedSide = tag.getInteger("AttachedSide");
        }
        if (tag.hasKey("Connections"))
        {
            connections = BasicTools.intToBoolArray(tag.getIntArray("Connections"));
        }
        if (tag.hasKey("Timer"))
        {
            timer = tag.getInteger("Timer");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("AttachedSide",attachedSide);
        tag.setIntArray("Connections",BasicTools.boolToIntArray(connections));
        tag.setInteger("Timer",timer);
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
    //END IM4thNBTSync

    //ITransportBlock
    @Override
    public boolean isSideConnected(ForgeDirection side) {
        return side.ordinal()<6 && connections[side.ordinal()];
    }

    @Override
    public void setConnection(ForgeDirection side, boolean makeThis, boolean attemptNeighbor) {
        if (worldObj.isRemote || side.ordinal()>6 || side.ordinal()==attachedSide || connections[side.ordinal()]==makeThis)
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
    public void attemptToSetNeighborConnection(ForgeDirection side, boolean makeThis) {
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
    //END ITransportBlock

    public int getAttachedSide()
    {
        return attachedSide;
    }

    public void connectToAllTransportBlocks()
    {
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;
        TileEntity tileEntity;
        for (int i=0;i<6;i++)
        {
            tileEntity = BasicTools.getTEOnSide(worldObj, xCoord, yCoord, zCoord, directions[i]);
            if (tileEntity != null && tileEntity instanceof ITransportBlock) {
                setConnection(directions[i], true, true);
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

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource==null)
        {
            return 0;
        }
        if (canFill(from,resource.getFluid())) {
            return super.fill(from, resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (canDrain(from,resource.getFluid())) {
            return super.drain(from, resource, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (canDrain(from,null)) {
            return super.drain(from, maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return ForgeDirection.VALID_DIRECTIONS[attachedSide]==from;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return ForgeDirection.VALID_DIRECTIONS[attachedSide]!=from;
    }

    /*checks to see if the given side leads to a valid connection
        if ANS is the return value for this method,
        ANS&1==1 if the side is connected
        ANS&2==2 if the side is connected to an IFluidHandler
        ANS&4==4 if the side is connected to an ITransportBlock
     */
    @Override
    public int isValidFluidConnection(ForgeDirection side,FluidStack fluidStack)
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

    //Begin fluid routing code

    public Stack<LocVec> findPath()
    {
        //Vector<LocVec> visited = new Vector<LocVec>();
        //visited.add(new LocVec(0,0,0));
//
        //LocVec currentLocation = new LocVec(0,0,0);

        ITransportBlock transportBlock;
        int index;
        int validate;
        int currentLevel;
        LocVec searchLocation;
        Vector<LocVec> visited = new Vector<LocVec>();
        Stack<LocVec> stack = new Stack<LocVec>();
        Stack<Integer> level = new Stack<Integer>();
        Stack<LocVec> path = new Stack<LocVec>();
        LocVec currentLocation = new LocVec(0,0,0);
        stack.push(currentLocation);
        level.push(0);
        while (stack.size()!=0)
        {
            currentLocation = stack.pop();
            currentLevel = level.pop();
            while (path.size()>currentLevel)
            {
                path.pop();
            }
            if (!visited.contains(currentLocation)) {
                visited.add(currentLocation);
                path.push(currentLocation);
                transportBlock = (ITransportBlock) BasicTools.getTEAtRelLoc(worldObj, xCoord, yCoord, zCoord, currentLocation);

                for (int i = 0; i < 6; i++) {
                    index = i;//(i+randomocity)%6;
                    searchLocation = currentLocation.copy();
                    searchLocation.addVec(directions[index]);
                    if (visited.contains(searchLocation)) //if we've already been here, skip the rest of the calculations
                    {
                        continue;
                    }
                    validate = transportBlock.isValidFluidConnection(directions[index], tank.getFluid());
                    if ((validate & 1) == 1) //we have a valid connection
                    {
                        if ((validate & 2) != 2) //we are not connecting to an IFluidHandler
                        {
                            stack.push(searchLocation);
                            level.push(path.size());
                        }
                        else
                        {
                            path.push(searchLocation);
                            return path;
                        }
                    }
                }
            }
            else
            {
                path.pop();
            }
        }
        return path;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord,yCoord,zCoord,xCoord+1,yCoord+1,zCoord+1);
    }

    private void checkWork(Stack<LocVec> stack)
    {
        int norm;
        for (int i=0;i<stack.size()-1;i++)
        {
            norm = (stack.elementAt(i+1).difference(stack.elementAt(i))).oneNorm();
            if (norm!=1)
            {
                LogHelper.info("Something ain't right...");
            }
        }
    }
}
