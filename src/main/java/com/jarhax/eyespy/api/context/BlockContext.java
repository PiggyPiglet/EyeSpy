package com.jarhax.eyespy.api.context;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.FillerBlockUtil;
import com.hypixel.hytale.server.core.util.TargetUtil;
import com.jarhax.eyespy.api.EyeSpyConfig;

import javax.annotation.Nullable;

public class BlockContext extends Context {

    private final BlockType block;
    private final BlockState state;
    private final Vector3i targetPos;
    private final Vector3i offsetPos;

    public BlockContext(float delta, int index, ArchetypeChunk<EntityStore> archetypeChunk, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer, PlayerRef observer, WorldChunk chunk, EyeSpyConfig config, Vector3i targetPos, Vector3i offsetPos, BlockType block, BlockState state) {
        super(delta, index, archetypeChunk, store, commandBuffer, observer, chunk, config);
        this.targetPos = targetPos;
        this.offsetPos = offsetPos;
        this.block = block;
        this.state = state;
    }

    public BlockType getBlock() {
        return block;
    }

    @Nullable
    public BlockState getState() {
        return state;
    }

    public Vector3i getTargetPos() {
        return targetPos;
    }

    public Vector3i getOffsetPos() {
        return offsetPos;
    }

    @Nullable
    public static BlockContext create(PlayerRef player, float dt, int index, ArchetypeChunk<EntityStore> archetypeChunk, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer, EyeSpyConfig config) {
        Ref<EntityStore> playerRef = player.getReference();
        if (playerRef == null) {
            return null;
        }
        Store<EntityStore> playerStore = playerRef.getStore();
        TransformComponent transform = playerStore.getComponent(playerRef, TransformComponent.getComponentType());
        World world = store.getExternalData().getWorld();
        final Vector3i targetBlockPos = TargetUtil.getTargetBlock(archetypeChunk.getReferenceTo(index), 5, commandBuffer);
        if (transform != null && targetBlockPos != null) {
            int x = targetBlockPos.x;
            int y = targetBlockPos.y;
            int z = targetBlockPos.z;

            long chunkIndex = ChunkUtil.indexChunkFromBlock(x, z);
            WorldChunk chunk = world.getChunkIfLoaded(chunkIndex);
            if (chunk == null) {
                return null;
            }

            Vector3i basePos = resolveBaseBlock(chunk, x, y, z);

            long baseChunkIndex = ChunkUtil.indexChunkFromBlock(basePos.x, basePos.z);
            WorldChunk baseChunk = baseChunkIndex == chunkIndex ? chunk : world.getChunkIfLoaded(baseChunkIndex);

            if (baseChunk == null) {
                return null;
            }

            BlockType block = baseChunk.getBlockType(basePos.x, basePos.y, basePos.z);
            BlockState state = baseChunk.getState(basePos.x, basePos.y, basePos.z);

            return new BlockContext(dt, index, archetypeChunk, store, commandBuffer, player, baseChunk, config, targetBlockPos, basePos, block, state);
        }
        return null;
    }

    public static Vector3i resolveBaseBlock(WorldChunk chunk, int x, int y, int z) {
        int filler = chunk.getFiller(x, y, z);
        if (filler == 0) {
            return new Vector3i(x, y, z);
        }

        return new Vector3i(x - FillerBlockUtil.unpackX(filler), y - FillerBlockUtil.unpackY(filler), z - FillerBlockUtil.unpackZ(filler));
    }
}