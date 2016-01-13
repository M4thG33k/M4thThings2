package com.m4thg33k.m4ththings.utility;

import com.m4thg33k.m4ththings.renderers.RendererHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;

public class M4thEventHandler {

    @SubscribeEvent
    public void makeMap(TextureStitchEvent.Post event) throws Exception
    {
        RendererHelper.postTextureStitch(event);
    }
}
