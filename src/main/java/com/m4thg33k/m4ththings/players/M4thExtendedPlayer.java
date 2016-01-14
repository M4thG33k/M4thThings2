package com.m4thg33k.m4ththings.players;

import com.m4thg33k.m4ththings.utility.LogHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import org.lwjgl.opengl.EXTPackedDepthStencil;

public class M4thExtendedPlayer implements IExtendedEntityProperties {

    public static final String EXT_PROP_NAME = "M4thExtendedPlayer";

    private final EntityPlayer player;
    public static final int PIPE_VISION = 20;

    public M4thExtendedPlayer(EntityPlayer p)
    {
        player = p;
        this.player.getDataWatcher().addObject(PIPE_VISION,1);
    }

    public static final void register(EntityPlayer p)
    {
        p.registerExtendedProperties(EXT_PROP_NAME,new M4thExtendedPlayer(p));
    }

    public  static final M4thExtendedPlayer get(EntityPlayer p)
    {
        return (M4thExtendedPlayer)p.getExtendedProperties(EXT_PROP_NAME);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties =  new NBTTagCompound();

        properties.setInteger("SeesPipes", this.player.getDataWatcher().getWatchableObjectInt(PIPE_VISION));

        compound.setTag(EXT_PROP_NAME,properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
        this.player.getDataWatcher().updateObject(PIPE_VISION,properties.getInteger("SeesPipes"));
    }

    @Override
    public void init(Entity entity, World world) {

    }

    public void toggleSight()
    {
        this.player.getDataWatcher().updateObject(PIPE_VISION,(this.player.getDataWatcher().getWatchableObjectInt(PIPE_VISION)^1));
    }

    public boolean seesPipes()
    {
        return (this.player.getDataWatcher().getWatchableObjectInt(PIPE_VISION)==1);
    }
}
