package com.jarhax.eyespy.api;

import com.hypixel.hytale.server.core.Message;

import java.text.DecimalFormat;

public class MessageHelpers {

    public static final DecimalFormat ROUNDED_1 = new DecimalFormat("#.#");

    public static Message tier(int tier) {
        return Message.translation("server.eyespy.tier").param("tier", tier);
    }

    public static Message level(int tier) {
        return Message.translation("server.eyespy.level").param("level", tier);
    }

    public static Message capacity(int capacity) {
        return Message.translation("server.eyespy.capacity").param("capacity", capacity);
    }

    public static Message size(int size) {
        return Message.translation("server.eyespy.size").param("size", size);
    }

    public static Message progress(int current, int max) {
        return progress((float) current / max);
    }

    public static Message progress(float amount) {
        return Message.translation("server.eyespy.progress").param("progress", amount);
    }

    public static Message stage(int current, int max) {
        return Message.translation("server.eyespy.stage").param("current", current).param("max", max);
    }

    public static Message health(float current, float max) {
        return Message.translation("server.eyespy.health").param("current", ROUNDED_1.format(current)).param("max", ROUNDED_1.format(max));
    }
}