package com.m4thg33k.m4ththings.packets;

import com.m4thg33k.m4ththings.M4thThings;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketHandlerSpline implements IMessageHandler<PacketSpline,IMessage> {

    @Override
    public IMessage onMessage(PacketSpline message, MessageContext ctx) {
        M4thThings.proxy.startSplineRendering(message);
        return null;
    }
}
