package com.jarhax.eyespy;

import com.hypixel.hytale.assetstore.AssetPack;
import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
import com.hypixel.hytale.server.core.asset.AssetModule;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.jarhax.eyespy.impl.hud.PlayerTickSystem;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EyeSpy extends JavaPlugin {

    public static final Map<String, String> OWNERSHIP = new HashMap<>();

    public EyeSpy(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();
        this.getEntityStoreRegistry().registerSystem(new PlayerTickSystem());
    }

    @Override
    protected void start() {
        OWNERSHIP.clear();
        final BlockTypeAssetMap<String, BlockType> blockTypes = BlockType.getAssetMap();
        for (AssetPack pack : AssetModule.get().getAssetPacks()) {
            final String ownerName = pack.getManifest().getGroup() + ":" + pack.getManifest().getName();
            final Set<String> blockTypeKeys = blockTypes.getKeysForPack(pack.getName());
            if (blockTypeKeys != null) {
                for (String entry : blockTypes.getKeysForPack(pack.getName())) {
                    OWNERSHIP.put(entry, ownerName);
                }
            }
        }
    }
}