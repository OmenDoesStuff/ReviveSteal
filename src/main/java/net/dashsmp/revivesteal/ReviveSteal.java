package net.dashsmp.revivesteal;

import net.dashsmp.revivesteal.command.ReviveCommand;
import net.dashsmp.revivesteal.command.SacrificeCommand;
import net.dashsmp.revivesteal.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ReviveSteal extends JavaPlugin {

    public static List<UUID> ghostPlayers = new ArrayList<>();
    public static ReviveSteal INSTANCE;
    private File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        INSTANCE = this;

        //createCustomConfig();
        //saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getCommand("revive").setExecutor(new ReviveCommand());
        getCommand("sacrifice").setExecutor(new SacrificeCommand());
        Bukkit.getLogger().info("[ReviveSteal] ReviveSteal Has Been Enabled.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[ReviveSteal] ReviveSteal Has Been Disabled.");
    }

    public static ReviveSteal getInstance() {
        return INSTANCE;
    }

    public static Location getSpawn() {
        return Bukkit.getWorld("world").getSpawnLocation();
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "custom.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }

        customConfig= new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
