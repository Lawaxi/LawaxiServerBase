package net.lawaxi.serverbase.utils;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LocationInfo {
    public BlockPos position;
    public ServerWorld world;
    public float yaw;
    public float pitch;

    public LocationInfo() {
    }

    // the location of the player
    public LocationInfo(ServerPlayerEntity player) {
        position = player.getBlockPos();
        world = player.getWorld();
        yaw = player.getHeadYaw();
        pitch = player.getPitch(1);
    }

    // the location in the file in the ServerWorld the player is in
    public LocationInfo(File file, ServerPlayerEntity player) throws BrokenPositionException {
        if (file.exists()) {
            try {
                FileInputStream fos = new FileInputStream(file);
                BufferedReader buffer = new BufferedReader(new InputStreamReader(fos, StandardCharsets.UTF_8));

                String stringWorld = buffer.readLine();
                String stringX = buffer.readLine();
                String stringY = buffer.readLine();
                String stringZ = buffer.readLine();
                String stringYaw = buffer.readLine();
                String stringPitch = buffer.readLine();

                buffer.close();

                if (stringWorld == null) throw new BrokenPositionException("Broken Position: world");
                if (stringX == null) throw new BrokenPositionException("Broken Position: X");
                if (stringY == null) throw new BrokenPositionException("Broken Position: Y");
                if (stringZ == null) throw new BrokenPositionException("Broken Position: Z");
                if (stringYaw == null) stringYaw = "0";
                if (stringPitch == null) stringPitch = "0";

                world = WorldDescription.getWorld(stringWorld, player.getServer());
                double x = Double.parseDouble(stringX);
                double y = Double.parseDouble(stringY);
                double z = Double.parseDouble(stringZ);
                position = new BlockPos(x, y, z);
                yaw = Float.parseFloat(stringYaw);
                pitch = Float.parseFloat(stringPitch);

            } catch (IOException ignored) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else throw new BrokenPositionException("File does not exist");
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public double getZ() {
        return position.getZ();
    }

    public void teleport(ServerPlayerEntity player) {
        player.teleport(
                this.world,
                this.getX(),
                this.getY(),
                this.getZ(),
                this.yaw,
                this.pitch
        );
    }
}

