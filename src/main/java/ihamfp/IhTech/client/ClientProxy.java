package ihamfp.IhTech.client;

import ihamfp.IhTech.TweakCape;
import ihamfp.IhTech.blocks.ModBlocks;
import ihamfp.IhTech.common.CommonProxy;
import ihamfp.IhTech.common.Config;
import ihamfp.IhTech.common.PacketHandler;
import ihamfp.IhTech.compatibility.TConstructIntegration;
import ihamfp.IhTech.items.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		PacketHandler.registerClientMessages();
		if (Loader.isModLoaded("tconstruct") && Config.TConstructIntegration) {
			TConstructIntegration.moltenModels();
		}
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		//ModBlocks.initModels();
		//ModBlocks.initItemModels();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		if (Config.showCapes)
			MinecraftForge.EVENT_BUS.register(new TweakCape());
		ModBlocks.initColors();
		ModItems.initColors();
	}
}
