package dev.arturidzze.vulkanbobby.mixin.bobby;

import de.johni0702.minecraft.bobby.FakeChunkManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.vulkanmod.render.chunk.ChunkStatusMap;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FakeChunkManager.class, remap = false)
public abstract class FakeChunkManagerMixin {

    @Inject(method = "load", at = @At("TAIL"))
    private void vb$onFakeChunkLoad(int x, int z, WorldChunk chunk, CallbackInfo ci) {
        if (ChunkStatusMap.INSTANCE != null) {
            ChunkStatusMap.INSTANCE.setChunkStatus(x, z, ChunkStatusMap.CHUNK_READY);
        }
    }

    @Inject(method = "unload", at = @At("TAIL"))
    private void vb$onFakeChunkUnload(int x, int z, boolean willBeReplaced, CallbackInfoReturnable<Boolean> cir) {
        if (ChunkStatusMap.INSTANCE != null) {
            ChunkStatusMap.INSTANCE.resetChunkStatus(x, z, ChunkStatusMap.CHUNK_READY);
        }
    }

    @Redirect(
            method = "load",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/world/ClientWorld;scheduleBlockRenders(III)V"
            ),
            remap = true,
            require = 0
    )
    private void vb$skipVanillaScheduleBlockRenders(ClientWorld world, int x, int y, int z) {
        // no-op: skip vanilla render call
    }
}
