package net.layin.lay;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FirstLogin implements Listener {
    public void gui(Player p) {
        Inventory inv = Bukkit.createInventory(null, 2 * 9, Component.text("请选择您的配置模式"));

        ItemStack notice = new ItemStack(Material.RED_WOOL);
        ItemMeta notice_meta = notice.getItemMeta();
        notice_meta.displayName(Component.text("检测到您第一次使用本插件!", NamedTextColor.DARK_RED));
        notice_meta.lore(Arrays.asList(Component.text("请确保以下操作由管理员执行!", NamedTextColor.RED),
                Component.text("请不要关闭当前页面，请选择插件以后(很久很久)保存配置文件(比如用户数据)的方式", NamedTextColor.GOLD)));
        notice.setItemMeta(notice_meta);
        inv.setItem(4, notice);

        ItemStack yml = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta yml_meta = yml.getItemMeta();
        yml_meta.displayName(Component.text("使用本地配置文件", NamedTextColor.GREEN));
        yml_meta.lore(Arrays.asList(Component.text("一种非常方便的配置文件方式", NamedTextColor.GREEN),
                Component.text("但是不怎么安全!", NamedTextColor.YELLOW)));
        yml.setItemMeta(yml_meta);
        inv.setItem(10, yml);

        ItemStack dataB = new ItemStack(Material.GRASS);
        ItemMeta dataB_meta = dataB.getItemMeta();
        dataB_meta.displayName(Component.text("使用数据库配置文件", NamedTextColor.AQUA));
        dataB_meta.lore(Arrays.asList(Component.text("一种高效安全的配置方式", NamedTextColor.AQUA),
                Component.text("将重要数据照数据库的方式保存，普通数据保存在本地文件中", NamedTextColor.AQUA),
                Component.text("但是没有开发完!(可能会有点慢，指读写速度和开发速度)", NamedTextColor.YELLOW)));
        dataB.setItemMeta(dataB_meta);
        inv.setItem(16, dataB);

        p.openInventory(inv);
    }
}
