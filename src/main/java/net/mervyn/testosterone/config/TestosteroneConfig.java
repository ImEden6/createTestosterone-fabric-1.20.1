package net.mervyn.testosterone.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "testosterone")
public class TestosteroneConfig implements ConfigData {

    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    public Client client = new Client();

    @ConfigEntry.Category("server")
    @ConfigEntry.Gui.TransitiveObject
    public Server server = new Server();

    public static class Client {
        public boolean Beard_Mustache = true;
        public boolean Render_While_Invincible = true;
        public boolean Render_While_On_Cooldown = true;
        public int Red_Of_Invincible = 255;
        public int Green_Of_Invincible = 209;
        public int Blue_Of_Invincible = 119;
        public int Red_Of_Cooldown = 255;
        public int Green_Of_Cooldown = 255;
        public int Blue_Of_Cooldown = 0;
        public boolean Display_Speed = false;
        public boolean Render_Trail = true;
        public double Minimum_Render_Distance = 2.0;
        public double Trail_Offset = 0.0;
    }

    public static class Server {
        public int Duration = 40;
        public int Multiplier = 10;
        public int Damage_Limit = 100;
        public int Max_Speed = 200;
        public int Ability_Speed = 50;
        public double Jump_Height = 0.012;
        public double Speed_Multiplier = 0.0015;
        public double In_Air_Multiplier = 0.5;
        public int Trail_Duration = 5;
        public double Ground_Slam_Radius = 4.0;
        public boolean Allow_Elytra = false;
        public int Powered_Amount = 4096;
        public boolean Vertical = false;
        public int Conversion_Range = 3;
        public double Rat_Boost_Multiplier = 5.0;
    }
}


