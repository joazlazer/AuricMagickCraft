package joazlazer.mods.amc.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import joazlazer.mods.amc.block.ModBlocks;
import joazlazer.mods.amc.client.render.ItemAwakeningTableRenderer;
import joazlazer.mods.amc.client.render.RenderHelper;
import joazlazer.mods.amc.client.render.RendererAwakeningTable;
import joazlazer.mods.amc.handlers.KeyHandler;
import joazlazer.mods.amc.tileentity.TileEntityAwakeningTable;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerNetworkHandlers() {
        super.registerNetworkHandlers();
    }

    @Override
    public void registerKeyHandlers() {
        KeyHandler.registerKeyHandlers();
        FMLCommonHandler.instance().bus().register(new KeyHandler());
    }

    @Override
    public void initializeRenderHandler() {
        RenderHelper.tess = Tessellator.instance;
    }

    @Override
    public void registerCustomRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAwakeningTable.class, new RendererAwakeningTable());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.BLOCKS.awakeningTable), new ItemAwakeningTableRenderer());
    }
}
