package com.jarhax.eyespy.impl.info;

import com.hypixel.hytale.builtin.crafting.state.BenchState;
import com.hypixel.hytale.builtin.crafting.state.ProcessingBenchState;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.FarmingData;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.asset.type.item.config.ItemTranslationProperties;
import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
import com.jarhax.eyespy.EyeSpy;
import com.jarhax.eyespy.api.MessageHelpers;
import com.jarhax.eyespy.api.context.BlockContext;
import com.jarhax.eyespy.api.info.InfoBuilder;
import com.jarhax.eyespy.api.info.InfoProvider;

import java.awt.*;

public class VanillaBlockInfoProvider implements InfoProvider<BlockContext> {

    private static final Color color = new Color(85, 85, 255);
    private static final Color color2 = new Color(170, 170, 170);

    @Override
    public void updateDescription(BlockContext context, InfoBuilder infoBuilder) {
        if (!context.getBlock().getId().equals("Empty")) {
            infoBuilder.setHeader(getDisplayName(context.getBlock()));

            final Item item = context.getBlock().getItem();
            if (item != null) {
                infoBuilder.setIcon(item.getId());
            }

            final String owner = EyeSpy.OWNERSHIP.get(context.getBlock().getId());
            if (owner != null) {
                infoBuilder.setFooter(Message.raw(owner).color(color).bold(true));
            }

            if (context.getState() instanceof BenchState bench) {
                infoBuilder.setBody(MessageHelpers.tier(bench.getTierLevel()).color(color2));
            }

            if (context.getState() instanceof ItemContainerState container) {
                infoBuilder.setBody(MessageHelpers.capacity(container.getItemContainer().getCapacity()).color(color2));
            }

            if (context.getState() instanceof ProcessingBenchState processor) {
                if (processor.isActive() && processor.getRecipe() != null) {
                    infoBuilder.setBody(MessageHelpers.progress((processor.getInputProgress() / processor.getRecipe().getTimeSeconds()) * 100f).color(color2));
                }
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
