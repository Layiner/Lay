package net.layin.lay.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.layin.lay.configs;
import net.layin.lay.inventory.menuName;
import net.layin.lay.six.nbt;
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

public class vip implements TabExecutor {
    private int qququququ = 0;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (strings.length==0){
            Inventory inv = Bukkit.createInventory(player, 9,menuName.VIP_TITLE);

            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            head = nbt.setNbt(head, "SkullOwner", player.getName());
            assert head != null;
            ItemMeta headMeta = head.getItemMeta();
            headMeta.displayName(Component.text(player.getName()));
            headMeta.lore(Collections.singletonList(Component.text("当前状态:" + configs.userdata.get(player.getName() + ".vip"),NamedTextColor.GREEN)));
            head.setItemMeta(headMeta);

            ItemStack gold = new ItemStack(Material.GOLD_INGOT);
            ItemMeta goldM = gold.getItemMeta();
            goldM.displayName(Component.text("黄金会员"));
            List<Component> r = new ArrayList<>();
            r.add(Component.text("一次购买,永久享受",NamedTextColor.LIGHT_PURPLE));
            r.add(Component.text("售价:"+configs.datau.getInt("vip.gold")));
            goldM.lore(r);
            gold.setItemMeta(goldM);

            ItemStack diamond = new ItemStack(Material.DIAMOND);
            ItemMeta diamondM = diamond.getItemMeta();
            diamondM.displayName(Component.text("钻石会员"));
            List<Component> ru = new ArrayList<>();
            ru.add(Component.text("一次购买,永久享受",NamedTextColor.LIGHT_PURPLE));
            ru.add(Component.text("售价:"+configs.datau.getInt("vip.diamond")));
            diamondM.lore(ru);
            diamond.setItemMeta(diamondM);

            ItemStack close = new ItemStack(Material.BARRIER);
            ItemMeta closeM = close.getItemMeta();
            closeM.displayName(Component.text("算了,我不配"));
            close.setItemMeta(closeM);

            inv.setItem(0,head);
            inv.setItem(3,gold);
            inv.setItem(5,diamond);
            inv.setItem(8,close);
            /*
            0 1 2 3 4 5 6 7 8
             */
            player.openInventory(inv);
            return true;
        }
        if (strings.length == 3) {
            if (!player.isOp()) {
                player.sendMessage(Component.text("你想啥呢,只有管理员才能用这指令", NamedTextColor.YELLOW));
                return true;
            }
            if (strings[0].equals("set")) {
                if (strings[2].equals("common") | strings[2].equals("gold") | strings[2].equals("diamond")) {
                    qququququ = 1;
                    player.sendMessage(Component.text("您确定要给 " + strings[1] + " 设定会员等级 " + strings[2] + " 吗?", NamedTextColor.LIGHT_PURPLE)
                            .append(Component.text("[确定]", NamedTextColor.RED).clickEvent(ClickEvent.callback(audience -> {
                                if (qququququ == 0) {
                                    player.sendMessage(Component.text("好像没有需要处理的了?", NamedTextColor.YELLOW));
                                    return;
                                }
                                if (configs.userdata.contains(strings[1])) {
                                    configs.userdata.set(strings[1] + ".vip", strings[2]);
                                    qququququ = 0;
                                    player.sendMessage(Component.text("成功为 " + strings[1] + " 设定会员等级 " + strings[2] + "!", NamedTextColor.LIGHT_PURPLE));
                                } else {
                                    qququququ = 0;
                                    player.sendMessage(Component.text("好像没有这个人奥"));
                                }
                            })))
                            .append(Component.text("[算了吧]", NamedTextColor.GREEN).clickEvent(ClickEvent.callback(audience -> {
                                if (qququququ == 0) {
                                    player.sendMessage(Component.text("好像没有需要处理的了?", NamedTextColor.YELLOW));
                                    return;
                                }
                                qququququ = 0;
                                player.sendMessage(Component.text("行吧,算了吧", NamedTextColor.WHITE));
                            }))));
                    return true;
                }
            }
            // /vip set <USERNAME> <TYPE>
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return Collections.singletonList("set");
        }
        if (strings.length == 2) {
            return Collections.singletonList("<用户名>");
        }
        if (strings.length == 3) {
            if (strings[0].equals("set")) {
                List<String> r = new ArrayList<>();
                r.add("common");
                r.add("gold");
                r.add("diamond");
                return r;
            }
            return null;
        }
        return null;
    }
}
