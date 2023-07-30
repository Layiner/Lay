package net.layin.lay;

import net.layin.lay.command.login;
import net.layin.lay.command.register;
import net.layin.lay.command.tpa;
import net.layin.lay.command.vip;
import net.layin.lay.inventory.mainMenu;
import net.layin.lay.inventory.menuName;
import net.layin.lay.inventory.openMenu;
import net.layin.lay.inventory.shop;
import net.layin.lay.land.land;
import net.layin.lay.land.listeners;
import net.layin.lay.listener.chat;
import net.layin.lay.listener.fl;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static net.layin.lay.configs.getConfiguration;

public final class Lay extends JavaPlugin {
    private static Lay instance;

    public Lay() {
        super();
        instance = this;
    }

    public static Lay getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        //查看玩家登录事件的监听器请移步listener->fl.java

        this.saveResource("userdata.yml", false);
        this.saveResource("datau.yml", false);
        this.saveResource("board.yml", false);
        this.saveResource("land.yml", false);
        this.saveResource("shop.yml", false);

        configs.userdata = getConfiguration("userdata.yml");
        configs.land = getConfiguration("land.yml");
        configs.shop = getConfiguration("shop.yml");
        configs.datau = getConfiguration("datau.yml");
        menuName.SHOP_OUT_ITEM_INT = configs.shop.getInt("out.int");
        menuName.SHOP_IN_ITEM_INT=configs.shop.getInt("in.int");

        Objects.requireNonNull(getCommand("login")).setExecutor(new login());
        Objects.requireNonNull(getCommand("register")).setExecutor(new register());
        Objects.requireNonNull(getCommand("menu")).setExecutor(new openMenu());
        Objects.requireNonNull(getCommand("tpa")).setExecutor(new tpa());
        Objects.requireNonNull(getCommand("shop")).setExecutor(new shop());
        Objects.requireNonNull(getCommand("land")).setExecutor(new land());
        Objects.requireNonNull(getCommand("vip")).setExecutor(new vip());

        Objects.requireNonNull(getCommand("config")).setExecutor(new configs());

        registerListener(new chat());
        registerListener(new net.layin.lay.listener.login());
        registerListener(new fl());
        registerListener(new mainMenu());
        registerListener(new listeners());
    }

    @Override
    public void onDisable() {
        configs.save();
    }

    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }
}
