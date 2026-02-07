package com.jarhax.eyespy.api.context;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
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
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.FillerBlockUtil;
import com.hypixel.hytale.server.core.util.TargetUtil;
import com.jarhax.eyespy.api.EyeSpyConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockContext extends Context {

    private final WorldChunk chunk;
    private final BlockType block;
    private final BlockState state;
    private final Ref<ChunkStore> ref;
    private final Vector3i targetPos;
    private final Vector3i blockPos;

    public BlockContext(float delta, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer, PlayerRef observer, WorldChunk chunk, EyeSpyConfig config, Vector3i targetPos, Vector3i offsetPos, BlockType block, BlockState state, Ref<ChunkStore> blockRef) {
        super(delta, store, commandBuffer, observer, config);
        this.chunk = chunk;
        this.targetPos = targetPos;
        this.blockPos = offsetPos;
        this.block = block;
        this.state = state;
        this.ref = blockRef;
    }

    public WorldChunk chunk() {
        return chunk;
    }

    public BlockType blockType() {
        return block;
    }

    @Nullable
    public BlockState blockState() {
        return state;
    }

    public Vector3i blockPos() {
        return blockPos;
    }

    public Vector3i targetPos() {
        return targetPos;
    }

    @Nullable
    public Ref<ChunkStore> ref() {
        return this.ref;
    }

    @Nullable
    public <T extends Component<ChunkStore>> T component(@Nonnull ComponentType<ChunkStore, T> componentType) {
        return this.isRefValid() ? this.world().getChunkStore().getStore().getComponent(this.ref, componentType) : null;
    }

    public boolean isRefValid() {
        return this.ref != null && this.ref.isValid();
    }

    @Nullable
    public static BlockContext create(PlayerRef player, float dt, int index, ArchetypeChunk<EntityStore> archetypeChunk, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer, EyeSpyConfig config) {
        final Ref<EntityStore> playerRef = player.getReference();
        if (playerRef != null) {
            final Store<EntityStore> playerStore = playerRef.getStore();
            final TransformComponent transform = playerStore.getComponent(playerRef, TransformComponent.getComponentType());
            final World world = store.getExternalData().getWorld();
            final Vector3i targetBlockPos = TargetUtil.getTargetBlock(archetypeChunk.getReferenceTo(index), 5, commandBuffer);
            if (transform != null && targetBlockPos != null) {
                final long targetChunkIndex = ChunkUtil.indexChunkFromBlock(targetBlockPos.x, targetBlockPos.z);
                final WorldChunk targetChunk = world.getChunkIfLoaded(targetChunkIndex);
                if (targetChunk != null) {
                    final Vector3i rootPos = resolveBaseBlock(targetChunk, targetBlockPos);
                    final long rootChunkIndex = ChunkUtil.indexChunkFromBlock(rootPos.x, rootPos.z);
                    final WorldChunk rootChunk = rootChunkIndex == targetChunkIndex ? targetChunk : world.getChunkIfLoaded(rootChunkIndex);
                    if (rootChunk != null) {
                        final BlockType block = rootChunk.getBlockType(rootPos.x, rootPos.y, rootPos.z);
                        final BlockState state = rootChunk.getState(rootPos.x, rootPos.y, rootPos.z);
                        final Ref<ChunkStore> blockRef = rootChunk.getBlockComponentEntity(rootPos.x, rootPos.y, rootPos.z);
                        return new BlockContext(dt, store, commandBuffer, player, rootChunk, config, targetBlockPos, rootPos, block, state, blockRef);
                    }
                }
            }
        }
        return null;
    }

    public static Vector3i resolveBaseBlock(WorldChunk chunk, Vector3i pos) {
        final int filler = chunk.getFiller(pos.x, pos.y, pos.z);
        return filler == 0 ? pos.clone() : new Vector3i(pos.x - FillerBlockUtil.unpackX(filler), pos.y - FillerBlockUtil.unpackY(filler), pos.z - FillerBlockUtil.unpackZ(filler));
    }
}