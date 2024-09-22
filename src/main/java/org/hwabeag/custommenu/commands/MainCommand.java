package org.hwabeag.custommenu.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.hwabeag.custommenu.config.ConfigManager;
import org.hwabeag.custommenu.inventorys.CustomMenuGUI;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class MainCommand implements CommandExecutor {

    FileConfiguration CustomMenuConfig = ConfigManager.getConfig("custommenu");
    String Prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(CustomMenuConfig.getString("custommenu.prefix")));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            String name = player.getName();
            if (CustomMenuConfig.getString("메인메뉴") != null) {
                String MenuName = CustomMenuConfig.getString("메인메뉴");
                CustomMenuConfig.set("플레이어메뉴." + name, MenuName);
                ConfigManager.saveConfigs();

                CustomMenuGUI inv = new CustomMenuGUI(player, MenuName);
                inv.open(player);
            } else {
                player.sendMessage(Prefix + " 아직 메뉴를 이용할 수 없습니다.");
            }
            return true;
        }
        return false;
    }
}