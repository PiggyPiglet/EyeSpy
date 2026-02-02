package com.jarhax.eyespy.impl.command;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jarhax.eyespy.impl.ui.ConfigUI;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class ConfigCommand extends AbstractAsyncCommand {

    @Nonnull
    private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");

    public ConfigCommand() {
        super("eyespy", "server.eyespy.command");
        addAliases("es");
        this.setPermissionGroup(GameMode.Adventure);
    }

    @Nonnull
    @Override
    protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
        if (context.isPlayer()) {
            Ref<EntityStore> playerRef = context.senderAsPlayerRef();
            if (playerRef != null && playerRef.isValid()) {
                Store<EntityStore> store = playerRef.getStore();
                World world = store.getExternalData().getWorld();
                return CompletableFuture.runAsync(() -> {
                    Player playerComponent = store.getComponent(playerRef, Player.getComponentType());
                    PlayerRef playerRefComponent = store.getComponent(playerRef, PlayerRef.getComponentType());

                    if (playerComponent != null && playerRefComponent != null) {
                        playerComponent.getPageManager().openCustomPage(playerRef, store, new ConfigUI(playerRefComponent));
                    }
                }, world);
            } else {
                context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
                return CompletableFuture.completedFuture(null);
            }
        } else {
            return CompletableFuture.completedFuture(null);
        }
    }
}
