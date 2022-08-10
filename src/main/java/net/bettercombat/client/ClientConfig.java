package net.bettercombat.client;

import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.api.ConfigGroup;
import net.minecraft.text.TextColor;

public class ClientConfig implements ConfigGroup {
    @ConfigEntry
    public boolean isHoldToAttackEnabled = true;
    @ConfigEntry
    public boolean isMiningWithWeaponsEnabled = true;
    @ConfigEntry
    public boolean isSwingThruGrassEnabled = true;
    @ConfigEntry
    public boolean isHighlightCrosshairEnabled = false;
    @ConfigEntry
    public TextColor hudHighlightColor = TextColor.fromRgb(0xFF0000);
    @ConfigEntry
    public boolean isShowingArmsInFirstPerson = false;
    @ConfigEntry
    public boolean isSmoothAnimationTransitionEnabled = true;
    @ConfigEntry
    public boolean isTooltipAttackRangeEnabled = true;
    @ConfigEntry
    public boolean isWeaponSwingSoundEnabled = true;

//    public Listener listener;
//    public interface Listener {
//        void feintKeyUpdated();
//    }
//
//    public void setFeintKey(Key feintKey) {
//        this.feintKey = feintKey;
//        if (listener != null) {
//            listener.feintKeyUpdated();
//        }
//    }
}
