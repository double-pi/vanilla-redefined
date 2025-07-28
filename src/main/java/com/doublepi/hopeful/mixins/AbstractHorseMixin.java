package com.doublepi.hopeful.mixins;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.Random;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin {
    @Inject(method="finalizeSpawn",at=@At("TAIL"))
    public void finalize1(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir){
        RandomSource random = level.getRandom();
        AbstractHorse thisHorse = (AbstractHorse) (Object) this;

        thisHorse.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12 + 2 * random.nextGaussian());
        thisHorse.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.45f + 0.15 * random.nextGaussian());
        thisHorse.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(0.4f + 0.15 * random.nextGaussian());

    }

    @Inject(method="setOffspringAttribute",at=@At("HEAD"),cancellable = true)
    private void create1(AgeableMob parent, AbstractHorse child,
                                Holder<Attribute> attribute,
                                double min, double max, CallbackInfo ci){
        AbstractHorse thisHorse = (AbstractHorse) (Object) this;
        double value1 = thisHorse.getAttributeBaseValue(attribute);
        double value2 = parent.getAttributeValue(attribute);

        if(max<=min)
            throw new IllegalArgumentException("Incorrect Range for Attribute");

        double deviation = (max-min)/10;
        double newValue = (value1+value2)/2+deviation* thisHorse.getRandom().nextGaussian();
        child.getAttribute(attribute).setBaseValue(newValue);


        ci.cancel();
    }


}
