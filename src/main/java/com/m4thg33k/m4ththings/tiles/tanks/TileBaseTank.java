package com.m4thg33k.m4ththings.tiles.tanks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileBaseTank extends TileFluidHandler{

    protected int timer;
    protected int cap;
    protected int orientation;
    protected int tankSize;

    public TileBaseTank()
    {
        super();
        cap = 8000;
        tankSize = 0;
        timer = 0;
        tank = new FluidTank(cap);
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
    }

    public void setEmpty()
    {
        tank.setFluid(null);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (canFill(from,resource.getFluid())){
            return super.fill(from, resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (canDrain(from,resource.getFluid()))
        {
            return super.drain(from, resource, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (canDrain(from,null))
        {
            return super.drain(from, maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return (from == ForgeDirection.DOWN || from == ForgeDirection.UP);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return (from == ForgeDirection.DOWN || from == ForgeDirection.UP);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setInteger("Timer",timer);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        if (tagCompound.hasKey("Timer"))
        {
            timer = tagCompound.getInteger("Timer");
        }
    }

    public int getTimer()
    {
        return timer;
    }
}
