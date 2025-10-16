package dev.arturidzze.vulkanbobby.mixin.vulkanmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.vulkanmod.config.option.Option;
import net.vulkanmod.config.option.Options;
import net.vulkanmod.config.gui.OptionBlock;
import net.vulkanmod.config.option.RangeOption;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import de.johni0702.minecraft.bobby.Bobby;

@Mixin(value = Options.class, remap = false)
public abstract class OptionsMixin {
    @Inject(method = "getGraphicsOpts", at = @At("RETURN"), cancellable = true)
    private static void vb$patchOnReturn(CallbackInfoReturnable<OptionBlock[]> cir) {
        OptionBlock[] blocks = cir.getReturnValue();
        vb$replaceRenderDistance(blocks);
        cir.setReturnValue(blocks);
    }

    @Unique
    private static void vb$replaceRenderDistance(OptionBlock[] blocks) {
        if (blocks == null || blocks.length == 0) return;

        Option<?>[] opts = blocks[0].options();
        if (opts == null || opts.length == 0) return;
        if (!(opts[0] instanceof RangeOption)) return;

        GameOptions go = MinecraftClient.getInstance().options;
        int cfgMax = Bobby.getInstance().getConfig().getMaxRenderDistance();
        if (cfgMax <= 0) return;

        RangeOption replacement = new RangeOption(
                Text.translatable("options.renderDistance"),
                2,
                cfgMax,
                1,
                v -> go.getViewDistance().setValue(v),
                () -> go.getViewDistance().getValue()
        );

        opts[0] = replacement;
    }
}