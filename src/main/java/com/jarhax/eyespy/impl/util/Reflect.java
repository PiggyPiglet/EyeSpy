package com.jarhax.eyespy.impl.util;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Reflect {


    public static class UICommandBuilder_ {
        private static final MethodHandles.Lookup LOOKUP = Util.make(() -> {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            return MethodHandles.privateLookupIn(UICommandBuilder.class, lookup);
        });

        private static final Supplier<VarHandle> _CODEC_MAP = Util.memoize(() -> LOOKUP.findStaticVarHandle(UICommandBuilder.class, "CODEC_MAP", Map.class));
        public static final Supplier<Map<Class<?>, Codec<?>>> CODEC_MAP = () -> (Map<Class<?>, Codec<?>>) _CODEC_MAP.get().get();
    }

    public static class HudManager_ {
        private static final MethodHandles.Lookup LOOKUP = Util.make(() -> {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            return MethodHandles.privateLookupIn(HudManager.class, lookup);
        });

        private static final Supplier<VarHandle> _CUSTOM_HUD = Util.memoize(() -> LOOKUP.findVarHandle(HudManager.class, "customHud", CustomUIHud.class));
        public static final BiConsumer<HudManager, CustomUIHud> CUSTOM_HUD = (hudManager, hud) -> _CUSTOM_HUD.get().set(hudManager, hud);
    }
}
