package com.parzivail.pswg.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public interface MinecraftClientAccessor
{
	@Accessor("pausedTickDelta")
	float getPausedTickDelta();
}
