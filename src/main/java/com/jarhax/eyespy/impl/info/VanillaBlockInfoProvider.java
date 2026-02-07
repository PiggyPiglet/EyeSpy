package com.jarhax.eyespy.impl.info;

import com.hypixel.hytale.builtin.adventure.teleporter.component.Teleporter;
import com.hypixel.hytale.builtin.crafting.state.BenchState;
import com.hypixel.hytale.builtin.crafting.state.ProcessingBenchState;
import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.asset.type.item.config.ItemTranslationProperties;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
import com.jarhax.eyespy.api.MessageHelpers;
import com.jarhax.eyespy.api.context.BlockContext;
import com.jarhax.eyespy.api.info.InfoBuilder;
import com.jarhax.eyespy.api.info.InfoProvider;
import com.jarhax.eyespy.api.ui.Group;
import com.jarhax.eyespy.api.ui.ItemGrid;
import com.jarhax.eyespy.api.ui.ItemIcon;
import com.jarhax.eyespy.api.ui.Label;
import com.jarhax.eyespy.api.ui.ProgressBar;
import com.jarhax.eyespy.api.ui.UIElement;
import com.jarhax.eyespy.api.util.Identifier;
import com.jarhax.eyespy.impl.util.Owners;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VanillaBlockInfoProvider implements InfoProvider<BlockContext> {

    private static final Color INFO_COLOR = new Color(170, 170, 170);
    private static final Color OWNER_COLOR = new Color(85, 85, 255);

    public static final Identifier ICON = Identifier.EYE_SPY.of("Icon");
    public static final Identifier NAME = Identifier.EYE_SPY.of("Name");
    public static final Identifier TELEPORTER_NAME = Identifier.EYE_SPY.of("TeleporterName");
    public static final Identifier TELEPORTER_POS = Identifier.EYE_SPY.of("TeleporterPos");
    public static final Identifier BENCH = Identifier.EYE_SPY.of("BenchInfo");
    public static final Identifier BENCH_TIER = Identifier.EYE_SPY.of("BenchTier");
    public static final Identifier BENCH_PROGRESS = Identifier.EYE_SPY.of("BenchProgress");
    public static final Identifier CONTAINER_INVENTORY = Identifier.EYE_SPY.of("ContainerInventory");
    public static final Identifier OWNER = Identifier.EYE_SPY.of("Owner");

    @Override
    public void updateDescription(BlockContext context, InfoBuilder info) {
        if (!context.blockType().getId().equals("Empty")) {
            // Block Name
            info.header(new Label(NAME, getDisplayName(context.blockType()), 24));

            // Block Icon
            final Item item = context.blockType().getItem();
            if (item != null) {
                info.icon(new ItemIcon(ICON, item.getId()));
            }

            // Teleporter Info
            if (context.component(Teleporter.getComponentType()) instanceof Teleporter teleporter) {
                if (teleporter.getOwnedWarp() != null) {
                    info.body(new Label(TELEPORTER_NAME, Message.raw("Name: " + teleporter.getOwnedWarp())));
                }
                if (teleporter.getWarp() != null) {
                    info.body(new Label(TELEPORTER_POS, Message.raw("Destination: " + teleporter.getWarp())));
                }
            }

            // Bench Info
            if (context.blockState() instanceof BenchState bench) {
                final List<UIElement> values = new ArrayList<>();
                values.add(new Label(BENCH_TIER, MessageHelpers.tier(bench.getTierLevel()).color(INFO_COLOR)));
                if (bench instanceof ProcessingBenchState processor && context.getConfig().showProcessingTimes() && processor.isActive() && processor.getRecipe() != null && !Float.isNaN(processor.getInputProgress()) && !Float.isNaN(processor.getRecipe().getTimeSeconds())) {
                    values.add(new ProgressBar(BENCH_PROGRESS, processor.getInputProgress() / processor.getRecipe().getTimeSeconds()));
                }
                info.body(new Group(BENCH, values));
            }

            // Container Info
            if (context.blockState() instanceof ItemContainerState containerState && context.getConfig().showContainers()) {
                final ItemContainer container = containerState.getItemContainer();
                final Map<String, Integer> stackCounts = new HashMap<>();
                for (short slot = 0; slot < container.getCapacity(); slot++) {
                    final ItemStack stack = container.getItemStack(slot);
                    if (stack != null && stack.isValid()) {
                        stackCounts.merge(stack.getItemId(), stack.getQuantity(), Integer::sum);
                    }
                }
                info.body(new ItemGrid(CONTAINER_INVENTORY, stackCounts.entrySet().stream().map(e -> new ItemStack(e.getKey(), e.getValue())).toList()));
            }

            // Owner Info
            final PluginIdentifier owner = Owners.blockOwner(context.blockType().getId());
            if (owner != null) {
                info.footer(new Label(OWNER, Message.raw(owner.getName()).color(OWNER_COLOR).bold(true)));
            }
        }
    }

    private static Message getDisplayName(BlockType type) {
        final Item item = type.getItem();
        if (item != null) {
            final ItemTranslationProperties translations = item.getTranslationProperties();
            if (translations != null) {
                final String nameKey = translations.getName();
                if (nameKey != null) {
                    return Message.translation(nameKey);
                }
            }
        }
        return Message.raw(type.getId());
    }
}
