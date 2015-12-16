package com.m4thg33k.m4ththings.renderers;

import com.m4thg33k.m4ththings.init.ModBlocks;
import com.m4thg33k.m4ththings.init.ModItems;
import com.m4thg33k.m4ththings.tiles.TileFluidTransportEntry;
import com.m4thg33k.m4ththings.tiles.TileTransportBlock;
import com.m4thg33k.m4ththings.utility.LogHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
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

    private IModelCustom fluidTransportEntry;
    private ResourceLocation fluidTransportEntryTexture;
    private ResourceLocation[] overlays;

    public FluidTransportEntryRenderer()
    {
        fluidTransportEntry = AdvancedModelLoader.loadModel(new ResourceLocation("m4ththings","models/fluidTransportEntryPoint.obj"));
        fluidTransportEntryTexture = new ResourceLocation("m4ththings","models/fluidTransportEntryPoint.png");
        overlays = new ResourceLocation[2];
        overlays[0] = new ResourceLocation("m4ththings","models/FluidTransportEntrySideOverlay.png");
        overlays[1] = new ResourceLocation("m4ththings","models/FluidTransportEntryTopOverlay.png");
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        ItemStack held = this.field_147501_a.field_147551_g.getHeldItem();
        //boolean flag = held == null || !(held.getItem()== ModItems.itemWrench || held.getItem() == Item.getItemFromBlock(ModBlocks.blockFluidTransportEntry));
        boolean flag = false; //swap comments to make it invisible when holding the correct item(s)
        float ox,oy,oz;

        if (flag)
        {
            return;
        }

        if (tile!=null && tile instanceof TileFluidTransportEntry){
            int attachedSide = ((TileFluidTransportEntry) tile).getAttachedSide();
            boolean[] connections = ((TileFluidTransportEntry) tile).getConnections();
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
            bindTexture(fluidTransportEntryTexture);
            fluidTransportEntry.renderAll();
            GL11.glPopMatrix();

            renderOverlays(x,y,z,attachedSide,connections);
        }

    }

    private void renderOverlays(double x, double y, double z, int attachedSide,boolean[] connections)
    {
        if (connections[ForgeDirection.VALID_DIRECTIONS[attachedSide].getOpposite().ordinal()]) {
            GL11.glPushMatrix();
            //GL11.glTranslated(x+0.5,y+0.055,z+0.5);

            switch (attachedSide) {
                case 1:
                    GL11.glTranslated(x + 0.5, y + 0.945, z + 0.5);
                    break;
                case 2:
                    GL11.glTranslated(x + 0.5, y + 0.5, z + 0.055);
                    break;
                case 3:
                    GL11.glTranslated(x + 0.5, y + 0.5, z + 0.945);
                    break;
                case 4:
                    GL11.glTranslated(x + 0.055, y + 0.5, z + 0.5);
                    break;
                case 5:
                    GL11.glTranslated(x + 0.945, y + 0.5, z + 0.5);
                    break;
                default:
                    GL11.glTranslated(x + 0.5, y + 0.055, z + 0.5);
            }

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


            bindTexture(overlays[1]);

            Tessellator t = Tessellator.instance;
            t.startDrawingQuads();
            t.addVertexWithUV(-0.25, 0, -0.25, 0.0, 1.0);
            t.addVertexWithUV(-0.25, 0, 0.25, 0.0, 0.0);
            t.addVertexWithUV(0.25, 0, 0.25, 1.0, 0.0);
            t.addVertexWithUV(0.25, 0, -0.25, 1.0, 1.0);
            t.draw();
            GL11.glPopMatrix();
        }
    }
}
