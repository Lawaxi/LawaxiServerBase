package net.lawaxi.serverbase.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.lawaxi.serverbase.utils.config.configs;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import static net.lawaxi.serverbase.utils.Zip.zip;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class backup {

    static HashMap<GameProfile, BackupRequest> hm;

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

            .then(literal("list").executes(backup::listBackup))

            .then(
                literal("del")
                    .then(
                        literal("id")
                            .then(
                                argument("id", StringArgumentType.string())
                                    .executes(ctx -> deleteBackup(ctx, Integer.parseInt(StringArgumentType.getString(ctx, "id"))))
                            )
                    )
                    .then(
                        literal("name")
                            .then(
                                argument("name", StringArgumentType.string())
                                    .suggests((commandContext, suggestionsBuilder) -> CommandSource.suggestMatching(getBackupFileNames(true), suggestionsBuilder))
                                    .executes(ctx -> deleteBackup(ctx, StringArgumentType.getString(ctx, "name")))
                            )
                    )
            )

            .then(
                literal("back")
                    .executes(ctx -> {
                        tell(ctx, "back");
                        return 0;
                    })
            )

            .then(
                literal("confirm")
                    .executes(ctx -> {
                        tell(ctx, "confirm");
                        return 0;
                    })
            )

            .then(
                literal("abort")
                    .executes(ctx -> {
                        tell(ctx, "abort");
                        return 0;
                    })
            )

            .then(
                literal("help")
                    .executes(ctx -> {
                        tell(ctx, "help");
                        return 0;
                    })
            )

            // no arguments
            .executes(ctx -> {
                    tell(ctx, "null");
                return 0;
                }
            );
        dispatcher.register(literalArgumentBuilder);
    }

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd_HH-mm-ss");
        Date date = new Date();
        return sdf.format(date);
    }

    private static void tell(CommandContext<ServerCommandSource> ctx, String message) {
        try {
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            player.sendMessage(new LiteralText(message), false);
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
    }

    private static File[] getBackupFiles() {
        return getBackupFiles(false);
    }

    private static File[] getBackupFiles(boolean reverse) {
        File[] backups = configs.worldBackupFolder.listFiles();
        Arrays.sort(backups, (f1, f2) -> {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return reverse ? 1 : -1;
                else if (diff == 0)
                    return 0;
                else
                    return reverse ? -1 : 1;
            }
        );
        return backups;
    }

    private static ArrayList<String> getBackupFileNames() {
        return getBackupFileNames(false);
    }

    private static ArrayList<String> getBackupFileNames(boolean reverse) {
        ArrayList<String> names = new ArrayList<>();
        for (File file : getBackupFiles(reverse)) {
            names.add(file.getName());
        }
        return names;
    }

    private static int makeBackup(CommandContext<ServerCommandSource> ctx, String name) {
        File world = configs.worldFolder;
        File backupFolder = configs.worldBackupFolder;
        if (!backupFolder.exists()) backupFolder.mkdir();
        File backup = new File(backupFolder + "/" + name + ".zip");
        if (!backup.exists()) {
            zip(backup, world);
            tell(ctx, "创建备份成功， 文件名: " + name + ".zip");
            System.out.println(ctx.getSource().getName() + "制作了备份：" + name + ".zip");
            return 0;
        } else {
            tell(ctx, name + "已存在！");
            return -1;
        }
    }

    private static int listBackup(CommandContext<ServerCommandSource> ctx) {
        File[] backups = getBackupFiles();
        tell(ctx, "备份列表:");
        for (int i = 0; i < backups.length; ++i) {
            int x = i + 1;
            tell(ctx, "[" + x + "]" + " " + backups[i].getName());
        }
        return 0;
    }

    private static int deleteBackup(CommandContext<ServerCommandSource> ctx, String name) throws CommandSyntaxException {
        for (File backup : getBackupFiles()) {
            if (backup.getName().equals(name))
                deleteBackup(ctx, backup);
            return 0;
        }
        tell(ctx, "请输入正确的文件名!");
        return -1;
    }

    private static int deleteBackup(CommandContext<ServerCommandSource> ctx, int id) {
        try {
            File file = getBackupFiles()[id - 1];
            deleteBackup(ctx, file);
            return 0;
        } catch (IndexOutOfBoundsException e) {
            tell(ctx, "请输入正确的id!");
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static void deleteBackup(CommandContext<ServerCommandSource> ctx, File backup) throws CommandSyntaxException {

        if (!hm.containsKey(ctx.getSource().getPlayer().getGameProfile())) {
            hm.put(ctx.getSource().getPlayer().getGameProfile(), new BackupRequest(BackupRequest.Type.DELETE, backup));
            tell(ctx, "输入/backup confirm以确认删除");
            tell(ctx, "输入/backup abort以取消");
        } else {
            boolean success = backup.delete();
            hm.remove(ctx.getSource().getPlayer().getGameProfile());
            if (success) {
                tell(ctx, "删除成功");
            } else {
                tell(ctx, "删除失败");
            }
        }
    }
}

class BackupRequest {
    Type type;
    File file;

    BackupRequest(Type type1, File file1) {
        type = type1;
        file = file1;
    }

    enum Type {DELETE, BACK}
}
