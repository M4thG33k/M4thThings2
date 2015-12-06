package com.m4thg33k.m4ththings.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class PacketNBT implements IMessage {

    public NBTTagCompound tagCompound;
    public int x;
    public int y;
    public int z;

    public PacketNBT()
    {

    }

    public PacketNBT(int x, int y, int z,NBTTagCompound tagCompound)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.tagCompound = tagCompound;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = ByteBufUtils.readVarInt(buf,5);
        y = ByteBufUtils.readVarInt(buf,5);
        z = ByteBufUtils.readVarInt(buf,5);
        tagCompound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarInt(buf,x,5);
        ByteBufUtils.writeVarInt(buf,y,5);
        ByteBufUtils.writeVarInt(buf,z,5);
        ByteBufUtils.writeTag(buf,tagCompound);
    }
}
