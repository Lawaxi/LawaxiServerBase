package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.shits.list;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class loadme {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("loadme")
                        .executes(ctx -> {
                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            String uuid = player.getUuid().toString();

                            if(player.getServer().getPlayerManager().getWhitelist().isAllowed(player.getGameProfile()))
                            {
                                File world = new File("world" + File.separator + "playerdata" + File.separator + uuid + ".dat");
                                File worldsave = new File("Lawaxi"+File.separator+"datasaves"+File.separator + uuid + ".dat");


                                if(!worldsave.exists())
                                {
                                    player.sendMessage(new LiteralText("§c您没有要载入的备份"));
                                }
                                else
                                {

                                    player.networkHandler.disconnect(new LiteralText("§a正在读取备份，请稍后连入"));

                                    //延时模块，网上搜的代码，似乎还挺管用
                                    new Timer().schedule(new TimerTask() {
                                        public void run() {
                                            try{
                                                world.delete();
                                                FileUtils.copyFile(worldsave,world);
                                            }
                                            catch (IOException e)
                                            {
                                                System.out.print("loadme 时出现问题.");
                                            }
                                            this.cancel();
                                        }
                                    }, 1000);
                                }
                            }
                            else
                            {
                                player.sendMessage(new LiteralText("§c很抱歉，备份功能需要白名单，请联系管理员获取"));
                            }
                            return 1;
                        })
        );
    }
}
