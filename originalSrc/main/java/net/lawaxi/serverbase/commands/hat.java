package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class hat {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("hat")
                .executes(ctx -> {

                    ServerPlayerEntity player = ctx.getSource().getPlayer();

                    ItemStack lasthand = player.getMainHandStack();
                    int slot = player.getInventory().main.indexOf(lasthand);

                    if (!lasthand.getItem().equals(Items.AIR)) {
                        ItemStack lasthead = player.getInventory().armor.get(3);

                        player.getInventory().armor.set(3, lasthand);
                        player.getInventory().main.set(slot, lasthead);

                        player.sendMessage(new LiteralText(messages.get(54, player.getGameProfile().getName())), false);
                    } else {
                        player.sendMessage(new LiteralText(messages.get(53, player.getGameProfile().getName())), false);
                    }
                    return 1;
                })
        );
    }
}
