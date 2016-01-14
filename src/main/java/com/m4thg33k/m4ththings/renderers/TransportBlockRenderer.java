package com.m4thg33k.m4ththings.renderers;

import cofh.api.item.IToolHammer;
import com.m4thg33k.m4ththings.init.ModBlocks;
import com.m4thg33k.m4ththings.init.ModItems;
import com.m4thg33k.m4ththings.players.M4thExtendedPlayer;
import com.m4thg33k.m4ththings.tiles.TileTest;
import com.m4thg33k.m4ththings.tiles.TileTransportBlock;
import com.rwtema.extrautils.fakeplayer.FakeWorld;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class TransportBlockRenderer extends TileEntitySpecialRenderer{

    private IModelCustom block;
    private IModelCustom connector;
    private ResourceLocation blockTexture;
    private ResourceLocation connectorTexture;
    private double scale = 0.09;

    public TransportBlockRenderer()
    {
        block = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/TransportBlock.obj"));
        blockTexture = new ResourceLocation("m4ththings","models/TransportBlockUV.png");
        connector = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/TransportConnection.obj"));
        connectorTexture = new ResourceLocation("m4ththings","models/TransportConnectionUV.png");
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        EntityLivingBase player = this.field_147501_a.field_147551_g;
        if (!(player instanceof EntityPlayer))
        {
            return;
        }
        ItemStack held = player.getHeldItem();

        M4thExtendedPlayer props = M4thExtendedPlayer.get((EntityPlayer)player);

        boolean flag = held == null || !(held.getItem() instanceof IToolHammer || held.getItem() == Item.getItemFromBlock(ModBlocks.blockTransportBlock) || held.getItem() == Item.getItemFromBlock(ModBlocks.blockFluidTransportEntry));
        //boolean flag = false; //swap comments to make it invisible when holding the correct item(s)
        if (!props.seesPipes() && (flag || tile==null || !(tile instanceof TileTransportBlock)))
        {
            return;
        }
        //bindTexture(TextureMap.locationBlocksTexture);
        //CubeRenderer.renderCube(x+0.5,y+0.5,z+0.5,0.0,0.0,0.0,0.05, Blocks.cobblestone.getIcon(0,0));

        int timer = ((TileTransportBlock)tile).getTimer();
        bindTexture(blockTexture);
        GL11.glPushMatrix();
        GL11.glTranslated(x+0.5,y+0.5+0.01*Math.sin(timer/4.0),z+0.5);
        GL11.glRotated(10.0*Math.cos(timer/8.0),0.0,1.0,0.0);
        GL11.glRotated(10.0*Math.cos(timer/8.0+45.0),1.0,0.0,0.0);
        GL11.glScaled(scale,scale,scale);
        block.renderAll();
        GL11.glPopMatrix();


        boolean[] sides = ((TileTransportBlock) tile).getConnections();
        ForgeDirection[] d = ForgeDirection.VALID_DIRECTIONS;
        bindTexture(connectorTexture);
        for (int i=0;i<6;i++)
        {
            if (sides[i])
            {
                GL11.glPushMatrix();
                GL11.glTranslated(x+0.5,y+0.5,z+0.5);
                //rotate correctly
                switch (i)
                {
                    case 1://up
                        GL11.glRotated(180.0,1.0,0.0,0.0);
                        break;
                    case 2:
                        GL11.glRotated(90.0,1.0,0.0,0.0);
                        break;
                    case 3:
                        GL11.glRotated(-90.0,1.0,0.0,0.0);
                        break;
                    case 4:
                        GL11.glRotated(-90.0,0.0,0.0,1.0);
                        break;
                    case 5:
                        GL11.glRotated(90.0,0.0,0.0,1.0);
                        break;
                    default:
                }
                connector.renderAll();
                GL11.glPopMatrix();
                //CubeRenderer.renderCube(x+0.5+ 0.1*d[i].offsetX,y+0.5+0.1*d[i].offsetY,z+0.5+0.1*d[i].offsetZ,0.0,0.0,0.0,0.05,Blocks.diamond_block.getIcon(0,0));
            }
        }

    }

}
