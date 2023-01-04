package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;

public class spawn {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("spawn")
                .executes(ctx -> {

                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    ServerWorld overworld = player.getServer().getWorld(World.OVERWORLD);
                    WorldProperties worldProperties = overworld.getLevelProperties();
                    player.sendMessage(Text.literal(messages.get(1, player.getGameProfile().getName())), false);
                    player.sendMessage(Text.literal(messages.get(23, player.getGameProfile().getName())), true);
                    player.teleport(overworld, worldProperties.getSpawnX(), worldProperties.getSpawnY(), worldProperties.getSpawnZ(), 0, 0);

                    return 1;
                })
        );
    }
}
