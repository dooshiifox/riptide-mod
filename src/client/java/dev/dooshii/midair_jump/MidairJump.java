package dev.dooshii.midair_jump;

import dev.dooshii.enchantment.ExtraEnchantmentEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class MidairJump {
    private static int timesJumped;
    private static boolean jumpDown;

    public MidairJump() {
    }

    public void tick() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return;
        }

        if (player.isOnGround()) {
            timesJumped = 0;
        } else {
            if (timesJumped == 0) {
                // regardless how ground contact was lost, count that as first jump
                timesJumped = 1;
                jumpDown = true;
            }
            if (player.input.playerInput.jump()) {
                if (!jumpDown && timesJumped < ExtraEnchantmentEffects.getMidairJump()) {
                    player.jumpFromGround();
                    ClientXplatAbstractions.INSTANCE.sendToServer(JumpPacket.INSTANCE);
                    timesJumped++;
                }
                jumpDown = true;
            } else {
                jumpDown = false;
            }
        }
    }
}
