package com.m4thg33k.m4ththings.packets;

import com.m4thg33k.m4ththings.M4thThings;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketHandlerNBT implements IMessageHandler<PacketNBT, IMessage> {

    @Override
    public IMessage onMessage(PacketNBT message, MessageContext ctx) {

        M4thThings.proxy.handleNBTPacket(message);

        return null;
    }
}
