package com.m4thg33k.m4ththings.renderers;

import cofh.api.item.IToolHammer;
import com.m4thg33k.m4ththings.init.ModBlocks;
import com.m4thg33k.m4ththings.init.ModItems;
import com.m4thg33k.m4ththings.players.M4thExtendedPlayer;
import com.m4thg33k.m4ththings.tiles.TileFluidTransportEntry;
import com.m4thg33k.m4ththings.tiles.TileTransportBlock;
import com.m4thg33k.m4ththings.utility.LogHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class FluidTransportEntryRenderer extends TileEntitySpecialRenderer {

    //I put a comment here just to see if I could...mwahahahahaha

    private IModelCustom fluidTransportEntry;
    private IModelCustom connections;
    private ResourceLocation fluidTransportEntryTexture;
    private ResourceLocation connectionUV;
    private ResourceLocation[] overlays;

    public FluidTransportEntryRenderer()
    {
        fluidTransportEntry = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/fluidTransportEntryPoint.obj"));
        fluidTransportEntryTexture = new ResourceLocation("m4ththings","models/fluidTransportEntryPoint.png");
        overlays = new ResourceLocation[2];
        overlays[0] = new ResourceLocation("m4ththings","models/FluidTransportEntrySideOverlay.png");
        overlays[1] = new ResourceLocation("m4ththings","models/FluidTransportEntryTopOverlay.png");
        connections = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/TransportConnection.obj"));
        connectionUV = new ResourceLocation("m4ththings","models/TransportConnectionUV.png");
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
        int attachedSide;
        boolean[] connections;



        if (tile!=null && tile instanceof TileFluidTransportEntry){
            attachedSide = ((TileFluidTransportEntry) tile).getAttachedSide();
            connections = ((TileFluidTransportEntry) tile).getConnections();
            //ox = 0.25f*ForgeDirection.VALID_DIRECTIONS[attachedSide].offsetX;
            //oy = 0.25f*ForgeDirection.VALID_DIRECTIONS[attachedSide].offsetY;
            //oz = 0.25f*ForgeDirection.VALID_DIRECTIONS[attachedSide].offsetZ;
            //bindTexture(TextureMap.locationBlocksTexture);
            //CubeRenderer.renderCube(x+0.5+ox,y+0.5+oy,z+0.5+oz,0.0,0.0,0.0,0.05, Blocks.cobblestone.getIcon(0,0));

            //boolean[] sides = ((TileFluidTransportEntry) tile).getConnections();
            //ForgeDirection[] d = ForgeDirection.VALID_DIRECTIONS;
            //for (int i=0;i<6;i++)
            //{
                //if (sides[i])
                //{
                    //CubeRenderer.renderCube(x+0.5+ox+ 0.1*d[i].offsetX,y+0.5+oy+0.1*d[i].offsetY,z+0.5+oz+0.1*d[i].offsetZ,0.0,0.0,0.0,0.05,Blocks.diamond_block.getIcon(0,0));
                //}
            //}

            GL11.glPushMatrix();
            GL11.glTranslated(x+0.5,y+0.5,z+0.5);
            switch (attachedSide)
            {
                case 1:
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
            //GL11.glScaled(2,2,2);
            bindTexture(fluidTransportEntryTexture);
            fluidTransportEntry.renderAll();
            GL11.glPopMatrix();

            if(!props.seesPipes() && flag)
            {
                return;
            }

            for (int i=0;i<6;i++) {
                if (connections[i]) {
                    GL11.glPushMatrix();
                    GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
                    rotateToMatchSide(ForgeDirection.VALID_DIRECTIONS[i].ordinal());
                    bindTexture(connectionUV);
                    this.connections.renderAll();
                    GL11.glPopMatrix();
                }
            }
            //renderOverlays(x,y,z,attachedSide,connections);

        }
    }

    private void renderOverlays(double x, double y, double z, int attachedSide,boolean[] connections)
    {
        if (connections[ForgeDirection.VALID_DIRECTIONS[attachedSide].getOpposite().ordinal()]) {
            GL11.glPushMatrix();
            GL11.glTranslated(x+0.5,y+0.5,z+0.5);

//            switch (attachedSide) {
//                case 1:
//                    GL11.glTranslated(x + 0.5, y + 0.945, z + 0.5);
//                    break;
//                case 2:
//                    GL11.glTranslated(x + 0.5, y + 0.5, z + 0.055);
//                    break;
//                case 3:
//                    GL11.glTranslated(x + 0.5, y + 0.5, z + 0.945);
//                    break;
//                case 4:
//                    GL11.glTranslated(x + 0.055, y + 0.5, z + 0.5);
//                    break;
//                case 5:
//                    GL11.glTranslated(x + 0.945, y + 0.5, z + 0.5);
//                    break;
//                default:
//                    GL11.glTranslated(x + 0.5, y + 0.055, z + 0.5);
//            }

            switch (attachedSide) {
                case 1:
                    GL11.glRotated(180.0, 1.0, 0.0, 0.0);
                    break;
                case 2:
                    GL11.glRotated(90.0, 1.0, 0.0, 0.0);
                    break;
                case 3:
                    GL11.glRotated(-90.0, 1.0, 0.0, 0.0);
                    break;
                case 4:
                    GL11.glRotated(-90.0, 0.0, 0.0, 1.0);
                    break;
                case 5:
                    GL11.glRotated(90.0, 0.0, 0.0, 1.0);
                    break;
                default:
            }


            //bindTexture(overlays[1]);
            bindTexture(connectionUV);
            this.connections.renderAll();
//            Tessellator t = Tessellator.instance;
//            t.startDrawingQuads();
//            t.addVertexWithUV(-0.25, 0, -0.25, 0.0, 1.0);
//            t.addVertexWithUV(-0.25, 0, 0.25, 0.0, 0.0);
//            t.addVertexWithUV(0.25, 0, 0.25, 1.0, 0.0);
//            t.addVertexWithUV(0.25, 0, -0.25, 1.0, 1.0);
//            t.draw();
            GL11.glPopMatrix();
        }
    }

    public void rotateToMatchSide(int attachedSide)
    {
        switch (attachedSide) {
            case 1:
                GL11.glRotated(180.0, 1.0, 0.0, 0.0);
                break;
            case 2:
                GL11.glRotated(90.0, 1.0, 0.0, 0.0);
                break;
            case 3:
                GL11.glRotated(-90.0, 1.0, 0.0, 0.0);
                break;
            case 4:
                GL11.glRotated(-90.0, 0.0, 0.0, 1.0);
                break;
            case 5:
                GL11.glRotated(90.0, 0.0, 0.0, 1.0);
                break;
            default:
        }
    }
}
