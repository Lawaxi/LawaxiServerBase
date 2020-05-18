package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.dimension.DimensionType;

public class here {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("here")
                        .executes(ctx -> {

                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            if(player.getServerWorld().getWorld()==player.getServer().getWorld(DimensionType.OVERWORLD)){

                                player.getServer().getPlayerManager().broadcastChatMessage(new LiteralText(messages.m.get(68)
                                        .replace("%player%",player.getGameProfile().getName())
                                        .replace("%x%",deal(player.getX()))
                                        .replace("%y%",deal(player.getY()))
                                        .replace("%z%",deal(player.getZ()))),false);
                            }
                            else if(player.getServerWorld().getWorld()==player.getServer().getWorld(DimensionType.THE_END)){
                                player.getServer().getPlayerManager().broadcastChatMessage(new LiteralText(messages.m.get(70)
                                        .replace("%player%",player.getGameProfile().getName())
                                        .replace("%x%",deal(player.getX()))
                                        .replace("%y%",deal(player.getY()))
                                        .replace("%z%",deal(player.getZ()))),false);
                            }
                            else if(player.getServerWorld().getWorld()==player.getServer().getWorld(DimensionType.THE_NETHER)){
                                player.getServer().getPlayerManager().broadcastChatMessage(new LiteralText(messages.m.get(69)
                                        .replace("%player%",player.getGameProfile().getName())
                                        .replace("%x%",deal(player.getX()))
                                        .replace("%y%",deal(player.getY()))
                                        .replace("%z%",deal(player.getZ()))),false);
                            }
                            else{
                                player.getServer().getPlayerManager().broadcastChatMessage(new LiteralText(messages.m.get(71)
                                        .replace("%x%",deal(player.getX()))
                                        .replace("%y%",deal(player.getY()))
                                        .replace("%z%",deal(player.getZ()))),false);
                            }
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING,60*20,1,false,false,false));

                            return 1;
                        })
        );
    }

    private static String deal(double a){
        return String.format("%.1f",a);
    }
}
