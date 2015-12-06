package com.m4thg33k.m4ththings.packets;

import com.m4thg33k.m4ththings.M4thThings;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketHandlerFilling implements IMessageHandler<PacketFilling,IMessage>{

    @Override
    public IMessage onMessage(PacketFilling message, MessageContext ctx) {
        M4thThings.proxy.startParticleRendering(message);
        return null;
    }
}
