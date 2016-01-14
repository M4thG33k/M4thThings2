package com.m4thg33k.m4ththings.utility;

import com.m4thg33k.m4ththings.players.M4thExtendedPlayer;
import com.m4thg33k.m4ththings.renderers.RendererHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.EntityEvent;

public class M4thEventHandler {

    @SubscribeEvent
    public void makeMap(TextureStitchEvent.Post event) throws Exception
    {
        RendererHelper.postTextureStitch(event);
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event)
    {
        if (event.entity instanceof EntityPlayer && M4thExtendedPlayer.get((EntityPlayer)event.entity)==null)
        {
            M4thExtendedPlayer.register((EntityPlayer)event.entity);
        }
    }
}
