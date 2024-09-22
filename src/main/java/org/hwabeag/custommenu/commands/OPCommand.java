package org.hwabeag.custommenu.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.hwabeag.custommenu.config.ConfigManager;
import org.hwabeag.custommenu.inventorys.CustomMenuSettingGUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class OPCommand implements CommandExecutor {

    FileConfiguration CustomMenuConfig = ConfigManager.getConfig("custommenu");
    String Prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(CustomMenuConfig.getString("custommenu.prefix")));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (p.isOp()) {
                if (args.length == 0) {
                    p.sendMessage(Prefix + " /커스텀메뉴 생성 [제목] - 커스텀메뉴를 생성합니다.");
                    p.sendMessage(Prefix + " /커스텀메뉴 메인메뉴설정 [제목] - 커스텀메뉴의 메인을 설정합니다.");
                    p.sendMessage(Prefix + " /커스텀메뉴 메인아이템변경 [제목] - 커스텀메뉴의 메인아이템을 변경합니다.");
                    p.sendMessage(Prefix + " /커스텀메뉴 세팅 [제목] - 커스텀메뉴를 세팅합니다.");
                    p.sendMessage(Prefix + " /커스텀메뉴 삭제 [제목] - 커스텀메뉴를 삭제합니다.");
                    p.sendMessage(Prefix + " /커스텀메뉴 목록 - 커스텀메뉴 목록을 확인합니다.");
                    p.sendMessage(Prefix + " /커스텀메뉴 리로드 - 커스텀메뉴를 리로드 합니다.");
                    return true;
                }
                switch (args[0]) {
                    case "생성" -> {
                        if (args.length == 1) {
                            p.sendMessage(Prefix + " /커스텀메뉴 생성 [제목] - 커스텀메뉴를 생성합니다.");
                            return true;
                        }
                        ItemStack itemStack = p.getItemInHand().clone();
                        itemStack.setAmount(1);

                        if (itemStack.getType().equals(Material.AIR)) {
                            p.sendMessage(Prefix + " 손에 아이템을 들고 작업해주세요.");
                            return true;
                        }

                        if (CustomMenuConfig.getString("커스텀메뉴." + args[1]) == null) {
                            @NotNull ItemStack item = p.getItemInHand().clone();
                            CustomMenuConfig.addDefault("커스텀메뉴." + args[1] + ".메인아이템", item);
                            p.sendMessage(Prefix + " " + args[1] + " 이름으로 커스텀메뉴를 제작했습니다.");
                            ConfigManager.saveConfigs();
                        } else {
                            p.sendMessage(Prefix + " " + args[1] + " 이름으로 커스텀메뉴가 이미 존재합니다.");
                        }
                        return true;
                    }
                    case "메인메뉴설정" -> {
                        if (args.length == 1) {
                            p.sendMessage(Prefix + " /커스텀메뉴 메인메뉴설정 [제목] - 커스텀메뉴의 메인을 설정합니다.");
                            return true;
                        }
                        if (CustomMenuConfig.getString("커스텀메뉴." + args[1]) != null) {
                            CustomMenuConfig.addDefault("메인메뉴", args[1]);
                            p.sendMessage(Prefix + " " + args[1] + " 이름으로 커스텀메뉴를 설정했습니다.");
                            ConfigManager.saveConfigs();
                        } else {
                            p.sendMessage(Prefix + " " + args[1] + " 이름의 커스텀메뉴가 존재하지 않습니다.");
                        }
                        return true;
                    }
                    case "메인아이템변경" -> {
                        if (args.length == 1) {
                            p.sendMessage(Prefix + " /커스텀메뉴 메인아이템변경 [제목] - 커스텀메뉴의 메인아이템을 변경합니다.");
                            return true;
                        }
                        ItemStack itemStack = p.getItemInHand().clone();
                        itemStack.setAmount(1);

                        if (itemStack.getType().equals(Material.AIR)) {
                            p.sendMessage(Prefix + " 손에 아이템을 들고 작업해주세요.");
                            return true;
                        }

                        if (CustomMenuConfig.getString("커스텀메뉴." + args[1]) != null) {
                            @Nullable ItemStack item = p.getItemInHand().clone();
                            CustomMenuConfig.set("커스텀메뉴." + args[1] + ".메인아이템", item);
                            p.sendMessage(Prefix + " " + args[1] + " 이름의 커스텀메뉴의 메인아이템을 변경했습니다.");
                            ConfigManager.saveConfigs();
                        } else {
                            p.sendMessage(Prefix + " " + args[1] + " 이름의 커스텀메뉴가 존재하지 않습니다.");
                        }
                        return true;
                    }
                    case "세팅" -> {
                        if (args.length == 1) {
                            p.sendMessage(Prefix + " /커스텀메뉴 세팅 [제목] - 커스텀메뉴를 세팅합니다.");
                            return true;
                        }
                        if (CustomMenuConfig.getString("커스텀메뉴." + args[1]) != null) {
                            String name = p.getName();
                            if (CustomMenuConfig.getString("커스텀메뉴세팅." + name) != null) {
                                CustomMenuConfig.set("커스텀메뉴세팅." + name, args[1]);
                            } else {
                                CustomMenuConfig.addDefault("커스텀메뉴세팅." + name, args[1]);
                            }
                            ConfigManager.saveConfigs();
                            CustomMenuSettingGUI inv = new CustomMenuSettingGUI(p);
                            inv.open(p);
                        } else {
                            p.sendMessage(Prefix + " " + args[1] + " 이름의 커스텀메뉴가 존재하지 않습니다.");
                        }
                        return true;
                    }
                    case "삭제" -> {
                        if (args.length == 1) {
                            p.sendMessage(Prefix + " /커스텀메뉴 삭제 [제목] - 커스텀메뉴를 삭제합니다.");
                            return true;
                        }
                        if (CustomMenuConfig.getString("커스텀메뉴." + args[1]) != null) {
                            CustomMenuConfig.set("커스텀메뉴." + args[1], null);
                            ConfigManager.saveConfigs();
                            p.sendMessage(Prefix + " " + args[1] + " 이름의 커스텀메뉴를 삭제했습니다.");
                            p.sendMessage(Prefix + " /커스텀메뉴 리로드 - 커스텀메뉴를 리로드 합니다.");
                        } else {
                            p.sendMessage(Prefix + " " + args[1] + " 이름의 커스텀메뉴가 존재하지 않습니다.");
                        }
                        return true;
                    }
                    case "목록" -> {
                        p.sendMessage(Prefix + " 커스텀메뉴 목록");
                        for (String key : Objects.requireNonNull(CustomMenuConfig.getConfigurationSection("커스텀메뉴.")).getKeys(false)) {
                            p.sendMessage(Prefix + " " + key);
                        }
                        p.sendMessage(Prefix + " 커스텀메뉴 목록을 불러왔습니다.");
                        return true;
                    }
                    case "리로드" -> {
                        ConfigManager.saveConfigs();
                        ConfigManager.reloadConfigs();
                        p.sendMessage(Prefix + " 커스텀메뉴 정보를 리로드 했습니다.");
                        return true;
                    }
                }
                p.sendMessage(Prefix + " /커스텀메뉴 생성 [제목] - 커스텀메뉴를 생성합니다.");
                p.sendMessage(Prefix + " /커스텀메뉴 메인아이템변경 [제목] - 커스텀메뉴의 메인아이템을 변경합니다.");
                p.sendMessage(Prefix + " /커스텀메뉴 세팅 [제목] - 커스텀메뉴를 세팅합니다.");
                p.sendMessage(Prefix + " /커스텀메뉴 삭제 [제목] - 커스텀메뉴를 삭제합니다.");
                p.sendMessage(Prefix + " /커스텀메뉴 목록 - 커스텀메뉴 목록을 확인합니다.");
                p.sendMessage(Prefix + " /커스텀메뉴 리로드 - 커스텀메뉴를 리로드 합니다.");
            } else {
                p.sendMessage(Prefix + " 당신은 권한이 없습니다.");
            }
            return true;
        }
        return false;
    }
}