package org.hwabeag.custommenu.inventorys;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.hwabeag.custommenu.config.ConfigManager;

import java.util.ArrayList;

public class CustomMenuGUI implements Listener {
    private final Inventory inv;

    FileConfiguration CustomMenuConfig = ConfigManager.getConfig("custommenu");

    private void initItemSetting(String MenuName) {
        int Slot = 0;
        while (Slot <= 53){
            if (CustomMenuConfig.getItemStack("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Meta") != null) {
                ItemStack item = CustomMenuConfig.getItemStack("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Meta");
                ItemMeta itemMeta = item.getItemMeta();

                if (CustomMenuConfig.getString("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Name") != null){
                    String ItemName = CustomMenuConfig.getString("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Name");
                    itemMeta.setDisplayName(ItemName);
                }

                ArrayList<String> loreList = new ArrayList<>();

                if (CustomMenuConfig.getString("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Cmd") != null){
                    String Cmd = CustomMenuConfig.getString("커스텀메뉴." + MenuName + ".아이템." + Slot + ".Cmd");
                    loreList.add(ChatColor.translateAlternateColorCodes('&', "&7&l- &a&l클릭시 사용되는 명령어 : " + Cmd));
                } else {
                    loreList.add(ChatColor.translateAlternateColorCodes('&', "&7&l- &a&l클릭시 사용되는 명령어 : 없음"));
                }

                if (CustomMenuConfig.getString("커스텀메뉴." + MenuName + ".아이템." + Slot + ".cmd") != null){
                    String NextMenu = CustomMenuConfig.getString("커스텀메뉴." + MenuName + ".아이템." + Slot + ".NextMenu");
                    loreList.add(ChatColor.translateAlternateColorCodes('&', "&7&l- &a&l클릭시 오픈되는 메뉴 : " + NextMenu));
                } else {
                    loreList.add(ChatColor.translateAlternateColorCodes('&', "&7&l- &a&l클릭시 오픈되는 메뉴 : 없음"));
                }

                itemMeta.setLore(loreList);

                item.setItemMeta(itemMeta);
                inv.setItem(Slot,item);
            }
            Slot += 1;
        }
    }

    public CustomMenuGUI(Player p, String MenuName) {
        this.inv = Bukkit.createInventory(null,54,"메뉴");
        initItemSetting(MenuName);
    }

    public void open(Player player){
        player.openInventory(inv);
    }

}
