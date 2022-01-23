package thepoultryman.ebwmi;

import net.minecraft.entity.damage.DamageSource;

public class DamageSources extends DamageSource {
    public static final DamageSource SOUND = new DamageSources("sound");

    protected DamageSources(String string) {
        super(string);
    }
}
