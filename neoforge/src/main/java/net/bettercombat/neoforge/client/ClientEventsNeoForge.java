package net.bettercombat.neoforge.client;

import net.bettercombat.BetterCombatMod;
import net.bettercombat.client.gui.HudRenderHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@Mod.EventBusSubscriber(modid = BetterCombatMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventsNeoForge {
    @SubscribeEvent
    public static void onRenderHud(RenderGuiEvent.Post event){
//        HudRenderHelper.render(event.getGuiGraphics(), event.getPartialTick());
    }
}