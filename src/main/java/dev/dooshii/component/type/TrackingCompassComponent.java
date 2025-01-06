package dev.dooshii.component.type;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.GlobalPos;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public record TrackingCompassComponent(Optional<GlobalPos> target, Optional<GameProfile> player, boolean differentDimension) {
    public static final Codec<GameProfile> GAME_PROFILE_CODEC = RecordCodecBuilder.create((builder2) -> builder2.group(
            Codec.sizeLimitedString(16).fieldOf("username").forGetter(GameProfile::getName),
            Codec.STRING.flatXmap((uuid) -> {
                try {
                    UUID parsed = UUID.fromString(uuid);
                    return DataResult.success(parsed);
                } catch (IllegalArgumentException _e) {
                    return DataResult.error(() -> "Invalid UUID");
                }
            }, (uuid) -> DataResult.success(uuid.toString())).fieldOf("uuid").forGetter(GameProfile::getId)
    ).apply(builder2, (username, uuid) -> new GameProfile(uuid, username)));
    public static final Codec<TrackingCompassComponent> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            GlobalPos.CODEC.optionalFieldOf("target").forGetter(TrackingCompassComponent::target),
            GAME_PROFILE_CODEC.optionalFieldOf("player").forGetter(TrackingCompassComponent::player),
            Codec.BOOL.fieldOf("differentDimension").forGetter(TrackingCompassComponent::differentDimension)
    ).apply(builder, TrackingCompassComponent::new));
    public static final PacketCodec<ByteBuf, TrackingCompassComponent> PACKET_CODEC;
    static {
        PACKET_CODEC = PacketCodec.tuple(
                GlobalPos.PACKET_CODEC.collect(PacketCodecs::optional),
                TrackingCompassComponent::target,
                PacketCodecs.GAME_PROFILE.collect(PacketCodecs::optional),
                TrackingCompassComponent::player,
                PacketCodecs.BOOLEAN,
                TrackingCompassComponent::differentDimension,
                TrackingCompassComponent::new
        );
    }

    public Optional<GlobalPos> target() { return this.target; }
    public Optional<GameProfile> player() { return this.player; }
    public boolean differentDimension() { return this.differentDimension; }

    static public GlobalPos getEntityGPos(Entity entity) {
        return GlobalPos.create(entity.getWorld().getRegistryKey(), entity.getBlockPos());
    }

    public Optional<ServerPlayerEntity> getOnlinePlayer(ServerWorld world) {
        return this.player().map((player) -> world.getServer().getPlayerManager().getPlayer(player.getId()));
    }

    public boolean isTargetedPlayer(Entity entity) {
        if (!(entity instanceof PlayerEntity playerEntity)) return false;

        UUID uuid = playerEntity.getGameProfile().getId();
        return this.player().map(p -> p.getId().equals(uuid)).orElse(false);
    }

    public TrackingCompassComponent update(ServerWorld world) {
        Optional<GlobalPos> target = Optional.empty();
        Optional<GameProfile> player = this.player();
        Optional<ServerPlayerEntity> onlinePlayer = this.getOnlinePlayer(world);
        boolean diffDimension = true;

        if (onlinePlayer.isPresent()) {
            ServerPlayerEntity p = onlinePlayer.get();
            target = Optional.of(TrackingCompassComponent.getEntityGPos(p));
            diffDimension = world != p.getWorld();

            String cachedName = player.orElseThrow().getName();
            String realName = p.getGameProfile().getName();
            if (!Objects.equals(cachedName, realName)) {
                player = Optional.of(p.getGameProfile());
            }
        }

        return new TrackingCompassComponent(target, player, diffDimension);
    }
}


