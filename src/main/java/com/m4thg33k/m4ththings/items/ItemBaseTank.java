package com.m4thg33k.m4ththings.items;

import com.m4thg33k.m4ththings.tiles.tanks.TileBaseTank;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import java.util.List;

public class ItemBaseTank extends ItemBlock implements IFluidContainerItem{

    protected int capacity;

    public ItemBaseTank(Block block){
        super(block);
        capacity = 8000;
        setMaxStackSize(16);
    }

    public ItemBaseTank setCapacity(int capacity)
    {
        this.capacity = capacity;
        return this;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName();
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("FluidName"))
        {
            NBTTagCompound tagCompound = itemStack.getTagCompound();

            list.add(FluidRegistry.getFluid(tagCompound.getString("FluidName")).getLocalizedName(null));
            list.add(tagCompound.getInteger("Amount") + "/" + "8000mb");
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        boolean supers = super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);

        if (!supers)
        {
            return false;
        }

//        TileEntity tileEntity = world.getTileEntity(x,y,z);
//
//        if (tileEntity != null && tileEntity instanceof TileBaseTank && stack.hasTagCompound())
//        {
//            tileEntity.readFromNBT(stack.getTagCompound());
//        }

        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int p_77663_4_, boolean p_77663_5_) {
        if (stack.hasTagCompound() && !stack.getTagCompound().hasKey("Empty"))
        {
            stack.getTagCompound().setInteger("Timer",(stack.getTagCompound().getInteger("Timer")+1)%360);
        }
    }



    //IFluidContainerItem (lovingly borrowed from ItemFluidContainer)


    @Override
    public FluidStack getFluid(ItemStack container) {
        if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("FluidName"))
        {
            return null;
        }
        return FluidStack.loadFluidStackFromNBT(container.stackTagCompound);
    }

    @Override
    public int getCapacity(ItemStack container) {
        return capacity;
    }

    @Override
    public int fill(ItemStack container, FluidStack resource, boolean doFill) {
        if (resource==null || resource.amount==0)
        {
            return 0;
        }


        NBTTagCompound inputTag = resource.writeToNBT(new NBTTagCompound());

        if (!doFill)
        {
            if (container.stackTagCompound == null || container.stackTagCompound.hasKey("Empty"))
            {
                return Math.min(capacity,resource.amount);
            }

            //at this point, we know there is a stackTagCompound and the tank is non-empty ==> it has the key FluidName
            if (!container.stackTagCompound.getString("FluidName").equals(inputTag.getString("FluidName")))
            {
                return 0;
            }

            int filling = capacity - container.stackTagCompound.getInteger("Amount");
            return Math.min(filling,resource.amount);
        }


        if (container.stackTagCompound == null || container.stackTagCompound.hasKey("Empty"))
        {
            if (container.stackTagCompound==null)
            {
                container.stackTagCompound = new NBTTagCompound();
            }
            if (container.stackTagCompound.hasKey("Empty"))
            {
                container.stackTagCompound.removeTag("Empty");
            }
            container.stackTagCompound.setString("FluidName",inputTag.getString("FluidName"));
            container.stackTagCompound.setInteger("Amount",Math.min(capacity,resource.amount));
            return Math.min(capacity,resource.amount);
        }

        if (!container.stackTagCompound.getString("FluidName").equals(inputTag.getString("FluidName")))
        {
            return 0;
        }

        int filling = capacity-container.stackTagCompound.getInteger("Amount");
        if (resource.amount <= filling)
        {
            container.stackTagCompound.setInteger("Amount",container.stackTagCompound.getInteger("Amount")+resource.amount);
            return Math.min(filling,resource.amount);
        }

        container.stackTagCompound.setInteger("Amount",capacity);
        return filling;
    }

    @Override
    public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain)
    {
        if (container.stackTagCompound == null || container.stackTagCompound.hasKey("Empty"))
        {
            return null;
        }

        FluidStack toReturn = FluidStack.loadFluidStackFromNBT(container.stackTagCompound);
        if (toReturn==null)
        {
            return null;
        }

        int currentAmount = toReturn.amount;
        toReturn.amount = Math.min(currentAmount,maxDrain); //now a FluidStack with the correct fluid type and amount
        if (doDrain)
        {
            if (currentAmount == toReturn.amount)
            {
                container.stackTagCompound.removeTag("FluidName");
                container.stackTagCompound.removeTag("Amount");
                container.stackTagCompound.setString("Empty","");

                return toReturn;
            }

            container.stackTagCompound.setInteger("Amount",currentAmount-toReturn.amount);
        }
        return toReturn;

    }
}
