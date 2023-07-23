package net.layin.lay.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class menuName {
    //没十年脑血栓写不出这玩意儿，真麻烦
    public static final Component TITLE = Component.text("菜单》这是一切的起源", NamedTextColor.DARK_PURPLE);
    public static final Component TPA_TITLE = Component.text("传送功能》玩家列表", NamedTextColor.DARK_PURPLE);
    public static final Component SHOP_TITLE = Component.text("商店》点击商品即可交易!", NamedTextColor.DARK_PURPLE);


    public static final Component CLOSE_MENU = Component.text("关闭菜单", NamedTextColor.WHITE);
    public static final Component BOARD = Component.text("公告", NamedTextColor.RED);
    public static final Component TPA = Component.text("传送", NamedTextColor.LIGHT_PURPLE);
    public static final Component SURVIVAL_ZONE1 = Component.text("生存一区", NamedTextColor.GREEN);
    public static final Component SURVIVAL_ZONE2 = Component.text("生存二区", NamedTextColor.GREEN);
    public static final Component BUY = Component.text("购买物品", NamedTextColor.LIGHT_PURPLE);
    public static final Component COIN = Component.text("兑换金币", NamedTextColor.LIGHT_PURPLE);
    public static final Component SHOP = Component.text("商店", NamedTextColor.LIGHT_PURPLE);
    public static final Component HEAD = Component.text("玩家信息", NamedTextColor.LIGHT_PURPLE);


    public static final List<Component> BOARD_LORE = Collections.singletonList(Component.text("点击查看公告", NamedTextColor.YELLOW));
    public static final List<Component> TPA_LORE = Collections.singletonList(Component.text("点击可以传送至任意玩家(除了你自己!)", NamedTextColor.YELLOW));
    public static final List<Component> SURVIVAL_ZONE1_LORE = Collections.singletonList(Component.text("点击传送到生存一区", NamedTextColor.YELLOW));
    public static final List<Component> SURVIVAL_ZONE2_LORE = Collections.singletonList(Component.text("点击传送到生存二区", NamedTextColor.YELLOW));
    public static final List<Component> BUY_LORE = Collections.singletonList(Component.text("此选项会直接跳转至购物专区", NamedTextColor.YELLOW));
    public static final List<Component> COIN_LORE = Collections.singletonList(Component.text("此选项会直接跳转至兑换专区", NamedTextColor.YELLOW));
    public static final List<Component> SHOP_LORE = Collections.singletonList(Component.text("充满魔法?走一走瞧一瞧嘞!", NamedTextColor.DARK_PURPLE));
    public static final List<Component> HEAD_LORE = Collections.singletonList(Component.text("此处储存了您的信息", NamedTextColor.DARK_PURPLE));


    public static Inventory components;
    //设置在线名单
    public static List<String> online_player = new ArrayList<>();
    public static int SHOP_OUT_ITEM_INT = 0;
    public static int SHOP_IN_ITEM_INT = 0;
}
