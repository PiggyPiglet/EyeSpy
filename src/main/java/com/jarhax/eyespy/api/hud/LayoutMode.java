package com.jarhax.eyespy.api.hud;


import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.codecs.EnumCodec;

public enum LayoutMode {
    FULL,
    LEFT,
    CENTER,
    RIGHT,
    TOP,
    MIDDLE,
    BOTTOM,
    LEFT_SCROLLING,
    RIGHT_SCROLLING,
    TOP_SCROLLING,
    BOTTOM_SCROLLING,
    CENTER_MIDDLE,
    MIDDLE_CENTER,
    LEFT_CENTER_WRAP;

    public static final Codec<LayoutMode> CODEC = new EnumCodec<>(LayoutMode.class);
}
