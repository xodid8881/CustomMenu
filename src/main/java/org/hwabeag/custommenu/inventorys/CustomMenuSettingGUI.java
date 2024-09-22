package org.hwabeag.custommenu.inventorys;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hwabeag.custommenu.config.ConfigManager;

public class CustomMenuSettingGUI implements Listener {
    private final Inventory inv;

    FileConfiguration CustomMenuConfig = ConfigManager.getConfig("custommenu");

    private void initItemSetting(Player p) {
        ItemStack item = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta = item.getItemMeta();   //검의 메타데이터
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a아이템 세팅"));
        item.setItemMeta(itemMeta);
        inv.setItem(12,item);

        //검의 메타데이터
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a세부 세팅"));
        item.setItemMeta(itemMeta);
        inv.setItem(14,item);
    }

    public CustomMenuSettingGUI(Player p) {
        this.inv = Bukkit.createInventory(null,27,"커스텀메뉴 세팅");
        initItemSetting(p);
    }

    public void open(Player player){
        player.openInventory(inv);
    }

}
