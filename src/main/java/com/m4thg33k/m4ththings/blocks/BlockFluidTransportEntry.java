package com.m4thg33k.m4ththings.blocks;

import com.m4thg33k.m4ththings.helpers.NameHelper;
import com.m4thg33k.m4ththings.init.ModBlocks;
import com.m4thg33k.m4ththings.init.ModItems;
import com.m4thg33k.m4ththings.tiles.TileFluidTransportEntry;
import com.m4thg33k.m4ththings.tiles.TileTransportBlock;
import com.m4thg33k.m4ththings.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockFluidTransportEntry extends Block implements ITileEntityProvider {

    public BlockFluidTransportEntry(Material material)
    {
        super(material);
        setHardness(0.5f);
        setResistance(0.5f);
        setBlockName(NameHelper.blockItemName("blockFluidTransportEntry"));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        if (meta<0 || meta>=6)
        {
            return new TileFluidTransportEntry();
        }
        return new TileFluidTransportEntry(meta);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        return ForgeDirection.VALID_DIRECTIONS[side].getOpposite().ordinal();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
        //if (entity instanceof EntityPlayer && ((EntityPlayer) entity).getHeldItem() != null && (((EntityPlayer) entity).getHeldItem().getItem() == ModItems.itemWrench || ((EntityPlayer) entity).getHeldItem().getItem() == Item.getItemFromBlock(ModBlocks.blockFluidTransportEntry)))
        //{

        //}
        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if (tileEntity!=null && tileEntity instanceof TileFluidTransportEntry)
        {
            int attached = ((TileFluidTransportEntry) tileEntity).getAttachedSide();
            AxisAlignedBB axisAlignedBB1;
            switch (attached)
            {
                case 1:
                    axisAlignedBB1 = AxisAlignedBB.getBoundingBox(x+0.25f,y+1.0f,z+0.25f,x+0.75f,y+0.95f,z+0.75f);
                    //this.setBlockBounds(0.25f,1.0f,0.25f,0.75f,0.95f,0.75f);
                    break;
                case 2:
                    axisAlignedBB1 = AxisAlignedBB.getBoundingBox(x+0.25f,y+0.25f,z+0.0f,x+0.75f,y+0.75f,z+0.05f);
                    //this.setBlockBounds(0.25f,0.25f,0.0f,0.75f,0.75f,0.05f);
                    break;
                case 3:
                    axisAlignedBB1 = AxisAlignedBB.getBoundingBox(x+0.25f,y+0.25f,z+0.95f,x+0.75f,y+0.75f,z+1.0f);
                    //this.setBlockBounds(0.25f,0.25f,0.95f,0.75f,0.75f,1.0f);
                    break;
                case 4:
                    axisAlignedBB1 = AxisAlignedBB.getBoundingBox(x+0.0f,y+0.25f,z+0.25f,x+0.05f,y+0.75f,z+0.75f);
                    //this.setBlockBounds(0.0f,0.25f,0.25f,0.05f,0.75f,0.75f);
                    break;
                case 5:
                    axisAlignedBB1 = AxisAlignedBB.getBoundingBox(x+0.95f,y+0.25f,z+0.25f,x+1.0f,y+0.75f,z+0.75f);
                    //this.setBlockBounds(0.95f,0.25f,0.25f,1.0f,0.75f,0.75f);
                    break;
                default:
                    axisAlignedBB1 = AxisAlignedBB.getBoundingBox(x+0.25f,y+0.0f,z+0.25f,x+0.75f,y+0.05f,z+0.75f);
                    //this.setBlockBounds(0.25f,0.0f,0.25f,0.75f,0.05f,0.75f);
            }
            if (axisAlignedBB.intersectsWith(axisAlignedBB1))
            {
                list.add(axisAlignedBB1);
            }
        }
        else
        {
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
        }
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        /*
        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if (tileEntity!=null && tileEntity instanceof TileFluidTransportEntry)
        {
            int attached = ((TileFluidTransportEntry) tileEntity).getAttachedSide();
            switch (attached)
            {
                case 1:
                    return AxisAlignedBB.getBoundingBox(0.25f,1.0f,0.25f,0.75f,0.95f,0.75f);
                    //this.setBlockBounds(0.25f,1.0f,0.25f,0.75f,0.95f,0.75f);
                    //break;
                case 2:
                    return AxisAlignedBB.getBoundingBox(0.25f,0.25f,0.0f,0.75f,0.75f,0.05f);
                    //this.setBlockBounds(0.25f,0.25f,0.0f,0.75f,0.75f,0.05f);
                    //break;
                case 3:
                    return AxisAlignedBB.getBoundingBox(0.25f,0.25f,0.95f,0.75f,0.75f,1.0f);
                    //this.setBlockBounds(0.25f,0.25f,0.95f,0.75f,0.75f,1.0f);
                    //break;
                case 4:
                    return AxisAlignedBB.getBoundingBox(0.0f,0.25f,0.25f,0.05f,0.75f,0.75f);
                    //this.setBlockBounds(0.0f,0.25f,0.25f,0.05f,0.75f,0.75f);
                    //break;
                case 5:
                    return AxisAlignedBB.getBoundingBox(0.95f,0.25f,0.25f,1.0f,0.75f,0.75f);
                    //this.setBlockBounds(0.95f,0.25f,0.25f,1.0f,0.75f,0.75f);
                    //break;
                default:
                    return AxisAlignedBB.getBoundingBox(0.25f,0.0f,0.25f,0.75f,0.05f,0.75f);
                    //this.setBlockBounds(0.25f,0.0f,0.25f,0.75f,0.05f,0.75f);
            }
        }*/
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        this.setBlockBounds(0.25f,0.25f,0.25f,0.75f,0.75f,0.75f);
        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if (tileEntity!=null && tileEntity instanceof TileFluidTransportEntry)
        {
            int attached = ((TileFluidTransportEntry) tileEntity).getAttachedSide();
            switch (attached)
            {
                case 1:
                    this.setBlockBounds(0.25f,0.95f,0.25f,0.75f,1.0f,0.75f);
                    break;
                case 2:
                    this.setBlockBounds(0.25f,0.25f,0.0f,0.75f,0.75f,0.05f);
                    break;
                case 3:
                    this.setBlockBounds(0.25f,0.25f,0.95f,0.75f,0.75f,1.0f);
                    break;
                case 4:
                    this.setBlockBounds(0.0f,0.25f,0.25f,0.05f,0.75f,0.75f);
                    break;
                case 5:
                    this.setBlockBounds(0.95f,0.25f,0.25f,1.0f,0.75f,0.75f);
                    break;
                default:
                    this.setBlockBounds(0.25f,0.0f,0.25f,0.75f,0.05f,0.75f);
            }
        }
    }



    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote)
        {
            return true;
        }
        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if (tileEntity != null && tileEntity instanceof TileFluidTransportEntry)
        {
            if (player.getHeldItem() != null) {
                if (player.getHeldItem().getItem() != Items.stick) {
                    ((TileFluidTransportEntry) tileEntity).toggleConnection(ForgeDirection.VALID_DIRECTIONS[side], true);
                }
            }
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack stack) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        if (tileEntity!=null && tileEntity instanceof TileFluidTransportEntry)
        {
            //LogHelper.info("Attempting to connect to neighbors");
            ((TileFluidTransportEntry) tileEntity).connectToAllTransportBlocks();
        }
    }


    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if (tileEntity!=null && tileEntity instanceof TileFluidTransportEntry)
        {
            ((TileFluidTransportEntry) tileEntity).breakAllConnections();
        }
        super.breakBlock(world, x, y, z, block, meta);
    }


}
