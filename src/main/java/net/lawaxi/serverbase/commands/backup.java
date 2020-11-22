package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class backup {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        final LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = literal("backup")
            .then(
                literal("make")
                    .then(
                        argument("comment", StringArgumentType.word())
                            .suggests((commandContext, suggestionsBuilder) -> CommandSource.suggestMatching(new String[]{getTime()}, suggestionsBuilder))
                            .executes(ctx -> makeBackup(ctx, StringArgumentType.getString(ctx, "comment")))
                    )
                    .executes(ctx -> makeBackup(ctx, getTime()))
            )

            .then(
                literal("list")
                    .executes(ctx -> {
                        tell(ctx, "list");
                        return 1;
                    })
            )

            .then(
                literal("del")
                    .executes(ctx -> {
                        tell(ctx, "del");
                        return 1;
                    })
            )

            .then(
                literal("back")
                    .executes(ctx -> {
                        tell(ctx, "back");
                        return 1;
                    })
            )

            .then(
                literal("confirm")
                    .executes(ctx -> {
                        tell(ctx, "confirm");
                        return 1;
                    })
            )

            .then(
                literal("abort")
                    .executes(ctx -> {
                        tell(ctx, "abort");
                        return 1;
                    })
            )

            .then(
                literal("help")
                    .executes(ctx -> {
                        tell(ctx, "help");
                        return 1;
                    })
            )

            // no arguments
            .executes(ctx -> {
                    tell(ctx, "null");
                    return 1;
                }
            );
        dispatcher.register(literalArgumentBuilder);
    }

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd-HH-mm");
        Date date = new Date();
        return sdf.format(date);
    }

    public static void zipMultiFile(String filepath, String zippath) {
        try {
            File file = new File(filepath);// 要被压缩的文件夹
            File zipFile = new File(zippath);
            InputStream input;
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File value : files) {
                    input = new FileInputStream(value);
                    zipOut.putNextEntry(new ZipEntry(file.getName() + File.separator + value.getName()));
                    int temp = 0;
                    while ((temp = input.read()) != -1) {
                        zipOut.write(temp);
                    }
                    input.close();
                }
            }
            zipOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void tell(CommandContext<ServerCommandSource> ctx, String message) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayer();
        player.sendMessage(new LiteralText(message), false);
    }

    private static int makeBackup(CommandContext<ServerCommandSource> ctx, String name) throws CommandSyntaxException {
        tell(ctx, "make backup, name: " + name);
        return 1;
    }
}
