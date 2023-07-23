package net.layin.lay.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.layin.lay.Lay;
import net.layin.lay.configs;
import net.layin.lay.six.nbt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class mainMenu implements Listener {
    private int isOut = 0;//如果是1，则正处于购物区，2处于兑换区

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        InventoryView inv = player.getOpenInventory();
        if (inv.title().equals(menuName.TITLE)) {
            /*
            依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩
            依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩
            依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩
            依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩
            依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩 依托答辩
             */
            e.setCancelled(true);
            if (e.getRawSlot() < 0 || e.getRawSlot() >= e.getInventory().getSize()) {
                return;
            }
            if (e.getRawSlot() == 53) {
                player.closeInventory();
                return;
            }//关闭
            if (e.getRawSlot() == 4) {
                File boardDataFile = new File(Lay.getPlugin(Lay.class).getDataFolder(), "board.yml");
                FileConfiguration boardData = YamlConfiguration.loadConfiguration(boardDataFile);
                ItemStack b = new ItemStack(Material.WRITTEN_BOOK);
                BookMeta bM = (BookMeta) b.getItemMeta();
                String[] bText = Objects.requireNonNull(boardData.getString("board"), "").split("\\*\\*\\*");
                Component[] bcText;
                switch (Objects.requireNonNull(boardData.getString("color"))) {
                    case "RED":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.RED);
                        }
                        break;
                    case "DARK_RED":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.DARK_RED);
                        }
                        break;
                    case "GOLD":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.GOLD);
                        }
                        break;
                    case "YELLOW":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.YELLOW);
                        }
                        break;
                    case "DARK_GREEN":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.DARK_GREEN);
                        }
                        break;
                    case "GREEN":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.GREEN);
                        }
                        break;
                    case "AQUA":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.AQUA);
                        }
                        break;
                    case "DARK_AQUA":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.DARK_AQUA);
                        }
                        break;
                    case "DARK_BLUE":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.DARK_BLUE);
                        }
                        break;
                    case "BLUE":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.BLUE);
                        }
                        break;
                    case "LIGHT_PURPLE":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.LIGHT_PURPLE);
                        }
                        break;
                    case "DARK_PURPLE":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.DARK_PURPLE);
                        }
                        break;
                    case "WHITE":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.WHITE);
                        }
                        break;
                    case "GRAY":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.GRAY);
                        }
                        break;
                    case "DARK_GRAY":
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.DARK_GRAY);
                        }
                        break;
                    case "BLACK":
                    default:
                        bcText = new Component[bText.length];
                        for (int i = 0; i < bText.length; i++) {
                            bcText[i] = Component.text(bText[i], NamedTextColor.BLACK);
                        }
                }
                //.split("\\*\\*\\*");
                // Java 默认使用正则表达式进行查找，在正则表达式中，加号是特殊字符，需要使用 \ 转义；而在 Java 中，\ 本身也是特殊字符，因此就需要输入两个 \
                bM.pages(bcText);
                bM.setAuthor(boardData.getString("author"));
                bM.setTitle("服务器公告");

                b.setItemMeta(bM);
                player.openBook(b);
                return;
            }//公告
            if (e.getRawSlot() == 22) {
                if (!menuName.online_player.contains(player.getName())) {
                    player.closeInventory();
                    player.sendMessage(Component.text("请先登录再操作!", NamedTextColor.RED));
                    return;
                }
                player.closeInventory();//menuName.components.close();
                Inventory tpa = Bukkit.createInventory(player, 6 * 9, menuName.TPA_TITLE);
                List<String> fuck_u = new ArrayList<>(menuName.online_player);//从来没这么气过
                fuck_u.remove(player.getName());
                for (int i = 0; i < fuck_u.size(); i++) {
                    ItemStack a = new ItemStack(Material.PLAYER_HEAD);
                    a = nbt.setNbt(a, "SkullOwner", fuck_u.get(i));
                    assert a != null;
                    ItemMeta am = a.getItemMeta();
                    am.displayName(Component.text(fuck_u.get(i), NamedTextColor.BLUE));
                    a.setItemMeta(am);
                    tpa.setItem(i, a);
                }
                player.openInventory(tpa);
                return;
            }//传送
            FileConfiguration dt = YamlConfiguration.loadConfiguration(new File(Lay.getPlugin(Lay.class).getDataFolder(), "datau.yml"));
            if (e.getRawSlot() == 11) {
                //FileConfiguration dt = YamlConfiguration.loadConfiguration(new File(Lay.getPlugin(Lay.class).getDataFolder(), "datau.yml"));
                player.teleport(new Location(Bukkit.getWorld("world"), dt.getInt("sz.x"), dt.getInt("sz.y"), dt.getInt("sz.z")));
                return;
            }//生存一区
            if (e.getRawSlot() == 20) {
                //FileConfiguration dt = YamlConfiguration.loadConfiguration(new File(Lay.getPlugin(Lay.class).getDataFolder(), "datau.yml"));
                player.teleport(new Location(Bukkit.getWorld("world"), dt.getInt("ss.x"), dt.getInt("ss.y"), dt.getInt("ss.z")));
                return;
            }//生存二区
            if (e.getRawSlot() == 31) {
                player.closeInventory();
                player.performCommand("shop");
                return;
            }//商店
        }
        if (inv.title().equals(menuName.TPA_TITLE)) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            player.performCommand("tpa " + e.getCurrentItem().getItemMeta().getDisplayName().substring(2));
            //不得已使用弃用方法，不然好麻烦
            player.closeInventory();
            return;
        }
        if (inv.title().equals(menuName.SHOP_TITLE)) {
            e.setCancelled(true);
            if (e.getRawSlot() == 53) {
                player.closeInventory();
                return;
            }
            //FileConfiguration du = YamlConfiguration.loadConfiguration(new File(net.layin.lay.Lay.getPlugin(net.layin.lay.Lay.class).getDataFolder(), "shop.yml"));
            if (e.getRawSlot() == 0) {
                isOut = 1;
                for (int u = 9; u <= 44; u++) {
                    e.getInventory().setItem(u, new ItemStack(Material.AIR));
                }
                //备用方法：将in以map形式读出来（还未实践，也不想实践）
                for (int item = 1; item <= menuName.SHOP_OUT_ITEM_INT; item++) {
                    ItemStack is = new ItemStack(Objects.requireNonNull(configs.shop.getItemStack("out." + item)));
                    //is.setAmount(configs.shop.getInt("out.coin." + item));
                    ItemMeta isM = is.getItemMeta();
                    List<Component> l;
                    try {
                        l = new ArrayList<>(Objects.requireNonNull(isM.lore()));
                    } catch (NullPointerException zenmekeneng) {
                        l = new ArrayList<>();
                    }
                    l.add(Component.text("售价:" + configs.shop.getInt("out.coin." + item)));
                    isM.lore(l);

                    //System.out.println(configs.shop.getInt("out.coin." + item));

                    is.setItemMeta(isM);
                    e.getInventory().setItem(item + 8, is);
                }
                return;
            }
            if (e.getRawSlot() == 8) {
                isOut = 2;
                for (int u = 9; u <= 44; u++) {
                    e.getInventory().setItem(u, new ItemStack(Material.AIR));
                }
                for (int abc = 1; abc <= menuName.SHOP_OUT_ITEM_INT; abc++) {
                    ItemStack is = new ItemStack(Objects.requireNonNull(configs.shop.getItemStack("in." + abc)));
                    ItemMeta isM = is.getItemMeta();
                    List<Component> l;
                    try {
                        l = new ArrayList<>(Objects.requireNonNull(isM.lore()));
                    } catch (NullPointerException zenmekeneng) {
                        l = new ArrayList<>();
                    }
                    l.add(Component.text("可以兑换" + configs.shop.getInt("out.coin." + abc) + "个金币"));
                    isM.lore(l);
                    is.setItemMeta(isM);
                    e.getInventory().setItem(abc + 8, is);
                }
                return;
            }
            if (e.getRawSlot() > 8 && e.getRawSlot() < 45) {
                if (isOut == 1) {
                    if (Objects.equals(e.getCurrentItem(), Material.AIR)) {
                        return;
                    }
                    int coin = configs.userdata.getInt(player.getName() + ".coin");
                    int cost = configs.shop.getInt("out.coin." + (e.getRawSlot() - 8));
                    if (coin < cost) {
                        player.closeInventory();
                        player.sendMessage(Component.text("金币不足,无法购买!", NamedTextColor.RED));
                        return;
                    }
                    ItemStack give = configs.shop.getItemStack("out." + (e.getRawSlot() - 8));
                    player.getInventory().addItem(give);
                    try {
                        player.sendMessage(Component.text("您已购买商品:", NamedTextColor.GREEN)
                                .hoverEvent(HoverEvent.showText(Component.text(Objects.requireNonNull(give.getItemMeta().displayName()).toString())))
                                .append(Component.text("如果背包已经满,你真的倒大霉了", NamedTextColor.YELLOW)));
                    } catch (NullPointerException asd) {
                        player.sendMessage(Component.text("您已购买商品:", NamedTextColor.GREEN)
                                .append(Component.text("如果背包已经满,你真的倒大霉了", NamedTextColor.YELLOW)));
                    }
                    configs.userdata.set(player.getName() + ".coin", coin - cost);
                    return;
                }
                if (isOut == 2) {
                    if (e.getCurrentItem().equals(Material.AIR)) {
                        return;
                    }
                    int coin = configs.userdata.getInt(player.getName() + ".coin");
                    int get = configs.shop.getInt("in.coin." + (e.getRawSlot() - 8));
                    ItemStack in = configs.shop.getItemStack("in." + (e.getRawSlot() - 8));
                    if (!player.getInventory().contains(in)) {
                        player.sendMessage(Component.text("你咋没有这商品呢,不给换!", NamedTextColor.YELLOW));
                        return;
                    }
                    //TODO:这里会删掉背包里所有相关的东西
                    //for获取玩家背包里的东西，检测到一个就松手
                    for(int i = 0;i<=player.getInventory().getSize();i++){
                        if(Objects.equals(player.getInventory().getItem(i), in)){
                            player.getInventory().setItem(i,new ItemStack(Material.AIR));
                            break;
                        }
                    }
                    //player.getInventory().setItem();
                    //player.getInventory().remove(in);
                    player.sendMessage(Component.text("您已获得" + -get + "个金币", NamedTextColor.GREEN));
                    configs.userdata.set(player.getName() + ".coin", coin + -get);
                    return;
                }
            }
        }
    }
}
