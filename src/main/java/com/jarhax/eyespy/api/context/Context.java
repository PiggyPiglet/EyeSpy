package com.jarhax.eyespy.api.context;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jarhax.eyespy.api.EyeSpyConfig;

public class Context {

    private final float delta;
    private final int index;
    private final ArchetypeChunk<EntityStore> archetypeChunk;
    private final Store<EntityStore> store;
    private final CommandBuffer<EntityStore> commandBuffer;

    private final PlayerRef observer;
    private final WorldChunk chunk;
    private final EyeSpyConfig config;

    public Context(float delta, int index, ArchetypeChunk<EntityStore> archetypeChunk, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer, PlayerRef observer, WorldChunk chunk, EyeSpyConfig config) {
        this.delta = delta;
        this.index = index;
        this.archetypeChunk = archetypeChunk;
        this.store = store;
        this.commandBuffer = commandBuffer;
        this.observer = observer;
        this.chunk = chunk;
        this.config = config;
    }

    public float getDelta() {
        return delta;
    }

    public ArchetypeChunk<EntityStore> getArchetypeChunk() {
        return archetypeChunk;
    }

    public int getIndex() {
        return index;
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

    public WorldChunk getChunk() {
        return chunk;
    }

    public EyeSpyConfig getConfig() {
        return config;
    }
}
