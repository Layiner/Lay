package net.layin.lay.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.layin.lay.FirstLogin;
import net.layin.lay.Lay;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class fl implements Listener {
    public static ItemStack menuItem;

    public static void createMenuItem() {
        menuItem = new ItemStack(Material.CLOCK);
        Enchantment enchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft("sharpness"));
        menuItem.addUnsafeEnchantment(enchantment, 5);
        ItemMeta menuItemMeta = menuItem.getItemMeta();
        menuItemMeta.displayName(Component.text("菜单》右键打开", NamedTextColor.GREEN));
        menuItemMeta.lore(Collections.singletonList(Component.text("一人只有一个奥", NamedTextColor.BLUE)));
        menuItem.setItemMeta(menuItemMeta);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getWhoClicked().getOpenInventory().title().equals(Component.text("请选择您的配置模式"))) {
            if (e.getRawSlot() > e.getInventory().getSize() | e.getRawSlot() < 0) {
                return;
            }
            e.setCancelled(true);
            if (e.getRawSlot() == 10) {
                //TODO:未来要做数据库
                player.closeInventory();
                File duF = new File(Lay.getPlugin(Lay.class).getDataFolder(), "datau.yml");
                FileConfiguration du = YamlConfiguration.loadConfiguration(duF);
                du.set("w", "yml");
                try {
                    du.save(duF);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        createMenuItem();
        if (event.getItemDrop().getItemStack().equals(menuItem)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);

        FileConfiguration du = YamlConfiguration.loadConfiguration(new File(net.layin.lay.Lay.getPlugin(net.layin.lay.Lay.class).getDataFolder(), "datau.yml"));
        if (du.get("w") == null) {
            new FirstLogin().gui(player);
            //TODO:看这就知道了
            player.sendMessage(Component.text("请在聊天框输入", NamedTextColor.RED)
                    .append(Component.text("/register <密码> <重复密码>", NamedTextColor.YELLOW))
                    .append(Component.text("进行注册!", NamedTextColor.RED)));
        } else {
            FileConfiguration userdata = YamlConfiguration.loadConfiguration(new File(net.layin.lay.Lay.getPlugin(net.layin.lay.Lay.class).getDataFolder(), "userdata.yml"));
            if (userdata.contains(player.getName())) {
                player.sendMessage(Component.text("请在聊天框输入", NamedTextColor.RED)
                        .append(Component.text("/login <密码>", NamedTextColor.YELLOW))
                        .append(Component.text("进行登录!", NamedTextColor.RED))
                        .append(Component.newline())
                        .append(Component.text("如果你是第一次进入服务器，说明你的名称已被别人使用，请更换名称登录", NamedTextColor.GOLD)));
                if (userdata.get(player.getName() + ".vip").equals("gold")) {
                    Bukkit.broadcast(Component.text("[黄金会员]", NamedTextColor.GOLD)
                            .append(Component.text(player.getName() + "登录!", NamedTextColor.WHITE)));
                }
                if (userdata.get(player.getName() + ".vip").equals("diamond")) {
                    Bukkit.broadcast(Component.text("[钻石会员]", NamedTextColor.DARK_AQUA)
                            .append(Component.text(player.getName() + "上线啦!", NamedTextColor.RED)));
                }
            } else {
                player.sendMessage(Component.text("请在聊天框输入", NamedTextColor.RED)
                        .append(Component.text("/register [密码] [重复密码]", NamedTextColor.YELLOW))
                        .append(Component.text("进行注册!", NamedTextColor.RED)));
            }
        }
    }
}
