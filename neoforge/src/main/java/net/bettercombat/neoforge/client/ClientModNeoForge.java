package net.bettercombat.neoforge.client;

import net.bettercombat.client.BetterCombatClientMod;
import net.bettercombat.client.animation.AnimationRegistry;
import net.bettercombat.BetterCombatMod;
import net.bettercombat.client.Keybindings;
import net.minecraft.client.MinecraftClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod.EventBusSubscriber(modid = BetterCombatMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModNeoForge {
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event){
        Keybindings.all.forEach(event::register);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
//        BetterCombatClientMod.initialize();
//
//        var resourceManager = MinecraftClient.getInstance().getResourceManager();
//        AnimationRegistry.load(resourceManager);
//
//        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> {
//            return new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> {
//                return new ConfigMenuScreen(screen);
//            });
//        });
    }
}