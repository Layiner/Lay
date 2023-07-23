package net.layin.lay;

import net.layin.lay.inventory.menuName;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class configs implements CommandExecutor {
    //针对已经公开的源码阅读者，不要问为什么要这样做
    private static Map<FileConfiguration, File> map = new HashMap<>();
    public static FileConfiguration userdata ;
    public static FileConfiguration land ;
    public static FileConfiguration shop ;
    public static FileConfiguration datau;

    /**
     * 加载配置文件
     *
     * @param path 配置文件路径
     * @return 加载出的配置文件
     */
    public static FileConfiguration getConfiguration(String path) {
        File f = new File(Lay.getInstance().getDataFolder(), path);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
        map.put(fc, f);
        return fc;
    }

    /**
     * 保存所有该类的配置文件
     */
    public static void save() {
        Iterator<FileConfiguration> it = map.keySet().iterator();
        FileConfiguration fc;
        while (it.hasNext()) {
            fc = it.next();
            try {
                fc.save(map.get(fc));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 保存单个配置文件
     *
     * @param fc 要保存的配置文件
     */
    public static void save(FileConfiguration fc) {
        try {
            fc.save(map.get(fc));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 重新加载所有的配置文件
     */
    public static void reloadConfiguration() {
        map.clear();
        userdata = getConfiguration("userdata.yml");
        shop = getConfiguration("shop.yml");
        land = getConfiguration("land.yml");
        datau = getConfiguration("datau.yml");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings[0].equals("save")) {
            configs.save();
            return true;
        }
        if (strings[0].equals("reload")) {
            reloadConfiguration();
            menuName.SHOP_OUT_ITEM_INT = configs.shop.getInt("out.int");
            menuName.SHOP_IN_ITEM_INT = configs.shop.getInt("in.int");
            return true;
        }
        return true;
    }
}
