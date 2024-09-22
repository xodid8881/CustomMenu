package org.hwabeag.custommenu.events;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.hwabeag.custommenu.CustomMenu;
import org.hwabeag.custommenu.config.ConfigManager;
import org.hwabeag.custommenu.inventorys.CustomMenuClickSettingGUI;
import org.hwabeag.custommenu.inventorys.CustomMenuGUI;
import org.hwabeag.custommenu.inventorys.CustomMenuItemSettingGUI;

import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class InvClickEvent implements Listener {
    FileConfiguration CustomMenuConfig = ConfigManager.getConfig("custommenu");
    String Prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(CustomMenuConfig.getString("custommenu.prefix")));

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        String name = player.getName();
        if (e.getClickedInventory() == null)
            return;
        if (e.getCurrentItem() != null) {
            if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("커스텀메뉴 세팅")) {
                String clickitem = e.getCurrentItem().getItemMeta().getDisplayName();
                if (clickitem.equals(ChatColor.translateAlternateColorCodes('&', "&a아이템 세팅"))) {
                    e.setCancelled(true);
                    player.closeInventory();
                    String MenuName = CustomMenuConfig.getString("커스텀메뉴세팅." + name);
                    getServer().getScheduler().scheduleSyncDelayedTask(CustomMenu.getPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            CustomMenuItemSettingGUI inv = new CustomMenuItemSettingGUI(player, MenuName);
                            inv.open(player);
                        }
                    }, 20);
                }
                if (clickitem.equals(ChatColor.translateAlternateColorCodes('&', "&a세부 세팅"))) {
                    e.setCancelled(true);
                    player.closeInventory();
                    String MenuName = CustomMenuConfig.getString("커스텀메뉴세팅." + name);
                    getServer().getScheduler().scheduleSyncDelayedTask(CustomMenu.getPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            CustomMenuClickSettingGUI inv = new CustomMenuClickSettingGUI(player, MenuName);
                            inv.open(player);
                        }
                    }, 20);
                }
            }
            if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("커스텀메뉴 세부세팅")) {
                String clickitem = e.getCurrentItem().getItemMeta().getDisplayName();
                String MenuName = CustomMenuConfig.getString("커스텀메뉴세팅." + name);
                e.setCancelled(true);
                int Slot = e.getSlot();
                if (CustomMenuConfig.getItemStack("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Meta") != null) {
                    if (e.getClick().isLeftClick()) {
                        if (CustomMenuConfig.getString("커스텀명령어세팅." + name) != null) {
                            CustomMenuConfig.set("커스텀명령어세팅." + name, Slot);
                        } else {
                            CustomMenuConfig.addDefault("커스텀명령어세팅." + name, Slot);
                        }
                        ConfigManager.saveConfigs();
                        player.sendMessage(Prefix + " " + "채팅을 이용하여 설정할 명령어를 적어주세요.");
                        player.closeInventory();
                        // Left click
                    } else if (e.getClick().isRightClick()) {
                        if (CustomMenuConfig.getString("커스텀이름세팅." + name) != null) {
                            CustomMenuConfig.set("커스텀이름세팅." + name, Slot);
                        } else {
                            CustomMenuConfig.addDefault("커스텀이름세팅." + name, Slot);
                        }
                        ConfigManager.saveConfigs();
                        player.sendMessage(Prefix + " " + "채팅을 이용하여 설정할 이름을 적어주세요.");
                        player.closeInventory();
                        // Right click
                    } else if (e.getClick().isShiftClick()) {
                        if (CustomMenuConfig.getString("커스텀메뉴연결세팅." + name) != null) {
                            CustomMenuConfig.set("커스텀메뉴연결세팅." + name, Slot);
                        } else {
                            CustomMenuConfig.addDefault("커스텀메뉴연결세팅." + name, Slot);
                        }
                        ConfigManager.saveConfigs();
                        player.sendMessage(Prefix + " " + "채팅을 이용하여 연결할 메뉴 이름을 적어주세요.");
                        player.closeInventory();
                        // Shift click
                    }
                }
            }
            if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("메뉴")) {
                String MenuName = CustomMenuConfig.getString("플레이어메뉴." + name);
                e.setCancelled(true);
                int Slot = e.getSlot();
                if (CustomMenuConfig.getItemStack("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Meta") != null) {
                    if (CustomMenuConfig.getString("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Cmd") != null){
                        String Cmd = CustomMenuConfig.getString("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Cmd");
                        player.chat("/" + Cmd);
                    }
                    if (CustomMenuConfig.getString("커스텀메뉴." + MenuName + ".아이템." + Slot + ".NextMenu") != null){
                        String NextMenu = CustomMenuConfig.getString("커스텀메뉴." + MenuName + ".아이템." + Slot + ".NextMenu");
                        getServer().getScheduler().scheduleSyncDelayedTask(CustomMenu.getPlugin(), new Runnable() {
                            @Override
                            public void run() {
                                CustomMenuGUI inv = new CustomMenuGUI(player, NextMenu);
                                inv.open(player);
                            }
                        }, 20);
                    }
                    player.closeInventory();
                }
            }
        }
    }
}
