package org.hwabeag.custommenu.events;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.hwabeag.custommenu.config.ConfigManager;

import java.util.Objects;

public class JoinEvent implements Listener {

    FileConfiguration CustomMenuConfig = ConfigManager.getConfig("custommenu");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        if (CustomMenuConfig.getString("플레이어페이지." + name) == null) {
            CustomMenuConfig.addDefault("플레이어페이지." + name, 0);
            CustomMenuConfig.addDefault("플레이어메뉴." + name, "없음");
            ConfigManager.saveConfigs();
        }
    }
}
