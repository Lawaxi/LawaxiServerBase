package net.lawaxi.serverbase.commands;

import com.google.common.io.Files;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class sethome {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("sethome")
                        .then(CommandManager.argument(messages.m.get(17), StringArgumentType.string())
                                .executes(ctx -> {

                                    File homefolder = new File(configs.homefolder);
                                    if(!homefolder.exists())
                                        homefolder.mkdir();

                                    ServerPlayerEntity player =ctx.getSource().getPlayer();
                                    homefolder = new File(configs.homefolder+File.separator+player.getEntityName());
                                    if(!homefolder.exists())
                                        homefolder.mkdir();

                                    String homename=StringArgumentType.getString(ctx,messages.m.get(17));
                                    File homefile = new File(configs.homefolder+File.separator+player.getEntityName() +File.separator+homename+".yml");
                                    if(homefile.exists())
                                    {
                                        player.sendMessage(new LiteralText(messages.m.get(18).replace("%name%",homename)),false);
                                    }
                                    else
                                    {
                                        try{
                                            String world = setwarp.getWorld(player.world,player.getServer());
                                            if(world.equals("shit"))
                                            {
                                                ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.m.get(19)),false);
                                            }
                                            else
                                            {
                                                BufferedWriter buffer = Files.newWriter(homefile, StandardCharsets.UTF_8);

                                                buffer.write(world);
                                                buffer.newLine();
                                                buffer.write(String.valueOf(player.getX()));
                                                buffer.newLine();
                                                buffer.write(String.valueOf(player.getY()));
                                                buffer.newLine();
                                                buffer.write(String.valueOf(player.getZ()));

                                                buffer.close();
                                                ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.m.get(20).replace("%name%",homename)),false);
                                                ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.m.get(1).replace("%to%",homename)),true);
                                            }
                                        }
                                        catch (IOException e)
                                        {
                                        }
                                    }
                                    return 1;
                                }))
                        .executes(ctx -> {
                            ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.m.get(21)),false);
                            return 1;
                        })
        );
    }
}
