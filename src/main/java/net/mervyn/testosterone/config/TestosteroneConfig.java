package net.mervyn.testosterone.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "testosterone")
public class TestosteroneConfig implements ConfigData {

    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    public Client client = new Client();

    @ConfigEntry.Category("server")
    @ConfigEntry.Gui.TransitiveObject
    public Server server = new Server();

    public static class Client {
        @Comment("Whether to render a beard and mustache cosmetic on the player. (Type: boolean)")
        public boolean Beard_Mustache = true;
        @Comment("Whether to render the invincibility frame visual overlay. (Type: boolean)")
        public boolean Render_While_Invincible = true;
        @Comment("Whether to render the ability cooldown visual overlay. (Type: boolean)")
        public boolean Render_While_On_Cooldown = true;
        @Comment("RGB red color channel for the invincibility overlay. (Type: int, 0-255)")
        public int Red_Of_Invincible = 255;
        @Comment("RGB green color channel for the invincibility overlay. (Type: int, 0-255)")
        public int Green_Of_Invincible = 209;
        @Comment("RGB blue color channel for the invincibility overlay. (Type: int, 0-255)")
        public int Blue_Of_Invincible = 119;
        @Comment("RGB red color channel for the cooldown overlay. (Type: int, 0-255)")
        public int Red_Of_Cooldown = 255;
        @Comment("RGB green color channel for the cooldown overlay. (Type: int, 0-255)")
        public int Green_Of_Cooldown = 255;
        @Comment("RGB blue color channel for the cooldown overlay. (Type: int, 0-255)")
        public int Blue_Of_Cooldown = 0;
        @Comment("Whether to display the running speed indicator on the HUD. (Type: boolean)")
        public boolean Display_Speed = false;
        @Comment("Whether to render particle/visual trails behind the player. (Type: boolean)")
        public boolean Render_Trail = true;
        @Comment("Minimum distance in blocks for visual trail segments to render. (Type: double)")
        public double Minimum_Render_Distance = 2.0;
        @Comment("Positional offset of the visual trail. (Type: double)")
        public double Trail_Offset = 0.0;
    }

    public static class Server {
        @Comment("Duration of the Man Power effect in ticks. (Type: int)")
        public int Duration = 40;
        @Comment("The power multiplier scaling the Man Power effect. (Type: int)")
        public int Multiplier = 10;
        @Comment("Maximum damage threshold that can be negated/absorbed by the Man Power effect. (Type: int)")
        public int Damage_Limit = 100;
        @Comment("Maximum running speed cap under Roid Rage. (Type: int)")
        public int Max_Speed = 200;
        @Comment("Activation speed threshold for custom abilities. (Type: int)")
        public int Ability_Speed = 50;
        @Comment("Height/velocity modifier applied to the custom super jump. (Type: double)")
        public double Jump_Height = 0.012;
        @Comment("Acceleration/speed modifier applied when sprinting during Roid Rage. (Type: double)")
        public double Speed_Multiplier = 0.0015;
        @Comment("Physics speed multiplier while in the air. (Type: double)")
        public double In_Air_Multiplier = 0.5;
        @Comment("Lifespan of particles/visual trails generated behind players during roid runs. (Type: int)")
        public int Trail_Duration = 5;
        @Comment("Impact radius of the ground slam shockwave. (Type: double)")
        public double Ground_Slam_Radius = 4.0;
        @Comment("Whether players are allowed to use Elytra during Roid Rage dashes. (Type: boolean)")
        public boolean Allow_Elytra = false;
        @Comment("Block limit of connected John Rocks toggled in a single redstone cascade. (Type: int)")
        public int Powered_Amount = 4096;
        @Comment("Whether John Rock propagation is allowed vertically. (Type: boolean)")
        public boolean Vertical = false;
        @Comment("Range in blocks for block conversion mechanics. (Type: int)")
        public int Conversion_Range = 3;
        @Comment("Movement speed boost multiplier applied when riding the Stupid Rat. (Type: double)")
        public double Rat_Boost_Multiplier = 5.0;
    }
}
