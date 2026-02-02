package com.jarhax.eyespy.impl.util;

import com.hypixel.hytale.assetstore.AssetPack;
import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.server.core.asset.AssetModule;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.jarhax.eyespy.EyeSpy;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Owners {

    private static final Map<String, PluginIdentifier> BLOCKS = new HashMap<>();

    public static void reload() {
        final long start = System.nanoTime();
        BLOCKS.clear();
        for (AssetPack pack : AssetModule.get().getAssetPacks()) {
            final PluginIdentifier identifier = new PluginIdentifier(pack.getManifest().getGroup(), pack.getManifest().getName());
            final Set<String> blockTypeKeys = BlockType.getAssetMap().getKeysForPack(pack.getName());
            if (blockTypeKeys != null) {
                for (String entry : blockTypeKeys) {
                    BLOCKS.put(entry, identifier);
                }
            }
        }
        final long end = System.nanoTime();
        EyeSpy.LOGGER.atInfo().log("Determined owners for %d blocks. Took %fms", BLOCKS.size(), (end - start) / 1_000_000f);
    }

    @Nullable
    public static PluginIdentifier blockOwner(String blockId) {
        return BLOCKS.get(blockId);
    }
}