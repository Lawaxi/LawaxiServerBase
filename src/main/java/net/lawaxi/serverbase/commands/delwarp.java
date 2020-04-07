package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.io.File;

public class delwarp {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("delwarp")
                        .then(CommandManager.argument("地标名称", StringArgumentType.string())
                                .executes(ctx -> {

                                    String warpname =StringArgumentType.getString(ctx,"地标名称");
                                    File warpfile = new File("Lawaxi"+File.separator+"warps"+File.separator+ warpname+".yml");
                                    if(warpfile.exists())
                                    {
                                        if(warpfile.delete())
                                        {
                                            ctx.getSource().getPlayer().sendMessage(new LiteralText("§a地标§2 "+warpname+" §a删除成功"));
                                        }
                                        else
                                        {
                                            ctx.getSource().getPlayer().sendMessage(new LiteralText("§c管理员禁止了地标§4 "+warpname+" §c的删除"));
                                        }
                                    }
                                    else
                                    {
                                        ctx.getSource().getPlayer().sendMessage(new LiteralText("§c地标§4 "+warpname+" §c不存在"));
                                    }
                                    return 1;
                                }))
                        .executes(ctx -> {
                            ctx.getSource().getPlayer().sendMessage(new LiteralText("§c请输入要删除的地标名称"));
                            return 1;
                        })
        );
    }
}
