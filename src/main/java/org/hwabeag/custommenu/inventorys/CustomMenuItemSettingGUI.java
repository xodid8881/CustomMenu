package org.hwabeag.custommenu.inventorys;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.hwabeag.custommenu.config.ConfigManager;

public class CustomMenuItemSettingGUI implements Listener {
    private final Inventory inv;

    FileConfiguration CustomMenuConfig = ConfigManager.getConfig("custommenu");

    private void initItemSetting(Player p, String MenuName) {
        int Slot = 0;
        while (Slot <= 53){
            if (CustomMenuConfig.getItemStack("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Meta") != null) {
                ItemStack item = CustomMenuConfig.getItemStack("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Meta");
                inv.setItem(Slot,item);
            }
            Slot += 1;
        }
    }

    public CustomMenuItemSettingGUI(Player p, String MenuName) {
        this.inv = Bukkit.createInventory(null,54,"커스텀메뉴 아이템세팅");
        initItemSetting(p, MenuName);
    }

    public void open(Player player){
        player.openInventory(inv);
    }

}
