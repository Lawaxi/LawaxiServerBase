package net.lawaxi.serverbase.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import java.util.UUID;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract Text getName();

    @Shadow public float pitch;
    @Shadow protected UUID uuid;

    @Shadow public abstract BlockPos getBlockPos();
}
