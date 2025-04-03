package com.doublepi.hopeful.tomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record TomeData(Tome tome) {
    public static final Codec<TomeData> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(Tome.CODEC.fieldOf("tome").forGetter(TomeData::tome))
                            .apply(instance, TomeData::new));


}
