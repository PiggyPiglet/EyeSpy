package com.jarhax.eyespy;

import com.hypixel.hytale.builtin.crafting.state.BenchState;
import com.hypixel.hytale.builtin.crafting.state.ProcessingBenchState;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.asset.type.item.config.ItemTranslationProperties;
import com.hypixel.hytale.server.core.entity.EntityUtils;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.sensorinfo.CachedPositionProvider;
import com.jarhax.eyespy.api.context.BlockContext;
import org.bson.BsonDocument;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EyeSpyHud extends CustomUIHud {

    private Message header;
    private String body;
    private String footer;
    private String icon;

    public EyeSpyHud(@Nonnull PlayerRef playerRef) {
        super(playerRef);
    }

    public void updateHud(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {

        this.header = null;
        this.body = null;
        this.footer = null;
        this.icon = null;

        final Holder<EntityStore> holder = EntityUtils.toHolder(index, archetypeChunk);
        final Player player = holder.getComponent(Player.getComponentType());
        if (player != null && player.getWorld() != null) {
            final BlockContext blockContext = BlockContext.create(player, dt, index, archetypeChunk, store, commandBuffer);
            if (blockContext != null) {
                // TODO generate
                header = getDisplayName(blockContext.getBlock());
                if (blockContext.getState() instanceof BenchState bench) {
                    body = "Tier: " + bench.getTierLevel();
                }
                footer = blockContext.getBlock().getId();
                icon = blockContext.getBlock().getId();
            }
        }
    }

    @Override
    protected void build(@Nonnull UICommandBuilder commandBuilder) {
        if (isValid(header) && this.icon != null) {
            commandBuilder.append("Hud/Test.ui");
            commandBuilder.set("#Header.Text", this.header);
            setText(commandBuilder, "#Body", this.body);
            setText(commandBuilder, "#Footer", this.footer);
            setText(commandBuilder,"#Footer", this.footer);
            commandBuilder.set("#Icon.ItemId", this.icon);
        }
    }

    @Override
    public void update(boolean clear, @Nonnull UICommandBuilder commandBuilder) {
        super.update(clear, commandBuilder);

    }

    private static boolean isValid(Message message) {
        if (message != null) {
            final BsonDocument doc = message.toDocument();
            return !doc.isEmpty() && doc.isString("MessageId") && !doc.isString("Children") && !doc.isString("RawText");

        }
        return false;
    }

    private void setText(@Nonnull UICommandBuilder commandBuilder, @Nonnull String selector, @Nullable String value) {
        if(value == null) {
            commandBuilder.set(selector + ".Visible", false);
        } else {
            commandBuilder.set(selector + ".Visible", true);
            commandBuilder.set(selector + ".Text", value);
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
