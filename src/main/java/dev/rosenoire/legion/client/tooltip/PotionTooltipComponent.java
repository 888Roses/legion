package dev.rosenoire.legion.client.tooltip;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import dev.rosenoire.legion.fundation.util.RomanNumber;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SuppressWarnings("FieldCanBeLocal")
public class PotionTooltipComponent implements TooltipComponent {
    private final PotionTooltipData tooltipData;
    private final RenderableStatusEffect[] renderableStatusEffects;

    public PotionTooltipComponent(PotionTooltipData tooltipData) {
        this.tooltipData = tooltipData;

        renderableStatusEffects = this.tooltipData
                .getEffects()
                .stream()
                .map(instance -> RenderableStatusEffect.fromInstance(
                        instance,
                        this.tooltipData.getDurationMultiplier(),
                        this.tooltipData.getChance(instance)
                ))
                .toArray(RenderableStatusEffect[]::new);
    }

    @Override
    public int getHeight(TextRenderer textRenderer) {
        return 20 * this.renderableStatusEffects.length + 3;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return 24 + Math.max(
                Arrays.stream(this.renderableStatusEffects)
                        .map(this::getPotionStatsText)
                        .map(text -> textRenderer.getWidth(text.asOrderedText()))
                        .max(Integer::compare)
                        .orElse(0),
                Arrays.stream(this.renderableStatusEffects)
                        .map(this::getNameText)
                        .map(text -> textRenderer.getWidth(text.asOrderedText()))
                        .max(Integer::compare)
                        .orElse(0)
        );
    }

    private Text getNameText(RenderableStatusEffect renderable) {
        MutableText nameText = Text.translatable(renderable.nameTranslationKey());
        MutableText levelText = Text.literal(" " + RomanNumber.toRoman(renderable.amplifier() + 1));
        MutableText chanceText = Text.literal("");

        if (renderable.chance < 1) {
            int chanceInt = Math.round(renderable.chance * 100);
            chanceText = Text.literal(" (" + chanceInt + "%)").formatted(Formatting.DARK_GRAY);
        }

        return nameText.append(levelText).withColor(renderable.effect().value().getColor()).append(chanceText);
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, int width, int height, DrawContext context) {
        int stackHeight = -1;

        for (RenderableStatusEffect statusEffect : this.renderableStatusEffects) {
            context.drawGuiTexture(
                    RenderPipelines.GUI_TEXTURED,
                    InGameHud.getEffectTexture(statusEffect.effect()),
                    x + 2, y + 2 + stackHeight, 18, 18, 0xffFFFFFF
            );

            context.drawText(
                    textRenderer,
                    getNameText(statusEffect),
                    x + 18 + 2 + 4,
                    y + 2 + stackHeight,
                    0xFFFFFFFF,
                    false
            );

            MutableText bottomText = this.getPotionStatsText(statusEffect);

            context.drawText(
                    textRenderer,
                    bottomText.formatted(Formatting.GRAY),
                    x + 18 + 2 + 4,
                    y + 2 + stackHeight + textRenderer.fontHeight,
                    0xFFFFFFFF,
                    false
            );

            stackHeight += 18 + 2;
        }
    }

    private MutableText getPotionStatsText(RenderableStatusEffect effect) {
        MutableText text = effect.durationText().copy().append(" ");

        Map<RegistryEntry<EntityAttribute>, List<EntityAttributeModifier>> entityAttributeModifiers = new HashMap<>();

        for (var entry : effect.effect().value().attributeModifiers.entrySet()) {
            if (!entityAttributeModifiers.containsKey(entry.getKey())) {
                entityAttributeModifiers.put(entry.getKey(), new ArrayList<>());
            }

            entityAttributeModifiers.get(entry.getKey()).add(entry.getValue().createAttributeModifier(effect.amplifier()));
        }

        if (entityAttributeModifiers.isEmpty()) {
            return text;
        }

        for (Map.Entry<RegistryEntry<EntityAttribute>, List<EntityAttributeModifier>> entry : entityAttributeModifiers.entrySet()) {
            RegistryEntry<EntityAttribute> attribute = entry.getKey();
            for (EntityAttributeModifier modifier : entry.getValue()) {
                double value = modifier.value();
                double effectiveValue = modifier.value();
                if (modifier.operation() == EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE || modifier.operation() == EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                    effectiveValue *= 100;
                }

                if (value > 0.0) {
                    text.append(Text.translatable(
                            "attribute.modifier.plus." + modifier.operation().getId(),
                            AttributeModifiersComponent.DECIMAL_FORMAT.format(effectiveValue),
                            Text.translatable(attribute.value().getTranslationKey())
                    ).formatted(Formatting.BLUE));
                } else if (value < 0.0) {
                    effectiveValue *= -1.0;
                    text.append(Text.translatable(
                            "attribute.modifier.take." + modifier.operation().getId(),
                            AttributeModifiersComponent.DECIMAL_FORMAT.format(effectiveValue),
                            Text.translatable(attribute.value().getTranslationKey())
                    ).formatted(Formatting.RED));
                }

                text = text.append(" ");
            }
        }

        return text;
    }

    private record RenderableStatusEffect(RegistryEntry<StatusEffect> effect, String nameTranslationKey, Text durationText, int amplifier, float chance) {
        public static @Nullable RenderableStatusEffect fromInstance(StatusEffectInstance instance, double durationMultiplier, float chance) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world == null) return null;
            float tickRate = client.world.getTickManager().getTickRate();

            Text durationText = StatusEffectUtil.getDurationText(
                    instance,
                    (float) durationMultiplier,
                    tickRate
            );

            return new RenderableStatusEffect(instance.getEffectType(), instance.getTranslationKey(), durationText, instance.getAmplifier(), chance);
        }
    }
}
