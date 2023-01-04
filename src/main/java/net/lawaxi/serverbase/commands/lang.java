package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class lang {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("lang")
                .then(CommandManager.argument("Language", StringArgumentType.string())
                        .suggests((ctx, suggestionsBuilder) -> CommandSource.suggestMatching(messages.getLangList(), suggestionsBuilder))
                        .executes(ctx -> {
                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            String lang = StringArgumentType.getString(ctx, "Language");

                            if (messages.getLangList().contains(lang)) {

                                if (messages.setLang(player.getGameProfile().getName(), lang)) {
                                    player.sendMessage(Text.literal(messages.get(76, player.getGameProfile().getName())
                                            .replace("%lang%", lang)
                                            .replace("%lang2%", messages.getByLang(-1,lang))), false);
                                } else {
                                    player.sendMessage(Text.literal(messages.get(77, player.getGameProfile().getName())
                                            .replace("%lang%", lang)
                                            .replace("%lang2%", messages.getByLang(-1,lang))), false);
                                }


                            } else {
                                player.sendMessage(Text.literal(messages.get(78, player.getGameProfile().getName())), false);

                            }

                            return 1;
                        }))
                .executes(ctx -> {

                    //当前语言
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    player.sendMessage(Text.literal(messages.get(79, player.getGameProfile().getName())
                            .replace("%lang%", messages.getLang(player.getGameProfile().getName()))
                            .replace("%lang2%", messages.get(-1,player.getGameProfile().getName()))), false);


                    //语言列表
                    String langlist = messages.get(73, player.getGameProfile().getName());
                    for (String a : messages.getLangList()) {
                        langlist += messages.get(74, player.getGameProfile().getName()).replace("%lang%", a) + messages.get(75, player.getGameProfile().getName());
                    }
                    player.sendMessage(Text.literal(langlist.substring(0, langlist.length() - messages.get(75, player.getGameProfile().getName()).length())), false);
                    return 1;
                })
        );
    }
}
