package com.m4thg33k.m4ththings.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class PacketSpline implements IMessage {

    private int[] data;
    private int[] init;
    private int attachedSide;
    private String fluidName;

    public PacketSpline()
    {}

    public PacketSpline(int[] locs,int[] initial,int side,String fName)
    {
        data = locs;
        init = initial;
        attachedSide = side;
        fluidName = fName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        NBTTagCompound tagCompound = ByteBufUtils.readTag(buf);
        data = tagCompound.getIntArray("data");
        init = tagCompound.getIntArray("init");
        attachedSide = tagCompound.getInteger("side");
        fluidName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setIntArray("data",data);
        tagCompound.setIntArray("init",init);
        tagCompound.setInteger("side",attachedSide);
        ByteBufUtils.writeTag(buf,tagCompound);
        ByteBufUtils.writeUTF8String(buf,fluidName);
    }

    public int[] getData()
    {
        return data;
    }

    public int[] getInit()
    {
        return init;
    }

    public int getAttachedSide()
    {
        return attachedSide;
    }

    public String getFluidName()
    {
        return fluidName;
    }
}
