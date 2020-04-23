package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.utils.list;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.io.File;

public class delhome {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("delhome")
                        .then(CommandManager.argument("家的名称", StringArgumentType.string())
                                .executes(ctx -> {

                                    ServerPlayerEntity player =ctx.getSource().getPlayer();
                                    String homename=StringArgumentType.getString(ctx,"家的名称");
                                    File homefile = new File(list.homefolder+File.separator+player.getEntityName() +File.separator+homename+".yml");
                                    if(homefile.exists())
                                    {
                                        if(homefile.delete())
                                        {
                                            ctx.getSource().getPlayer().sendMessage(new LiteralText("§a家§2 "+homename+" §a删除成功"),false);
                                        }
                                        else
                                        {
                                            ctx.getSource().getPlayer().sendMessage(new LiteralText("§c管理员禁止了家§4 "+homename+" §c的删除"),false);
                                        }
                                    }
                                    else
                                    {
                                        ctx.getSource().getPlayer().sendMessage(new LiteralText("§c家§4 "+homename+" §c不存在"),false);
                                    }
                                    return 1;
                                }))
                        .executes(ctx -> {
                            ctx.getSource().getPlayer().sendMessage(new LiteralText("§c请输入要删除的家的名称"),false);
                            return 1;
                        })
        );
    }
}
