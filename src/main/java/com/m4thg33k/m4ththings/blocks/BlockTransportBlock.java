package com.m4thg33k.m4ththings.blocks;

import com.m4thg33k.m4ththings.helpers.NameHelper;
import com.m4thg33k.m4ththings.init.ModBlocks;
import com.m4thg33k.m4ththings.init.ModItems;
import com.m4thg33k.m4ththings.interfaces.ITransportBlock;
import com.m4thg33k.m4ththings.tiles.TileTransportBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

public class BlockTransportBlock extends Block implements ITileEntityProvider {

    public BlockTransportBlock(Material material)
    {
        super(material);
        setHardness(0.5f);
        setResistance(0.5f);
        setBlockName(NameHelper.blockItemName("blockTransportBlock"));
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTransportBlock();
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
        if (entity instanceof EntityPlayer && ((EntityPlayer) entity).getHeldItem() != null && (((EntityPlayer) entity).getHeldItem().getItem() == ModItems.itemWrench || ((EntityPlayer) entity).getHeldItem().getItem() == Item.getItemFromBlock(ModBlocks.blockTest)))
        {
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
        }

    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        ItemStack held = Minecraft.getMinecraft().thePlayer.getHeldItem();
        if (held!=null && (held.getItem() == ModItems.itemWrench || held.getItem() == Item.getItemFromBlock(ModBlocks.blockTransportBlock)))
        {
            return super.getSelectedBoundingBoxFromPool(world, x, y, z);
        }
        return AxisAlignedBB.getBoundingBox(0,0,0,0,0,0);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        this.setBlockBounds(0.45f,0.45f,0.45f,0.55f,0.55f,0.55f);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote)
        {
            return true;
        }
        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if (tileEntity != null && tileEntity instanceof TileTransportBlock)
        {
            ((TileTransportBlock) tileEntity).toggleConnection(ForgeDirection.VALID_DIRECTIONS[side],true);
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack stack) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);

        if (tileEntity!=null && tileEntity instanceof TileTransportBlock)
        {
            //LogHelper.info("Attempting to connect to neighbors");
            ((TileTransportBlock) tileEntity).connectToAllTransportBlocks();
            ((TileTransportBlock) tileEntity).connectToAllIFluidHandlers();
        }
    }


    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if (tileEntity!=null && tileEntity instanceof TileTransportBlock)
        {
            ((TileTransportBlock) tileEntity).breakAllConnections();
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if (tileEntity != null && tileEntity instanceof ITransportBlock)
        {
            ((ITransportBlock)tileEntity).breakInvalidConnections();
        }
    }
}
