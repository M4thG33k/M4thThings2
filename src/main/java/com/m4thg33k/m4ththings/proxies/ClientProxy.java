package com.m4thg33k.m4ththings.proxies;

import com.m4thg33k.m4ththings.init.ModRenderers;
import com.m4thg33k.m4ththings.interfaces.IM4thNBTSync;
import com.m4thg33k.m4ththings.packets.PacketFilling;
import com.m4thg33k.m4ththings.packets.PacketNBT;
import com.m4thg33k.m4ththings.particles.ParticleManager;
import com.m4thg33k.m4ththings.tiles.tanks.TileBaseTank;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        super.registerRenderers();

        ModRenderers.init();
    }

    @Override
    public void handleNBTPacket(PacketNBT message) {
        if (message == null)
        {
            return;
        }

        TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(message.x,message.y,message.z);

        if (tileEntity != null && tileEntity instanceof IM4thNBTSync)
        {
            ((IM4thNBTSync)tileEntity).receiveNBTPacket(message.tagCompound);
        }
    }

    @Override
    public void startParticleRendering(PacketFilling message) {
        World world = Minecraft.getMinecraft().theWorld;

        TileEntity tileEntity = world.getTileEntity(message.getX(),message.getY(),message.getZ());

        if (tileEntity!=null && tileEntity instanceof TileBaseTank)
        {
            ParticleManager.tankFillParticles(world,message.getX(),message.getY(),message.getZ(),message.getDirection(),message.getIsFilling(), FluidRegistry.getFluid(message.getFluidName()),message.getAmount(),message.getSize());
        }
    }
}
