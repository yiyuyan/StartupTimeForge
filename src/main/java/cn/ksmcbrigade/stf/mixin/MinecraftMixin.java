package cn.ksmcbrigade.stf.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.management.ManagementFactory;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow @Final private ToastComponent toast;
    @Unique
    public boolean startupTimeForge$startup = true;

    @Inject(method = "onGameLoadFinished",at = @At("TAIL"))
    public void finished(CallbackInfo ci){
        if(!startupTimeForge$startup) return;

        long timeMillis = ManagementFactory.getRuntimeMXBean().getUptime();
        float time = ((float) (timeMillis / 1000.0D));
        Component title = Component.translatable("stf.time",time);
        SystemToast.addOrUpdate(this.toast, SystemToast.SystemToastIds.PERIODIC_NOTIFICATION, title,Component.empty());
        startupTimeForge$startup = false;
    }
}
