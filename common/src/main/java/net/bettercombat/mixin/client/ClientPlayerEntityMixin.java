package net.bettercombat.mixin.client;

import net.bettercombat.BetterCombat;
import net.bettercombat.api.MinecraftClient_BetterCombat;
import net.bettercombat.logic.PlayerAttackHelper;
import net.bettercombat.logic.PlayerAttackProperties;
import net.bettercombat.utils.MathHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/Input;tick(ZF)V", shift = At.Shift.AFTER))
    private void tickMovement_ModifyInput(CallbackInfo ci) {
        var config = BetterCombat.config;
        var client = (MinecraftClient_BetterCombat) MinecraftClient.getInstance();
        var clientPlayer = (ClientPlayerEntity)((Object)this);
        if (clientPlayer.hasVehicle() && !config.movement_speed_effected_while_mounting) {
            return;
        }
        var swingProgress = client.getSwingProgress();

        var comboCount = ((PlayerAttackProperties) clientPlayer).getComboCount();
        var attack = PlayerAttackHelper.getCurrentAttack(clientPlayer, comboCount - 1);
        if (attack == null) {
            return;
        }
        var attackMovementMultiplier = attack.attack().movementSpeedMultiplier();
        if (attackMovementMultiplier == -1) {
            attackMovementMultiplier = 1.0;
        }
        var multiplier = Math.min(Math.max(config.movement_speed_while_attacking, 0.0), 1.0);
//        System.out.println("Multiplier " + multiplier);
        if (multiplier == 1 && attackMovementMultiplier == 1.0) {
            return;
        }
        if (swingProgress < 0.98) {
            if (config.movement_speed_applied_smoothly) {
                double p2 = 0;
                if (swingProgress <= 0.5) {
                    p2 = MathHelper.easeOutCubic(swingProgress * 2);
                } else {
                    p2 = MathHelper.easeOutCubic(1 - ((swingProgress - 0.5) * 2));
                }
                multiplier = (float) ( 1.0 - (1.0 - multiplier) * p2 );
//                var chart = "-".repeat((int)(100.0 * multiplier)) + "x";
//                System.out.println("Movement speed multiplier: " + String.format("%.4f", multiplier) + ">" + chart);
            }
            clientPlayer.input.movementForward *= multiplier * attackMovementMultiplier;
            clientPlayer.input.movementSideways *= multiplier * attackMovementMultiplier;
        }
    }
}
