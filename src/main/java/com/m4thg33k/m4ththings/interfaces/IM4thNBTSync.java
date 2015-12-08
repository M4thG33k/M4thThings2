package com.m4thg33k.m4ththings.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface IM4thNBTSync {

    void receiveNBTPacket(NBTTagCompound tagCompound);

    void prepareSync();
}
