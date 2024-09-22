package org.hwabeag.custommenu.events;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.hwabeag.custommenu.config.ConfigManager;

import java.util.Objects;

public class ChatEvent implements Listener {

    FileConfiguration CustomMenuConfig = ConfigManager.getConfig("custommenu");
    String Prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(CustomMenuConfig.getString("custommenu.prefix")));

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        String msg = event.getMessage();
        event.setCancelled(true);
        String MenuName = CustomMenuConfig.getString("커스텀메뉴세팅." + name);
        if (CustomMenuConfig.getString("커스텀명령어세팅." + name) != null) {
            int Slot = CustomMenuConfig.getInt("커스텀명령어세팅." + name);
            if (CustomMenuConfig.getString("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Cmd") != null){
                CustomMenuConfig.set("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Cmd", msg);
            } else {
                CustomMenuConfig.addDefault("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Cmd", msg);
            }
            CustomMenuConfig.set("커스텀명령어세팅." + name, null);
            ConfigManager.saveConfigs();
            player.sendMessage(Prefix + " " + msg + " 의 명령어를 연결했습니다.");
        } else if (CustomMenuConfig.getString("커스텀이름세팅." + name) != null) {
            int Slot = CustomMenuConfig.getInt("커스텀이름세팅." + name);
            if (CustomMenuConfig.getString("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Name") != null){
                CustomMenuConfig.set("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Name", msg);
            } else {
                CustomMenuConfig.addDefault("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Name", msg);
            }
            CustomMenuConfig.set("커스텀이름세팅." + name, null);
            ConfigManager.saveConfigs();
            player.sendMessage(Prefix + " " + msg + " 이름으로 변경했습니다.");
        } else if (CustomMenuConfig.getString("커스텀메뉴연결세팅." + name) != null) {
            int Slot = CustomMenuConfig.getInt("커스텀메뉴연결세팅." + name);
            if (CustomMenuConfig.getString("커스텀메뉴." + MenuName + ".아이템." + Slot + ".NextMenu") != null){
                CustomMenuConfig.set("커스텀메뉴." + MenuName + ".아이템." + Slot + ".NextMenu", msg);
            } else {
                CustomMenuConfig.addDefault("커스텀메뉴." + MenuName + ".아이템." + Slot + ".NextMenu", msg);
            }
            CustomMenuConfig.set("커스텀메뉴연결세팅." + name, null);
            ConfigManager.saveConfigs();
            player.sendMessage(Prefix + " " + msg + " 이름의 메뉴로 연결했습니다.");
        }
    }
}
