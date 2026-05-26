package net.mervyn.testosterone.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import java.util.function.Supplier;

public class ConfigRegistry {
    private static TestosteroneConfig CONFIG;

    public static void register() {
        AutoConfig.register(TestosteroneConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(TestosteroneConfig.class).getConfig();
    }

    public static class ConfigValue<T> implements Supplier<T> {
        private final Supplier<T> supplier;
        public ConfigValue(Supplier<T> supplier) {
            this.supplier = supplier;
        }
        @Override
        public T get() {
            return supplier.get();
        }
    }

    public static final ConfigValue<Boolean> RENDER_BEARD = new ConfigValue<>(() -> CONFIG.client.Beard_Mustache);
    public static final ConfigValue<Boolean> RENDER_TESTOSTERONE_INVINCIBLE = new ConfigValue<>(() -> CONFIG.client.Render_While_Invincible);
    public static final ConfigValue<Integer> TESTOSTERONE_R_INVINCIBLE = new ConfigValue<>(() -> CONFIG.client.Red_Of_Invincible);
    public static final ConfigValue<Integer> TESTOSTERONE_G_INVINCIBLE = new ConfigValue<>(() -> CONFIG.client.Green_Of_Invincible);
    public static final ConfigValue<Integer> TESTOSTERONE_B_INVINCIBLE = new ConfigValue<>(() -> CONFIG.client.Blue_Of_Invincible);
    public static final ConfigValue<Boolean> RENDER_TESTOSTERONE_COOLDOWN = new ConfigValue<>(() -> CONFIG.client.Render_While_On_Cooldown);
    public static final ConfigValue<Integer> TESTOSTERONE_R_COOLDOWN = new ConfigValue<>(() -> CONFIG.client.Red_Of_Cooldown);
    public static final ConfigValue<Integer> TESTOSTERONE_G_COOLDOWN = new ConfigValue<>(() -> CONFIG.client.Green_Of_Cooldown);
    public static final ConfigValue<Integer> TESTOSTERONE_B_COOLDOWN = new ConfigValue<>(() -> CONFIG.client.Blue_Of_Cooldown);
    public static final ConfigValue<Boolean> RENDER_TRAIL = new ConfigValue<>(() -> CONFIG.client.Render_Trail);
    public static final ConfigValue<Double> TRAIL_MIN_RENDER_DISTANCE = new ConfigValue<>(() -> CONFIG.client.Minimum_Render_Distance);
    public static final ConfigValue<Double> TRAIL_OFFSET = new ConfigValue<>(() -> CONFIG.client.Trail_Offset);

    public static final ConfigValue<Integer> TESTOSTERONE_DURATION = new ConfigValue<>(() -> CONFIG.server.Duration);
    public static final ConfigValue<Integer> TESTOSTERONE_MULTIPLIER = new ConfigValue<>(() -> CONFIG.server.Multiplier);
    public static final ConfigValue<Integer> TESTOSTERONE_MAX_DAMAGE = new ConfigValue<>(() -> CONFIG.server.Damage_Limit);
    public static final ConfigValue<Integer> MAX_SPEED = new ConfigValue<>(() -> CONFIG.server.Max_Speed);
    public static final ConfigValue<Integer> ABILITY_SPEED = new ConfigValue<>(() -> CONFIG.server.Ability_Speed);
    public static final ConfigValue<Double> JUMP_MULTIPLIER = new ConfigValue<>(() -> CONFIG.server.Jump_Height);
    public static final ConfigValue<Double> SPEED_MULTIPLIER = new ConfigValue<>(() -> CONFIG.server.Speed_Multiplier);
    public static final ConfigValue<Integer> JOHN_ROCK_LIMIT = new ConfigValue<>(() -> CONFIG.server.Powered_Amount);
    public static final ConfigValue<Boolean> JOHN_ROCK_VERTICAL = new ConfigValue<>(() -> CONFIG.server.Vertical);
    public static final ConfigValue<Integer> JOHN_ROCK_RANGE = new ConfigValue<>(() -> CONFIG.server.Conversion_Range);
    public static final ConfigValue<Double> RAT_BOOST_MULTIPLIER = new ConfigValue<>(() -> CONFIG.server.Rat_Boost_Multiplier);
    public static final ConfigValue<Boolean> DISPLAY_SPEED = new ConfigValue<>(() -> CONFIG.client.Display_Speed);
    public static final ConfigValue<Double> TREN_IN_AIR_MUL = new ConfigValue<>(() -> CONFIG.server.In_Air_Multiplier);
    public static final ConfigValue<Integer> TRAIL_DURATION = new ConfigValue<>(() -> CONFIG.server.Trail_Duration);
    public static final ConfigValue<Double> FALL_DAMAGE_RADIUS = new ConfigValue<>(() -> CONFIG.server.Ground_Slam_Radius);
    public static final ConfigValue<Boolean> ALLOW_ELYTRA = new ConfigValue<>(() -> CONFIG.server.Allow_Elytra);
}


