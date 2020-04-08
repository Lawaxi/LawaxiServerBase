package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

public class hat {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("hat")
                        .executes(ctx -> {

                            ServerPlayerEntity player = ctx.getSource().getPlayer();

                            ItemStack lasthand = player.getMainHandStack();
                            int slot =player.inventory.main.indexOf(lasthand);

                            if(!lasthand.getItem().equals(Items.AIR))
                            {
                                ItemStack lasthead =player.inventory.armor.get(3);

                                player.inventory.armor.set(3,lasthand);
                                player.inventory.main.set(slot,lasthead);

                                player.sendMessage(new LiteralText("§a享受你的新帽子吧~"));
                            }
                            else
                            {
                                player.sendMessage(new LiteralText("§c您手中没有物品"));
                            }
                            return 1;
                        })
        );
    }
}
