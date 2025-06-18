package com.dupe.mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;

public class DupeMod implements ClientModInitializer {
    private boolean dupeEnabled = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_X)) {
                dupeEnabled = !dupeEnabled;
                System.out.println("[DupeMod] Dupe mode: " + (dupeEnabled ? "ON" : "OFF"));
                try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            }

            if (dupeEnabled && client.currentScreen instanceof GenericContainerScreen screen) {
                for (int i = 0; i < screen.getScreenHandler().slots.size(); i++) {
                    client.interactionManager.clickSlot(
                        screen.getScreenHandler().syncId,
                        i,
                        0,
                        SlotActionType.QUICK_MOVE,
                        client.player
                    );
                }
            }
        });
    }
}
