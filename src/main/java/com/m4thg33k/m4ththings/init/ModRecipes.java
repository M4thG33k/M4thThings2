package com.m4thg33k.m4ththings.init;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModRecipes {

    public static void init()
    {
        //GPI ingot
        GameRegistry.addRecipe(new ItemStack(ModItems.itemModIngot,1,0),"nnn","nnn","nnn",'n',new ItemStack(ModItems.itemModNugget,1,0));
        GameRegistry.addRecipe(new ItemStack(ModItems.itemModIngot,1,0)," g ","gig"," g ",'g',new ItemStack(Items.gold_nugget,1),'i',new ItemStack(Items.iron_ingot,1));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemModIngot,9,0),new ItemStack(ModBlocks.blockIngotBlock,1,0));
        //GPI block
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockIngotBlock,1,0)," G ","GIG"," G ",'G',new ItemStack(Items.gold_ingot,1),'I',new ItemStack(Blocks.iron_block));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockIngotBlock,1,0),"iii","iii","iii",'i',new ItemStack(ModItems.itemModIngot,9,0));
        //GPI nugget
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemModNugget,9,0),new ItemStack(ModItems.itemModIngot,1,0));
        //Wrench
        GameRegistry.addRecipe(new ItemStack(ModItems.itemWrench,1),"n n"," s "," n ",'n',new ItemStack(ModItems.itemModNugget,1,0),'s',new ItemStack(Blocks.stone,1));
        //Item Valve (crafting item)
        GameRegistry.addRecipe(new ItemStack(ModItems.itemModCrafting,1,0),"n n"," n ",'n',new ItemStack(ModItems.itemModNugget,1,0));
        //BaseTank
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockBaseTank,1),"lv ","g g"," v ",'l',new ItemStack(Item.getItemFromBlock(Blocks.ladder),1),'v',new ItemStack(ModItems.itemModCrafting,1,0),'g',new ItemStack(Blocks.glass,1));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockBaseTank,1),new ItemStack(ModBlocks.blockBaseTank,1));
        //AdvancedTank
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockAdvancedTank,1),"lv ","gBg"," v ",'l',new ItemStack(Item.getItemFromBlock(Blocks.ladder),1),'v', new ItemStack(ModItems.itemModCrafting,1,0),'g',new ItemStack(Blocks.glass,1),'B',new ItemStack(ModBlocks.blockIngotBlock,1,0));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockAdvancedTank,1),new ItemStack(ModBlocks.blockBaseTank,1),new ItemStack(ModBlocks.blockIngotBlock,1,0));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockAdvancedTank,1),new ItemStack(ModBlocks.blockAdvancedTank,1));
        //Pipe-seeing button
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemPipeVisionToggler,1), new ItemStack(Blocks.wooden_button,1), new ItemStack(ModItems.itemWrench.setContainerItem(ModItems.itemWrench),1,OreDictionary.WILDCARD_VALUE));
        //fluid transport entry
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockFluidTransportEntry,1)," r ","rVr","nnn",'r',new ItemStack(Items.redstone,1),'V',new ItemStack(ModItems.itemModCrafting,1,0),'n',new ItemStack(ModItems.itemModNugget,1,0));
        //transport blocks
        GameRegistry.addRecipe(new ItemStack(ModBlocks.blockTransportBlock,8),"sss","gng","sss",'g',new ItemStack(Blocks.glass,1),'s',new ItemStack(Blocks.stone,1),'n',new ItemStack(ModItems.itemModNugget,1,0));
    }
}
