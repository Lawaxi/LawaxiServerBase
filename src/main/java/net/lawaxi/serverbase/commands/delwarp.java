package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.io.File;

public class delwarp {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("delwarp")
                        .then(CommandManager.argument(messages.m.get(42), StringArgumentType.string())
                                .executes(ctx -> {

                                    String warpname =StringArgumentType.getString(ctx,messages.m.get(42));
                                    File warpfile = new File(configs.warpfolder+File.separator+ warpname+".yml");
                                    if(warpfile.exists())
                                    {
                                        if(warpfile.delete())
                                        {
                                            ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.m.get(43).replace("%name%",warpname)),false);
                                        }
                                        else
                                        {
                                            ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.m.get(44).replace("%name%",warpname)),false);
                                        }
                                    }
                                    else
                                    {
                                        ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.m.get(45).replace("%name%",warpname)),false);
                                    }
                                    return 1;
                                }))
                        .executes(ctx -> {
                            ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.m.get(46)),false);
                            return 1;
                        })
        );
    }
}
