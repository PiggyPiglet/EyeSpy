package com.jarhax.eyespy.api.ui;


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

    private final String elementName;

    LayoutMode() {
        this.elementName = EnumCodec.EnumStyle.LEGACY.formatCamelCase(this.name());
    }

    public static final Codec<LayoutMode> CODEC = new EnumCodec<>(LayoutMode.class);

    public String elementName() {
        return this.elementName;
    }
}