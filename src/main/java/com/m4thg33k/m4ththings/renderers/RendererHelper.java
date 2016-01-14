package com.m4thg33k.m4ththings.renderers;

import com.m4thg33k.m4ththings.utility.LogHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.IntBuffer;
import java.util.Map;

public class RendererHelper {

    //public static final ResourceLocation blockTextureMap = TextureMap.locationBlocksTexture;
    private static BufferedImage blockTextures;
    private static int WIDTH,HEIGHT;
    public static boolean colorMapGenerated = false;
    public static Vec3[] colorMap;

    //The following methods are "Borrowed" from LexManos: https://gist.github.com/LexManos/3c700306331080598daf
    public static void saveGlTexture(String fileName,int textureID, int mipmapLevels)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureID);

        GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT,1);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT,1);

        for (int level=0;level<=mipmapLevels;level++)
        {
            int width = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D,level,GL11.GL_TEXTURE_WIDTH);
            int height = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D,level,GL11.GL_TEXTURE_HEIGHT);
            int size = width*height;

            BufferedImage bufferedImage = new BufferedImage(width,height,2);
            File output = new File(fileName + "_" + textureID + ".png");

            IntBuffer buffer = BufferUtils.createIntBuffer(size);
            int[] data = new int[size];

            GL11.glGetTexImage(GL11.GL_TEXTURE_2D, level, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, buffer);
            buffer.get(data);
            bufferedImage.setRGB(0, 0, width, height, data, 0 , width);

            if (textureID == 9)
            {
                blockTextures = bufferedImage;
                WIDTH = width;
                HEIGHT = height;
                //createFluidColorMaps();
            }

            try
            {
                ImageIO.write(bufferedImage, "png", output);
            }
            catch (IOException e)
            {
                LogHelper.info("Failed to write file: " + fileName);
            }

            Map<Fluid,Integer> fluidMap = FluidRegistry.getRegisteredFluidIDsByFluid();
        }
    }

    private static Field fName;
    private static String getName(TextureMap map) throws Exception
    {
        if (fName == null)
        {
            fName = TextureMap.class.getDeclaredFields()[7];
            fName.setAccessible(true);
        }
        return ((String)fName.get(map)).replace('/','_');
    }
    private static Field fMip;
    private static int getMip(TextureMap map) throws Exception
    {
        if (fMip == null)
        {
            fMip = TextureMap.class.getDeclaredFields()[8];
            fMip.setAccessible(true);
        }
        return fMip.getInt(map);
    }

    public static void postTextureStitch(TextureStitchEvent.Post e) throws Exception
    {
        saveGlTexture("test",e.map.getGlTextureId(),0);
    }

    public static void createFluidColorMaps()
    {
        if (colorMapGenerated)
        {
            return;
        }
        colorMapGenerated = true;

        int numFluids = FluidRegistry.getMaxID();
        colorMap = new Vec3[numFluids];

        IIcon icon;
        int umin,umax,vmin,vmax,numPix,r,g,b,pixel;


        for (int i=1;i<=numFluids;i++)
        {
            icon = FluidRegistry.getFluid(i).getIcon();
            umin = (int)(WIDTH*icon.getMinU());
            umax = (int)(WIDTH*icon.getMaxU());
            vmin = (int)(HEIGHT*icon.getMinV());
            vmax = (int)(HEIGHT*icon.getMaxV());
            numPix = (umax-umin+1)*(vmax-vmin+1);
            r = g = b = 0;
            for (int u=umin;u<=umax;u++)
            {
                for (int v=vmin;v<=vmax;v++)
                {
                    pixel = blockTextures.getRGB(u,v);
                    b += pixel & 255;
                    g += (pixel>>8) & 255;
                    r += (pixel>>16) & 255;
                }
            }
            r = r/numPix;
            g = g/numPix;
            b = b/numPix;

            colorMap[i-1] = Vec3.createVectorHelper(r/256.0,g/256.0,b/256.0);
        }

//        Fluid fluid = FluidRegistry.getFluid(1);
//        IIcon icon = fluid.getStillIcon();
//        int umin = (int)(WIDTH*icon.getMinU());
//        int umax = (int)(WIDTH*icon.getMaxU());
//        int vmin = (int)(HEIGHT*icon.getMinV());
//        int vmax = (int)(HEIGHT*icon.getMaxV());
//        int numPix = (umax-umin+1)*(vmax-vmin+1);
//
//        Vector<LocVec> vals = new Vector<LocVec>();
//        int r=0;
//        int g=0;
//        int b=0;
//        int pixel;
//        for (int u=umin;u<=umax;u++)
//        {
//            for (int v=vmin;v<=vmax;v++)
//            {
//                pixel = blockTextures.getRGB(u,v);
//                b += pixel & 255;
//                g += (pixel>>8)&255;
//                r += (pixel>>16)&255;
//            }
//        }
//
//        r = r/numPix;
//        g = g/numPix;
//        b = b/numPix;
//        vals.add(new LocVec(r,g,b));

        //LogHelper.info("Break point RendererHelper");
    }

    public static Vec3 getFluidRGB(Fluid fluid)
    {
        if (!colorMapGenerated)
        {
            createFluidColorMaps();
        }

        return colorMap[fluid.getID()-1];
    }

}
