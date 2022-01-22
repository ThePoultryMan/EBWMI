package thepoultryman.ebwmi.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import thepoultryman.ebwmi.Ebwmi;
import thepoultryman.ebwmi.statuseffects.MaliciousIntentStatusEffect;

public class StatusEffectRegistry {
    public static final StatusEffect MALICIOUS_INTENT_STATUS_EFFECT = new MaliciousIntentStatusEffect();

    public static void registerStatusEffects() {
        register("malicious_intent", MALICIOUS_INTENT_STATUS_EFFECT);
    }

    private static void register(String name, StatusEffect statusEffect) {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(Ebwmi.MOD_ID, name), statusEffect);
    }
}
