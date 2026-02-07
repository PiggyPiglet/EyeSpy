package com.jarhax.eyespy.api.context;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jarhax.eyespy.api.EyeSpyConfig;

/**
 * Holds context about the game, the observer, and what is being observed.
 */
public class Context {

    private final float delta;
    private final Store<EntityStore> store;
    private final CommandBuffer<EntityStore> commandBuffer;
    private final PlayerRef observer;
    private final EyeSpyConfig config;

    public Context(float delta, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer, PlayerRef observer, EyeSpyConfig config) {
        this.delta = delta;
        this.store = store;
        this.commandBuffer = commandBuffer;
        this.observer = observer;
        this.config = config;
    }

    public float getDelta() {
        return delta;
    }

    public Store<EntityStore> getStore() {
        return store;
    }

    public PlayerRef getObserver() {
        return observer;
    }

    public CommandBuffer<EntityStore> getCommandBuffer() {
        return commandBuffer;
    }

    public PlayerRef observer() {
        return this.observer;
    }

    public World world() {
        return this.getStore().getExternalData().getWorld();
    }

    public EyeSpyConfig getConfig() {
        return config;
    }
}