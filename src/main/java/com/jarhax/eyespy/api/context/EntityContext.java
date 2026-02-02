package com.jarhax.eyespy.api.context;

import com.hypixel.hytale.builtin.path.PathPlugin;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.TargetUtil;
import com.jarhax.eyespy.api.EyeSpyConfig;

import javax.annotation.Nullable;

public class EntityContext extends Context {

    private final Ref<EntityStore> entity;

    public EntityContext(float delta, int index, ArchetypeChunk<EntityStore> archetypeChunk, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer, PlayerRef observer, EyeSpyConfig config, Ref<EntityStore> entity) {
        super(delta, index, archetypeChunk, store, commandBuffer, observer, config);
        this.entity = entity;
    }

    public Ref<EntityStore> entity() {
        return entity;
    }

    @Nullable
    public static EntityContext create(PlayerRef player, float dt, int index, ArchetypeChunk<EntityStore> archetypeChunk, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer, EyeSpyConfig config) {
        final Ref<EntityStore> playerRef = player.getReference();
        if (playerRef != null) {
            final Store<EntityStore> playerStore = playerRef.getStore();
            final Ref<EntityStore> targetEntity = TargetUtil.getTargetEntity(archetypeChunk.getReferenceTo(index), commandBuffer);
            final TransformComponent transform = playerStore.getComponent(playerRef, TransformComponent.getComponentType());
            if (transform != null && canDisplayEntity(store, targetEntity)) {
                return new EntityContext(dt, index, archetypeChunk, store, commandBuffer, player, config, targetEntity);
            }
        }
        return null;
    }

    private static boolean canDisplayEntity(Store<EntityStore> store, Ref<EntityStore> target) {
        return target != null && store.getComponent(target, ModelComponent.getComponentType()) instanceof ModelComponent mc && !mc.getModel().equals(PathPlugin.get().getPathMarkerModel());
    }
}
