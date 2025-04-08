package com.doublepi.hopeful.tomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.enchantment.Enchantment;

public record Tome(Component title, TomeType tomeType, int maxLevel, int scorePerLevel, HolderSet<Enchantment> enchantments) {
    public static final Codec<Tome> CODEC =
            RecordCodecBuilder.create((tomeInstance ->tomeInstance.group(
                    ComponentSerialization.CODEC.fieldOf("title").forGetter(Tome::title),
                    TomeType.CODEC.fieldOf("type").forGetter(Tome::tomeType),
                    ExtraCodecs.intRange(0,255).fieldOf("max_level").forGetter(Tome::maxLevel),
                    ExtraCodecs.intRange(-16,16).fieldOf("score_per_level").forGetter(Tome::scorePerLevel),
                    RegistryCodecs.homogeneousList(Registries.ENCHANTMENT).fieldOf("enchantments").forGetter(Tome::enchantments))
                    .apply(tomeInstance, Tome::new)
            ));

    @Override
    public Component title() {
        return title;
    }

    @Override
    public TomeType tomeType() {
        return tomeType;
    }

    @Override
    public int maxLevel() {
        return maxLevel;
    }

    @Override
    public int scorePerLevel() {
        return scorePerLevel;
    }

    @Override
    public HolderSet<Enchantment> enchantments() {
        return enchantments;
    }
}

enum TomeType implements StringRepresentable{
    BLESSING("blessing", ChatFormatting.GREEN),
    CURSE("curse", ChatFormatting.DARK_RED),
    DEAL("deal", ChatFormatting.YELLOW);

    public static final Codec<TomeType> CODEC = StringRepresentable.fromEnum(TomeType::values);

    private final String name;
    private final ChatFormatting chatColor;
    private final Component displayName;

    private TomeType(String name, ChatFormatting chatColor) {
        this.name = name;
        this.chatColor = chatColor;
        this.displayName = Component.translatable("tome.hopeful.type." + name).withColor(chatColor.getColor());
    }
    public ChatFormatting getChatColor() {
        return this.chatColor;
    }

    public Component getDisplayName() {
        return this.displayName;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
