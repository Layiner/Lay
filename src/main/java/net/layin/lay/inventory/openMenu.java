package net.layin.lay.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.layin.lay.configs;
import net.layin.lay.six.nbt;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class openMenu implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Component.text("你想啥呢,只有玩家才能用这指令", NamedTextColor.YELLOW));
            return true;
        }

        Player player = (Player) commandSender;
        menuName.components = Bukkit.createInventory(player, 6 * 9, menuName.TITLE);

        ItemStack closeMenu = new ItemStack(Material.BARRIER);
        ItemMeta closeMenuMeta = closeMenu.getItemMeta();
        closeMenuMeta.displayName(menuName.CLOSE_MENU);
        closeMenu.setItemMeta(closeMenuMeta);

        ItemStack board = new ItemStack(Material.BOOK);
        Enchantment enchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft("unbreaking"));
        board.addUnsafeEnchantment(enchantment, 3);
        ItemMeta boardMeta = board.getItemMeta();
        boardMeta.displayName(menuName.BOARD);
        boardMeta.lore(menuName.BOARD_LORE);
        board.setItemMeta(boardMeta);

        ItemStack tpa = new ItemStack(Material.PAPER);
        tpa.addUnsafeEnchantment(enchantment, 3);
        ItemMeta tpaMeta = tpa.getItemMeta();
        tpaMeta.displayName(menuName.TPA);
        tpaMeta.lore(menuName.TPA_LORE);
        tpa.setItemMeta(tpaMeta);

        ItemStack survivalZone1 = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta survivalZone1Meta = survivalZone1.getItemMeta();
        survivalZone1Meta.displayName(menuName.SURVIVAL_ZONE1);
        survivalZone1Meta.lore(menuName.SURVIVAL_ZONE1_LORE);
        survivalZone1.setItemMeta(survivalZone1Meta);

        ItemStack survivalZone2 = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta survivalZone2Meta = survivalZone2.getItemMeta();
        survivalZone2Meta.displayName(menuName.SURVIVAL_ZONE2);
        survivalZone2Meta.lore(menuName.SURVIVAL_ZONE2_LORE);
        survivalZone2.setItemMeta(survivalZone2Meta);

        ItemStack shop = new ItemStack(Material.EMERALD);
        shop.addUnsafeEnchantment(enchantment, 3);
        ItemMeta shopMeta = shop.getItemMeta();
        shopMeta.displayName(menuName.SHOP);
        shopMeta.lore(menuName.SHOP_LORE);
        shop.setItemMeta(shopMeta);

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        head = nbt.setNbt(head, "SkullOwner", player.getName());
        assert head != null;
        head.addUnsafeEnchantment(enchantment, 3);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.displayName(menuName.HEAD);
        headMeta.lore(menuName.HEAD_LORE);
        head.setItemMeta(headMeta);

        ItemStack vip = new ItemStack(Material.DIAMOND);
        ItemMeta vipMeta = vip.getItemMeta();
        vipMeta.displayName(Component.text("会员系统",NamedTextColor.RED));
        vipMeta.lore(Collections.singletonList(Component.text("当前状态:" + configs.userdata.get(player.getName() + ".vip"),NamedTextColor.GREEN)));
        vip.setItemMeta(vipMeta);

        menuName.components.setItem(53, closeMenu);//关闭菜单
        menuName.components.setItem(4, board);//公告
        menuName.components.setItem(22, tpa);//传送
        menuName.components.setItem(11, survivalZone1);//生存一区
        menuName.components.setItem(20, survivalZone2);//生存二区
        menuName.components.setItem(31, shop);//商店
        menuName.components.setItem(13, head);//玩家信息
        menuName.components.setItem(15, vip);//会员系统



        /*索引
          0  1  2  3  4  5  6  7  8
          9  10 11 12 13 14 15 16 17
          18 19 20 21 22 23 24 25 26
          27 28 29 30 31 32 33 34 35
          36 37 38 39 40 41 42 43 44
          45 46 47 48 49 50 51 52 53
         */

        player.openInventory(menuName.components);
        return true;
    }
}
