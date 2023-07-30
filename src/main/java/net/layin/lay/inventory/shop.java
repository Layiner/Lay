package net.layin.lay.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.layin.lay.configs;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class shop implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Component.text("你想啥呢,只有玩家才能用这指令", NamedTextColor.YELLOW));
            return true;
        }
        Player player = (Player) commandSender;
        if (strings.length == 1) {
            player.sendMessage(Component.text("Umm...你是不是输错了?"));
            return true;
        }
        if (strings.length == 2) {
            if (strings[0].equals("add")) {
                if (player.isOp()) {
                    if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                        player.sendMessage(Component.text("你干啥呢,卖空气呢", NamedTextColor.YELLOW));
                        return true;
                    }
                    try {
                        Integer.parseInt(strings[1]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(Component.text("售价必须输入数字!", NamedTextColor.RED));
                        return true;
                    }
                    if (menuName.SHOP_OUT_ITEM_INT>=36|menuName.SHOP_IN_ITEM_INT>=36){
                        player.sendMessage(Component.text("商品已经满了!")
                                .append(Component.newline())
                                .append(Component.text("您可以等待更新换页功能(很久很久),也可以使用指令替换一些商品")));
                        return true;
                    }
                    addShopItem(Integer.parseInt(strings[1]), player);
                } else {
                    commandSender.sendMessage(Component.text("你想啥呢,只有管理员才能用这指令", NamedTextColor.YELLOW));
                }
                return true;
            }
            return true;
        }
        if (strings.length==3){
            if (strings[0].equals("set")){
                try {
                    Integer.parseInt(strings[1]);
                    Integer.parseInt(strings[2]);
                }catch (NumberFormatException nbnbnb){
                    return true;
                }
                int bianHao = Integer.parseInt(strings[1]);//原商品编号
                int shouJia = Integer.parseInt(strings[2]);//现售价
                if (shouJia>=0){
                    if (bianHao<0|bianHao>menuName.SHOP_OUT_ITEM_INT){
                        return true;
                    }
                    configs.shop.set("out."+bianHao,player.getInventory().getItemInMainHand());
                    configs.shop.set("out.coin."+bianHao,shouJia);
                    return true;
                } else {
                    if (bianHao<0|bianHao>menuName.SHOP_IN_ITEM_INT){
                        return true;
                    }
                    configs.shop.set("in."+bianHao,player.getInventory().getItemInMainHand());
                    configs.shop.set("in.coin."+bianHao,shouJia);
                    return true;
                }
            }
        }
        Inventory shop = Bukkit.createInventory(player, 6 * 9, menuName.SHOP_TITLE);
        /*
        商店需要换页
        0  1  2  3  4  5  6  7  8
        9  10 11 12 13 14 15 16 17
        18 19 20 21 22 23 24 25 26
        27 28 29 30 31 32 33 34 35
        36 37 38 39 40 41 42 43 44
        45 46 47 48 49 50 51 52 53

        53=关闭;0=购买物品;8=兑换金币;48=上一页;49=当前页数;50=下一页
        将9-44设为物品展示区域
         */
        ItemStack closeMenu = new ItemStack(Material.BARRIER);
        ItemMeta closeMenuMeta = closeMenu.getItemMeta();
        closeMenuMeta.displayName(menuName.CLOSE_MENU);
        closeMenu.setItemMeta(closeMenuMeta);

        ItemStack buy = new ItemStack(Material.BEDROCK);
        ItemMeta buyMeta = buy.getItemMeta();
        buyMeta.displayName(menuName.BUY);
        buyMeta.lore(menuName.BUY_LORE);
        buy.setItemMeta(buyMeta);

        ItemStack coin = new ItemStack(Material.ANVIL);
        ItemMeta coinMeta = coin.getItemMeta();
        coinMeta.displayName(menuName.COIN);
        coinMeta.lore(menuName.COIN_LORE);
        coin.setItemMeta(coinMeta);

        shop.setItem(53, closeMenu);//关闭菜单
        shop.setItem(0, buy);//购买物品
        shop.setItem(8, coin);//兑换金币

        player.openInventory(shop);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            List<String> r = new ArrayList<>();
            r.add("add");
            r.add("set");
            return r;
        }
        if (strings.length == 2) {
            if (strings[0].equals("add")) {
                return Collections.singletonList("<售价>");
            }
            if (strings[0].equals("set")) {
                return Collections.singletonList("<原商品编号>");
            } else {
                return null;
            }
        }
        if (strings.length == 3) {
            if (strings[0].equals("set")) {
                return Collections.singletonList("<现售价>");
            } else {
                return null;
            }
        } else {
            return null;
        }
        // /shop add 金币数量   /shop set 原商品编号 售价
    }

    private void addShopItem(int coin, Player player) {
        if (coin >= 0) {
            menuName.SHOP_OUT_ITEM_INT++;
            configs.shop.set("out." + menuName.SHOP_OUT_ITEM_INT, player.getInventory().getItemInMainHand());
            configs.shop.set("out.coin." + menuName.SHOP_OUT_ITEM_INT, coin);
            configs.shop.set("out.int", menuName.SHOP_OUT_ITEM_INT);
            //ItemStack i = new ItemStack(Objects.requireNonNull(configs.shop.getItemStack("out")));
            //player.getInventory().addItem(i);
            player.sendMessage(Component.text("您已添加可购买商品;", NamedTextColor.GREEN)
                    .hoverEvent(HoverEvent.showText(Component.text(player.getInventory().getItemInMainHand().toString())))
                    .append(Component.text("商品编号:" + menuName.SHOP_OUT_ITEM_INT)));
        } else {
            menuName.SHOP_IN_ITEM_INT++;
            configs.shop.set("in." + menuName.SHOP_IN_ITEM_INT, player.getInventory().getItemInMainHand());
            configs.shop.set("in.coin." + menuName.SHOP_IN_ITEM_INT, coin);
            configs.shop.set("in.int", menuName.SHOP_IN_ITEM_INT);
            player.sendMessage(Component.text("您已添加可兑换金币的商品;", NamedTextColor.GREEN)
                    .hoverEvent(HoverEvent.showText(Component.text(player.getInventory().getItemInMainHand().toString())))
                    .append(Component.text("商品编号:" + menuName.SHOP_IN_ITEM_INT)));
        }
    }
}
