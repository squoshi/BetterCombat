package net.bettercombat.network;

import com.google.gson.Gson;
import net.bettercombat.BetterCombatMod;
import net.bettercombat.config.ServerConfig;
import net.bettercombat.logic.AnimatedHand;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class Packets {
    public record C2S_AttackRequest(int comboCount, boolean isSneaking, int selectedSlot, int[] entityIds) implements CustomPayload {
        public C2S_AttackRequest(int comboCount, boolean isSneaking, int selectedSlot, List<Entity> entities) {
            this(comboCount, isSneaking, selectedSlot, convertEntityList(entities));
        }

        private static int[] convertEntityList(List<Entity> entities) {
            int[] ids = new int[entities.size()];
            for(int i = 0; i < entities.size(); i++) {
                ids[i] = entities.get(i).getId();
            }
            return ids;
        }

        public static Identifier ID = new Identifier(BetterCombatMod.ID, "c2s_request_attack");
        public static boolean UseVanillaPacket = true;
        public void write(PacketByteBuf buffer) {
            buffer.writeInt(comboCount);
            buffer.writeBoolean(isSneaking);
            buffer.writeInt(selectedSlot);
            buffer.writeIntArray(entityIds);
        }

        public static C2S_AttackRequest read(PacketByteBuf buffer) {
            int comboCount = buffer.readInt();
            boolean isSneaking = buffer.readBoolean();
            int selectedSlot = buffer.readInt();
            int[] ids = buffer.readIntArray();
            return new C2S_AttackRequest(comboCount, isSneaking, selectedSlot, ids);
        }

        @Override
        public Identifier id() {
            return ID;
        }
    }

    public record AttackAnimation(int playerId, AnimatedHand animatedHand, String animationName, float length, float upswing) implements CustomPayload {
        public static Identifier ID = new Identifier(BetterCombatMod.ID, "attack_animation");
        public static String StopSymbol = "!STOP!";
        public static AttackAnimation stop(int playerId, int length) { return new AttackAnimation(playerId, AnimatedHand.MAIN_HAND, StopSymbol, length, 0); }

        public void write(PacketByteBuf buffer) {
            buffer.writeInt(playerId);
            buffer.writeInt(animatedHand.ordinal());
            buffer.writeString(animationName);
            buffer.writeFloat(length);
            buffer.writeFloat(upswing);
        }

        public static AttackAnimation read(PacketByteBuf buffer) {
            int playerId = buffer.readInt();
            var animatedHand = AnimatedHand.values()[buffer.readInt()];
            String animationName = buffer.readString();
            float length = buffer.readFloat();
            float upswing = buffer.readFloat();
            return new AttackAnimation(playerId, animatedHand, animationName, length, upswing);
        }

        @Override
        public Identifier id() {
            return ID;
        }
    }

    public record AttackSound(double x, double y, double z, String soundId, float volume, float pitch, long seed) implements CustomPayload {
        public static Identifier ID = new Identifier(BetterCombatMod.ID, "attack_sound");
        public void write(PacketByteBuf buffer) {
            buffer.writeDouble(x);
            buffer.writeDouble(y);
            buffer.writeDouble(z);
            buffer.writeString(soundId);
            buffer.writeFloat(volume);
            buffer.writeFloat(pitch);
            buffer.writeLong(seed);
        }

        public static AttackSound read(PacketByteBuf buffer) {
            var x = buffer.readDouble();
            var y = buffer.readDouble();
            var z = buffer.readDouble();
            var soundId = buffer.readString();
            var volume = buffer.readFloat();
            var pitch = buffer.readFloat();
            var seed = buffer.readLong();
            return new AttackSound(x, y, z, soundId, volume, pitch, seed);
        }

        @Override
        public Identifier id() {
            return ID;
        }
    }

    public record WeaponRegistrySync(List<String> chunks) implements CustomPayload {
        public static Identifier ID = new Identifier(BetterCombatMod.ID, "weapon_registry");

        public void write(PacketByteBuf buffer) {
            buffer.writeInt(chunks.size());
            for (var chunk: chunks) {
                buffer.writeString(chunk);
            }
        }

        public static WeaponRegistrySync read(PacketByteBuf buffer) {
            var chunkCount = buffer.readInt();
            var chunks = new ArrayList<String>();
            for (int i = 0; i < chunkCount; ++i) {
                chunks.add(buffer.readString());
            }
            return new WeaponRegistrySync(chunks);
        }

        @Override
        public Identifier id() {
            return ID;
        }
    }

    public record ConfigSync(String json) implements CustomPayload {
        public static Identifier ID = new Identifier(BetterCombatMod.ID, "config_sync");

        private static final Gson gson = new Gson();
        public static String serialize(ServerConfig config) {
            return gson.toJson(config);
        }

        public void write(PacketByteBuf buffer) {
            buffer.writeString(json);
        }

        public static ConfigSync read(PacketByteBuf buffer) {
            var json = buffer.readString();
            return new ConfigSync(json);
        }

        public ServerConfig deserialized() {
            return gson.fromJson(json, ServerConfig.class);
        }

        @Override
        public Identifier id() {
            return ID;
        }
    }

    public record Ack(String code) implements CustomPayload {
        public static Identifier ID = new Identifier(BetterCombatMod.ID, "ack");

        @Override
        public void write(PacketByteBuf buffer) {
            buffer.writeString(code);
        }

        public static Ack read(PacketByteBuf buffer) {
            var code = buffer.readString();
            return new Ack(code);
        }

        @Override
        public Identifier id() {
            return ID;
        }
    }
}