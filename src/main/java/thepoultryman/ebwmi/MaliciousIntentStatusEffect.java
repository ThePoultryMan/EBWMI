package thepoultryman.ebwmi;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class MaliciousIntentStatusEffect extends StatusEffect {
	public MaliciousIntentStatusEffect() {
		super(StatusEffectType.NEUTRAL, 0x701212);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {

	}
}
