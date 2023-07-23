package net.layin.lay.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.layin.lay.configs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

import static net.layin.lay.inventory.menuName.online_player;

public class chat implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) throws IOException {
        Player player = event.getPlayer();
        /*File onlineUserFile = new File(Lay.getPlugin(Lay.class).getDataFolder(), "online-user.yml");
        FileConfiguration onlineUser = YamlConfiguration.loadConfiguration(onlineUserFile);
        onlineUser.set(player.getName(),null);
        onlineUser.save(onlineUserFile);*/
        online_player.remove(player.getName());
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        //取消玩家聊天   event.setCancelled(true);

        //File onlineUserFile = new File(Lay.getPlugin(Lay.class).getDataFolder(), "online-user.yml");
        //FileConfiguration onlineUser = YamlConfiguration.loadConfiguration(onlineUserFile);

        //File userdataFile = new File(Lay.getPlugin(Lay.class).getDataFolder(), "userdata.yml");
        //FileConfiguration userdata = YamlConfiguration.loadConfiguration(userdataFile);

        //!onlineUser.contains(player.getName())
        if (!online_player.contains(player.getName())) {
            event.setCancelled(true);
            player.sendMessage(Component.text("请先登录或注册再发言！", NamedTextColor.RED));
        } else {
            event.setCancelled(true);
            String vip = (String) configs.userdata.get(player.getName() + ".vip");
            Component p = Component.text(player.getName(), NamedTextColor.WHITE)
                    .clickEvent(ClickEvent.runCommand("/tpa " + player.getName()))
                    .hoverEvent(HoverEvent.showText(Component.text("点击以向" + player.getName() + "发送传送请求", NamedTextColor.GREEN)));
            if (vip.equals("common")) {
                Bukkit.broadcast(Component.text("")
                        .append(Component.text("[注册会员]", NamedTextColor.GRAY))
                        .append(p)
                        .append(Component.text("：", NamedTextColor.WHITE))
                        .append(event.message()));
            }
            if (vip.equals("gold")) {
                Bukkit.broadcast(Component.text("")
                        .append(Component.text("[黄金会员]", NamedTextColor.GOLD))
                        .append(p)
                        .append(Component.text("：", NamedTextColor.WHITE))
                        .append(event.message()));
            }
            if (vip.equals("diamond")) {
                Component p2 = Component.text(player.getName(), NamedTextColor.RED)
                        .clickEvent(ClickEvent.runCommand("/tpa " + player.getName()))
                        .hoverEvent(HoverEvent.showText(Component.text("点击以向" + player.getName() + "发送传送请求", NamedTextColor.GREEN)));
                Bukkit.broadcast(Component.text("")
                        .append(Component.text("[钻石会员]", NamedTextColor.AQUA))
                        .append(p2)
                        .append(Component.text("：", NamedTextColor.WHITE))
                        .append(event.message()));
            }
        }
    }
}
