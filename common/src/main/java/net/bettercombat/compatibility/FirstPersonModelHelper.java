package net.bettercombat.compatibility;

import dev.tr7zw.firstperson.api.FirstPersonAPI;
import dev.tr7zw.firstperson.FirstPersonModelCore;

import java.util.function.Supplier;

public class FirstPersonModelHelper {
    public static Supplier<Boolean> isDisabled() {
        return (() -> {
            return !(FirstPersonAPI.isEnabled() && !FirstPersonModelCore.instance.getLogicHandler().hideArmsAndItems());
        });
    }
}
