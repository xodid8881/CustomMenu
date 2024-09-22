package org.hwabeag.custommenu;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.hwabeag.custommenu.commands.MainCommand;
import org.hwabeag.custommenu.commands.OPCommand;
import org.hwabeag.custommenu.config.ConfigManager;
import org.hwabeag.custommenu.events.ChatEvent;
import org.hwabeag.custommenu.events.InvClickEvent;
import org.hwabeag.custommenu.events.InvCloseEvent;
import org.hwabeag.custommenu.events.JoinEvent;

import java.util.Objects;

public final class CustomMenu extends JavaPlugin {

    private static ConfigManager configManager;

    private FileConfiguration config;

    public static CustomMenu getPlugin() {
        return JavaPlugin.getPlugin(CustomMenu.class);
    }

    public static void getConfigManager() {
        if (configManager == null)
            configManager = new ConfigManager();
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new InvClickEvent(), this);
        getServer().getPluginManager().registerEvents(new InvCloseEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getServer().getPluginCommand("메뉴")).setExecutor(new MainCommand());
        Objects.requireNonNull(getServer().getPluginCommand("커스텀메뉴")).setExecutor(new OPCommand());
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("[CustomMenu] Enable");
        getConfigManager();
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("[CustomMenu] Disable");
        ConfigManager.saveConfigs();
    }
}
