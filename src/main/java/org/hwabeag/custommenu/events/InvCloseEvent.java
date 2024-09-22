package org.hwabeag.custommenu.events;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.hwabeag.custommenu.config.ConfigManager;
import org.jetbrains.annotations.Nullable;

public class InvCloseEvent implements Listener {

    FileConfiguration CustomMenuConfig = ConfigManager.getConfig("custommenu");
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        String name = p.getName();
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("커스텀메뉴 아이템세팅")) {
            String craftingname = CustomMenuConfig.getString("커스텀메뉴세팅." + name);
            if (CustomMenuConfig.getString("커스텀메뉴." + craftingname) != null) {
                int Slot = 0;
                while (Slot <= 53) {
                    if (e.getInventory().getItem(Slot) != null) {
                        if (CustomMenuConfig.getItemStack("커스텀메뉴." + craftingname + ".아이템." + Slot + ".Meta") != null) {
                            @Nullable ItemStack item = e.getInventory().getItem(Slot);
                            CustomMenuConfig.set("커스텀메뉴." + craftingname + ".아이템." + Slot + ".Meta", item);
                        } else {
                            @Nullable ItemStack item = e.getInventory().getItem(Slot);
                            CustomMenuConfig.addDefault("커스텀메뉴." + craftingname + ".아이템." + Slot + ".Meta", item);
                        }
                    } else {
                        CustomMenuConfig.set("커스텀메뉴." + craftingname + ".아이템." + Slot, null);
                    }
                    Slot += 1;
                }
                ConfigManager.saveConfigs();
            }
        }
    }
}
