package net.bettercombat.fabric;

import net.bettercombat.BetterCombatMod;
import net.bettercombat.utils.SoundHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        BetterCombatMod.init();

        ServerLifecycleEvents.SERVER_STARTED.register((minecraftServer) -> {
            BetterCombatMod.loadWeaponAttributes(minecraftServer);
        });

        SoundHelper.registerSounds();
    }
}
