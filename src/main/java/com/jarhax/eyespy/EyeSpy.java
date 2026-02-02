package com.jarhax.eyespy;

import com.hypixel.hytale.assetstore.AssetPack;
import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.asset.AssetModule;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.plugin.PluginBase;
import com.hypixel.hytale.server.core.plugin.PluginManager;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jarhax.eyespy.api.hud.HudProvider;
import com.jarhax.eyespy.api.hud.LayoutMode;
import com.jarhax.eyespy.api.hud.MultiHudProvider;
import com.jarhax.eyespy.api.hud.VanillaHudProvider;
import com.jarhax.eyespy.api.info.AnchorBuilder;
import com.jarhax.eyespy.impl.command.ConfigCommand;
import com.jarhax.eyespy.impl.component.EyeSpyComponent;
import com.jarhax.eyespy.impl.hud.PlayerTickSystem;
import com.jarhax.eyespy.impl.util.Reflect;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EyeSpy extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    public static final Map<String, String> OWNERSHIP = new HashMap<>();
    public static final AnchorBuilder DEFAULT_HUD_POSITION = new AnchorBuilder().setLeft(20).setTop(20);

    public static HudProvider provider = new VanillaHudProvider();

    public static ComponentType<EntityStore, EyeSpyComponent> eyeSpyComponentType;

    public EyeSpy(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();
        this.getEntityStoreRegistry().registerSystem(new PlayerTickSystem());
        this.getCommandRegistry().registerCommand(new ConfigCommand());
        EyeSpy.eyeSpyComponentType = this.getEntityStoreRegistry().registerComponent(EyeSpyComponent.class, "EyeSpy", EyeSpyComponent.CODEC);
    }

    @Override
    protected void start() {
        PluginBase plugin = PluginManager.get().getPlugin(PluginIdentifier.fromString("Buuz135:MultipleHUD"));
        if (plugin != null) {
            EyeSpy.provider = new MultiHudProvider();
        }
        final long start = System.nanoTime();
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
        final long end = System.nanoTime();
        LOGGER.atInfo().log("Determined owners for %d blocks. Took %fms", OWNERSHIP.size(), (end - start) / 1_000_000f);
        Reflect.UICommandBuilder_.CODEC_MAP.get().put(LayoutMode.class, LayoutMode.CODEC);
    }

}