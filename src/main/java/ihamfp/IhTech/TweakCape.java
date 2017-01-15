package ihamfp.IhTech;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TweakCape {
	private static Minecraft mc = FMLClientHandler.instance().getClient();
	private static ResourceLocation ihamfp = new ResourceLocation("ihtech:textures/capes/ihamfp.png");
	private static ResourceLocation noli = new ResourceLocation("ihtech:textures/capes/noli.png");
	
	private static HashMap<String, Boolean> addedList = new HashMap<String, Boolean>();
	
	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
		if (event.phase == Phase.START && mc.theWorld != null) {
			for (EntityPlayer player : mc.theWorld.playerEntities) {
				if (addedList.containsKey(player.getName())) continue;
				if (StringUtils.stripControlCodes(player.getName()).equals("_Firew0lf")) {
					addCape(player, ihamfp);
					addedList.put(player.getName(), true);
				} else if (StringUtils.stripControlCodes(player.getName()).equals("Nolifertu")) {
					addCape(player, noli);
				} else {
					ModIhTech.logger.info("No cape for " + player.getName());
					addedList.put(player.getName(), false);
				}
			}
		}
	}
	
	public void addCape(EntityPlayer player, ResourceLocation cape) {
		if (!(player instanceof AbstractClientPlayer)) {
			ModIhTech.logger.error("Player was not abstract enough.");
			return;
		}
		
		AbstractClientPlayer aplayer = (AbstractClientPlayer)player;
		
		try {
			Method met;
			try {
				met = AbstractClientPlayer.class.getDeclaredMethod("func_175155_b");
			} catch (Exception ignore) {
				ModIhTech.logger.error("Could not get normal meth");
			} finally {
				met = AbstractClientPlayer.class.getDeclaredMethod("getPlayerInfo");
			}
			if (met == null) {
				ModIhTech.logger.error("Could not get any meth :(");
				return;
			}
			ModIhTech.logger.info("Got meth.");
			met.setAccessible(true);
			
			Object obj = met.invoke(aplayer);
			if (!(obj instanceof NetworkPlayerInfo)) return;
		
			NetworkPlayerInfo npi = (NetworkPlayerInfo)obj;
			
			Field playerTextures;
			try {
				playerTextures = NetworkPlayerInfo.class.getDeclaredField("field_187107_a");
			} catch (Exception ignore) {
				
			} finally {
				playerTextures = NetworkPlayerInfo.class.getDeclaredField("playerTextures");
			}
			playerTextures.setAccessible(true);
			Map<MinecraftProfileTexture.Type, ResourceLocation> map = (Map<MinecraftProfileTexture.Type, ResourceLocation>)playerTextures.get(npi);
			
			map.put(MinecraftProfileTexture.Type.CAPE, cape);
			ModIhTech.logger.info("My cape goes here.");
			
		} catch (Exception e) {
			ModIhTech.logger.error("I failed. " + e.getMessage());
			return;
		}
	}
}