package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import org.apache.commons.io.FileUtils;

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
                                File worldsave = new File(configs.backupfolder+File.separator + uuid + ".dat");


                                if(!worldsave.exists())
                                {
                                    player.sendMessage(new LiteralText(messages.m.get(59)),false);
                                }
                                else
                                {

                                    player.networkHandler.disconnect(new LiteralText(messages.m.get(60)));

                                    //延时模块，网上搜的代码，似乎还挺管用
                                    new Timer().schedule(new TimerTask() {
                                        public void run() {
                                            try{
                                                world.delete();
                                                FileUtils.copyFile(worldsave,world);
                                            }
                                            catch (IOException e)
                                            {
                                            }
                                            this.cancel();
                                        }
                                    }, 1000);
                                }
                            }
                            else
                            {
                                player.sendMessage(new LiteralText(messages.m.get(57)),true);
                            }
                            return 1;
                        })
        );
    }
}
