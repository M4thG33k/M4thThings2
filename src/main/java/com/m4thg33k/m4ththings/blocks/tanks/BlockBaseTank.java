package com.m4thg33k.m4ththings.blocks.tanks;

import cofh.api.block.IDismantleable;
import cofh.api.item.IToolHammer;
import com.m4thg33k.m4ththings.helpers.ChatHelper;
import com.m4thg33k.m4ththings.helpers.NameHelper;
import com.m4thg33k.m4ththings.tiles.tanks.TileBaseTank;
import com.m4thg33k.m4ththings.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import java.util.ArrayList;

public class BlockBaseTank extends Block implements ITileEntityProvider, IDismantleable {

    public BlockBaseTank(Material material)
    {
        super (material);
        setHardness(2.0F);
        setResistance(5.0F);
        setBlockName(NameHelper.blockItemName("blockBaseTank"));
        setBlockTextureName(NameHelper.textureName("blank"));
        setStepSound(Blocks.glass.stepSound);
        setHarvestLevel("pickaxe",2);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileBaseTank();
    }

    @Override
    public boolean isBlockNormalCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return Blocks.glass.getIcon(0,0);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        this.setBlockBounds(0.333f,0,0.333f,0.667f,1,0.667f);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        if (tileEntity != null && tileEntity instanceof TileBaseTank)
        {
            TileBaseTank tank = (TileBaseTank)tileEntity;

            NBTTagCompound tagCompound = new NBTTagCompound();
            tank.writeAllButLocationToNBT(tagCompound);

            ItemStack toReturn = new ItemStack(world.getBlock(x,y,z),1,metadata);

            if (!tagCompound.hasKey("Empty"))
            {
                toReturn.setTagCompound(tagCompound);
                toReturn.getTagCompound().setInteger("Mode",0);
            }

            ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
            stacks.add(toReturn);
            return stacks;
        }
        return super.getDrops(world, x, y, z, metadata, fortune);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(world, player, x, y, z, false);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
        super.harvestBlock(world, player, x, y, z, meta);
        world.setBlockToAir(x,y,z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        //LogHelper.info("Block placed at: " + StringHelper.coordinates(x,y,z));

        if (tileEntity!=null && tileEntity instanceof TileBaseTank && stack.hasTagCompound())
        {
            tileEntity.readFromNBT(stack.getTagCompound());
        }

        //if the block above this one is another BlockBaseTank, we should set that one to drain mode
        TileEntity tEnt = world.getTileEntity(x,y+1,z);
        if (tEnt!=null && tEnt instanceof TileBaseTank)
        {
            ((TileBaseTank)tEnt).setMode(1);
        }

        //if the block below the placed one is another BlockBaseTank, we should automatically set this tank to drain mode
        Block below = world.getBlock(x,y-1,z);
        if (below!=null && below instanceof BlockBaseTank && tileEntity!=null && tileEntity instanceof TileBaseTank)
        {
            ((TileBaseTank)tileEntity).setMode(1);
        }
    }

    //IDismantleable

    @Override
    public ArrayList<ItemStack> dismantleBlock(EntityPlayer entityPlayer, World world, int x, int y, int z, boolean b) {

        ArrayList<ItemStack> toReturn = getDrops(world,x,y,z,0,0);

        world.removeTileEntity(x,y,z);
        world.setBlockToAir(x,y,z);

        for (ItemStack itemStack : toReturn)
        {
            EntityItem entityItem = new EntityItem(world, (float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f, itemStack);
            float f = 0.05F;
            entityItem.motionX = (double)((float)world.rand.nextGaussian() * f);
            entityItem.motionY = (double)((float)world.rand.nextGaussian() * f + 0.2F);
            entityItem.motionZ = (double)((float)world.rand.nextGaussian() * f);

            world.spawnEntityInWorld(entityItem);
        }

        return toReturn;
    }

    @Override
    public boolean canDismantle(EntityPlayer entityPlayer, World world, int i, int i1, int i2) {
        return true;
    }

    //END IDismantleable


    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote)
        {
            return true;
        }

        ItemStack held = player.getHeldItem();
        FluidStack fluidHeld = FluidContainerRegistry.getFluidForFilledItem(held);
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        //debugging
//        if (held!=null)
//        {
//            LogHelper.info("Using item: " + held.getItem().getUnlocalizedName());
//        }
        //end debugging
        if (held!=null && held.stackSize==1 && held.getItem() instanceof IFluidContainerItem && tileEntity!=null && (tileEntity instanceof  TileBaseTank))
        {
            IFluidContainerItem fluidItem = (IFluidContainerItem)held.getItem();
            if (((TileBaseTank)tileEntity).fill(ForgeDirection.UNKNOWN,fluidItem.getFluid(held),false) >= 1000)
            {
                ((TileBaseTank)tileEntity).fill(ForgeDirection.UNKNOWN,new FluidStack(fluidItem.getFluid(held),1000),true);
                fluidItem.drain(held,1000,true);
                return true;
            }
            else
            {
                FluidStack inTank = ((TileBaseTank)tileEntity).drain(ForgeDirection.UNKNOWN,1000,false);
                if ((fluidItem.getFluid(held)==null || fluidItem.getFluid(held).amount==0) && fluidItem.getCapacity(held) >= 1000 && inTank!=null && inTank.amount==1000)
                {
                    fluidItem.fill(held,((TileBaseTank)tileEntity).drain(ForgeDirection.UNKNOWN,1000,true),true);
                    return true;
                }
            }
            return true;
        }

        if (fluidHeld != null && tileEntity != null && tileEntity instanceof TileBaseTank)
        {
            /*
            LogHelper.info("Entering check for held fluids");
            LogHelper.info(fluidHeld.getFluid().getName());
            LogHelper.info(fluidHeld.amount);
            LogHelper.info(held.getDisplayName());
            LogHelper.info("Is bucket: " + FluidContainerRegistry.isBucket(held));
            LogHelper.info(((TileBaseTank)tileEntity).fill(ForgeDirection.UNKNOWN,fluidHeld,false));
            LogHelper.info("Is creative: " + player.capabilities.isCreativeMode);*/

            TileBaseTank tank = (TileBaseTank)tileEntity;
            if (tank.fill(ForgeDirection.UNKNOWN,fluidHeld,false)==fluidHeld.amount)
            {
               tank.fill(ForgeDirection.UNKNOWN,fluidHeld,true);
                //FluidContainerRegistry.drainFluidContainer(held);
                if (!player.capabilities.isCreativeMode && FluidContainerRegistry.isBucket(held))
                {
                    int heldLocation = player.inventory.currentItem;
                    player.inventory.decrStackSize(heldLocation,1);
                    world.spawnEntityInWorld(new EntityItem(world,player.posX,player.posY,player.posZ, new ItemStack(Items.bucket,1)));
                }
                //else CRY
            }
            return true;
        }

        if (held!=null && held.getItem() == Items.bucket && tileEntity instanceof TileBaseTank)
        {
            TileBaseTank tank = (TileBaseTank)tileEntity;
            if (tank.getPercentFilled() == 0)
            {
                return false;
            }
            if (tank.drain(ForgeDirection.UNKNOWN,1000,false).amount != 1000)
            {
                return true;
            }
            FluidStack toFill = tank.drain(ForgeDirection.UNKNOWN,1000,true);
            if (!player.capabilities.isCreativeMode) {
                ItemStack filledBucket = FluidContainerRegistry.fillFluidContainer(toFill, new ItemStack(Items.bucket, 1));
                player.inventory.decrStackSize(player.inventory.currentItem, 1);
                world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, filledBucket));
            }
            return true;
        }

        if (held!=null && held.getItem() instanceof IToolHammer && ((IToolHammer)held.getItem()).isUsable(held,player,x,y,z) && !player.isSneaking() && tileEntity instanceof TileBaseTank)
        {
            ((TileBaseTank)tileEntity).switchMode(this.amAdvanced());
            int mode = ((TileBaseTank)tileEntity).getMode();

            displayMode(world,player,mode);
            return true;
        }

        if (held==null && tileEntity instanceof TileBaseTank)
        {
            int mode;
            if (player.isSneaking())
            {
                mode = ((TileBaseTank)tileEntity).switchMode(false);
            }
            else
            {
                mode = ((TileBaseTank)tileEntity).getMode();
            }
            displayMode(world,player,mode);
            return true;
        }



        return false;
    }

    public void displayMode(World world, EntityPlayer player, int mode)
    {
        String message;
        switch(mode)
        {
            case 1:
                message = "drain";
                break;
            case 2:
                message = "place";
                break;
            default:
                message = "default";
        }
        ChatHelper.sayMessage(world,player,"Currently in " + message + " mode");
    }

    protected boolean amAdvanced()
    {
        return false;
    }

    @Override
    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
        return true;
    }
}



















