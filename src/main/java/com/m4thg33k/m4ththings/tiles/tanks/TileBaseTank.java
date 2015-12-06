package com.m4thg33k.m4ththings.tiles.tanks;

import com.m4thg33k.m4ththings.helpers.StringHelper;
import com.m4thg33k.m4ththings.interfaces.IM4thNBTSync;
import com.m4thg33k.m4ththings.packets.ModPackets;
import com.m4thg33k.m4ththings.packets.PacketFilling;
import com.m4thg33k.m4ththings.packets.PacketNBT;
import com.m4thg33k.m4ththings.utility.LogHelper;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileBaseTank extends TileFluidHandler implements IM4thNBTSync{

    protected int timer;
    protected int cap;
    protected int orientation;
    protected int tankSize;
    protected int mode;
    protected int numModes = 3;

    public TileBaseTank()
    {
        super();
        cap = 8000;
        tankSize = 0;
        timer = 0;
        tank = new FluidTank(cap);
        mode = 0;

    }

    public void advanceTimer()
    {
        timer = (timer+1)%360;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        advanceTimer();

        if (tank.getFluidAmount()>cap)
        {
            tank.setFluid(new FluidStack(tank.getFluid().getFluid(),cap));
            //prepareSync(); ???
        }

        if (mode==1) //auto-push mode
        {
            attemptPush();
        }
        if (mode==2 && (timer%10)==0) //auto-place mode
        {
            attemptPlacement();
        }

        prepareSync();
    }

    public void setEmpty()
    {
        tank.setFluid(null);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource==null || resource.amount==0)
        {
            return 0;
        }
        if (canFill(from,resource.getFluid())){
            int toReturn = super.fill(from, resource, doFill);
            if (doFill)
            {
                ModPackets.INSTANCE.sendToAllAround(new PacketFilling(xCoord,yCoord,zCoord,from.ordinal(),1,FluidRegistry.getFluidName(resource),toReturn,tankSize),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
            }
            if (toReturn != resource.amount && from!=ForgeDirection.UP) //if we have leftovers, try to push them into a valid tank above
            {
                TileEntity tileEntity = worldObj.getTileEntity(xCoord,yCoord+1,zCoord);
                int pushing = 0;
                if (tileEntity!=null && tileEntity instanceof TileBaseTank && ((TileBaseTank)tileEntity).getMode()==1)
                {
                    pushing = ((TileBaseTank)tileEntity).fill(ForgeDirection.DOWN,new FluidStack(resource,resource.amount-toReturn),false);
                }
                if (pushing >0)
                {
                    ModPackets.INSTANCE.sendToAllAround(new PacketFilling(xCoord,yCoord,zCoord,ForgeDirection.UP.ordinal(),0,FluidRegistry.getFluidName(resource),toReturn,tankSize),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
                    toReturn += ((TileBaseTank)tileEntity).fill(ForgeDirection.DOWN,new FluidStack(resource,resource.amount-toReturn),doFill);
                }
            }
            return toReturn;
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || resource.amount==0)
        {
            return null;
        }
        if (canDrain(from,resource.getFluid()))
        {
            FluidStack toReturn = super.drain(from, resource, doDrain);
            if (doDrain)
            {
                ModPackets.INSTANCE.sendToAllAround(new PacketFilling(xCoord,yCoord,zCoord,from.ordinal(),0,FluidRegistry.getFluidName(resource),toReturn.amount,tankSize),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
            }
            return toReturn;
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (canDrain(from,null))
        {
            FluidStack toReturn = super.drain(from, maxDrain, doDrain);
            if (doDrain)
            {
                ModPackets.INSTANCE.sendToAllAround(new PacketFilling(xCoord,yCoord,zCoord,from.ordinal(),0,FluidRegistry.getFluidName(toReturn),toReturn.amount,tankSize),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
            }
            return toReturn;
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return (from == ForgeDirection.DOWN || from == ForgeDirection.UP || from == ForgeDirection.UNKNOWN);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return (from == ForgeDirection.DOWN || from == ForgeDirection.UP || from == ForgeDirection.UNKNOWN);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setInteger("Timer",timer);
        tagCompound.setInteger("Mode",mode);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
       if (tagCompound.hasKey("x"))
       {
           super.readFromNBT(tagCompound);
       }
        else
       {
           tank.readFromNBT(tagCompound);
       }

        if (tagCompound.hasKey("Timer"))
        {
            timer = tagCompound.getInteger("Timer");
        }

        if (tagCompound.hasKey("Mode"))
        {
            mode = tagCompound.getInteger("Mode");
        }
    }

    public int getTimer()
    {
        return timer;
    }

    @Override
    public void receiveNBTPacket(NBTTagCompound tagCompound) {
        this.readFromNBT(tagCompound);
    }

    public void prepareSync() {
        if (!worldObj.isRemote)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            this.writeToNBT(tagCompound);
            ModPackets.INSTANCE.sendToAllAround(new PacketNBT(xCoord,yCoord,zCoord,tagCompound),new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,32));
        }
    }

    public double getPercentFilled()
    {
        return (1.0*tank.getFluidAmount())/tank.getCapacity();
    }

    //attempts to push fluids in the tank to a fluid handler beneath it
    public void attemptPush()
    {

        if (tank.getFluidAmount()==0)
        {
            return;
        }

        TileEntity tileEntity = worldObj.getTileEntity(xCoord,yCoord-1,zCoord);
        if (!(tileEntity instanceof IFluidHandler) || !((IFluidHandler)tileEntity).canFill(ForgeDirection.UP,tank.getFluid().getFluid()))
        {
            return;
        }

        int validAmount = ((IFluidHandler)tileEntity).fill(ForgeDirection.UP,new FluidStack(tank.getFluid().getFluid(),Math.min(tank.getFluidAmount(),(int)(tank.getCapacity()*0.25))),false);
        if (validAmount>0)
        {
            ((IFluidHandler)tileEntity).fill(ForgeDirection.UP,new FluidStack(tank.getFluid().getFluid(),Math.min(tank.getFluidAmount(),(int)(tank.getCapacity()*0.25))),true);
            this.drain(ForgeDirection.DOWN,validAmount,true);
        }
    }

    public int switchMode(boolean allowAdvanced)
    {
        //LogHelper.info("Switching mode at: " + StringHelper.coordinates(xCoord,yCoord,zCoord));
        if (allowAdvanced)
        {
            mode = (mode+1)%numModes;
        }
        else{
            mode = (mode+1)%2;
        }
        return mode;
    }

    public int getMode()
    {
        return mode;
    }

    public void attemptPlacement()
    {
        if (worldObj.isRemote || tank.getFluidAmount()<1000 || worldObj.getBlock(xCoord,yCoord-1,zCoord)!= Blocks.air || !tank.getFluid().getFluid().canBePlacedInWorld())
        {
            return;
        }
        Block toPlace = tank.getFluid().getFluid().getBlock();
        worldObj.setBlock(xCoord,yCoord-1,zCoord,toPlace);
        worldObj.getBlock(xCoord,yCoord-1,zCoord).onNeighborBlockChange(worldObj,xCoord,yCoord-1,zCoord,null);
        drain(ForgeDirection.DOWN,1000,true);


    }

    public void setFluid(FluidStack fluidStack)
    {
        tank.setFluid(fluidStack);
    }

    public void setTimer(int t)
    {
        timer = t;
    }

    public void readTankFromNBT(NBTTagCompound tagCompound)
    {
        tank.readFromNBT(tagCompound);
    }

    public void writeAllButLocationToNBT(NBTTagCompound tagCompound)
    {
        tank.writeToNBT(tagCompound);

        tagCompound.setInteger("Timer",timer);
        tagCompound.setInteger("Mode",mode);
    }
}
