package org.hwabeag.custommenu.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.hwabeag.custommenu.CustomMenu;

import java.util.HashMap;

public class ConfigManager {
    private static final CustomMenu plugin = CustomMenu.getPlugin();

    private static final HashMap<String, ConfigMaker> configSet = new HashMap<>();


    public ConfigManager() {
        String path = plugin.getDataFolder().getAbsolutePath();
        configSet.put("custommenu", new ConfigMaker(path, "CustomMenu.yml"));
        loadSettings();
        saveConfigs();
    }

    public static void reloadConfigs() {
        for (String key : configSet.keySet()){
            plugin.getLogger().info(key);
            configSet.get(key).reloadConfig();
        }
    }

    public static void saveConfigs(){
        for (String key : configSet.keySet())
            configSet.get(key).saveConfig();
    }

    public static FileConfiguration getConfig(String fileName) {
        return configSet.get(fileName).getConfig();
    }

    public static void loadSettings(){
        FileConfiguration CraftingConfig = getConfig("custommenu");
        CraftingConfig.options().copyDefaults(true);

        CraftingConfig.addDefault("custommenu.prefix", "&a&l[CustomMenu]&7");
    }


}