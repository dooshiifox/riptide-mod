package dev.dooshii.enchantment;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class MidairJump {
    private static final Set<ServerPlayerEntity> JUMPING_PLAYERS = Collections.newSetFromMap(new WeakHashMap<>());

    private static int timesJumped;
    private static boolean jumpDown;

    public MidairJump() {
    }

    public void onWornTick(ItemStack stack, LivingEntity living) {
        Proxy.INSTANCE.runOnClient(() -> () -> {
            if (living == Minecraft.getInstance().player) {
                Player playerSp = (LocalPlayer) living;

                if (playerSp.onGround()) {
                    timesJumped = 0;
                } else {
                    if (timesJumped == 0) {
                        // regardless how ground contact was lost, count that as first jump
                        timesJumped = 1;
                        jumpDown = true;
                    }
                    if (playerSp.input.jumping) {
                        if (!jumpDown && timesJumped < ((CirrusAmuletItem) stack.getItem()).getMaxAllowedJumps()) {
                            playerSp.jumpFromGround();
                            ClientXplatAbstractions.INSTANCE.sendToServer(JumpPacket.INSTANCE);
                            timesJumped++;
                        }
                        jumpDown = true;
                    } else {
                        jumpDown = false;
                    }
                }
            }
        });
    }

    public static void setJumping(ServerPlayerEntity entity) {
        JUMPING_PLAYERS.add(entity);
    }

    public static boolean popJumping(PlayerEntity entity) {
        if (entity.level().isClientSide) {
            return timesJumped > 0;
        }
        return JUMPING_PLAYERS.remove((ServerPlayerEntity) entity);
    }
}
