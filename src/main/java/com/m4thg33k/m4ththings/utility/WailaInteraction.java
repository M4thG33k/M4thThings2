package com.m4thg33k.m4ththings.utility;

import com.m4thg33k.m4ththings.tiles.tanks.TileAdvancedTank;
import com.m4thg33k.m4ththings.tiles.tanks.TileBaseTank;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class WailaInteraction implements IWailaDataProvider {

    public static final WailaInteraction INSTANCE = new WailaInteraction();

    public static void load(IWailaRegistrar registrar)
    {
        LogHelper.info("I'm a-firin' my laser!");
        registrar.registerBodyProvider(INSTANCE, TileBaseTank.class);
        registrar.registerNBTProvider(INSTANCE, TileBaseTank.class);
        //registrar.registerBodyProvider(INSTANCE, TileAdvancedTank.class);

        registrar.addConfig("M4thThings","option.m4ththings.showTankStorage");
        registrar.addConfig("M4thThings","option.m4ththings.showOnSneak");
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return list;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler handler) {
        TileEntity te = accessor.getTileEntity();

        if (te == null)
        {
            return currentTip;
        }

        if (te instanceof TileBaseTank && handler.getConfig("option.m4ththings.showTankStorage") && accessor.getPlayer().isSneaking()==handler.getConfig("option.m4ththings.showOnSneak"))
        {
            int amount = ((TileBaseTank)te).getAmount();
            if (amount==0)
            {
                currentTip.add("EMPTY");
            }
            else
            {
                currentTip.add(((TileBaseTank)te).getFluid().getLocalizedName() + ":" + amount + "/" + ((TileBaseTank)te).getCap());
                currentTip.add(((TileBaseTank)te).getRoundedPercentFilled() + "%");
            }

            switch (((TileBaseTank)te).getMode())
            {
                case 1:
                    currentTip.add("Drain mode");
                    break;
                case 2:
                    currentTip.add("Place mode");
                    break;
                default:
            }
        }
        return currentTip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return list;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP entityPlayerMP, TileEntity tileEntity, NBTTagCompound nbtTagCompound, World world, int i, int i1, int i2) {
        if (tileEntity!=null)
        {
            tileEntity.writeToNBT(nbtTagCompound);
        }

        return nbtTagCompound;
    }
}
