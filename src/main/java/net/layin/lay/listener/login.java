package net.layin.lay.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static net.layin.lay.inventory.menuName.online_player;

public class login implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        //File onlineUserFile = new File(Lay.getPlugin(Lay.class).getDataFolder(), "online-user.yml");
        //FileConfiguration onlineUser = YamlConfiguration.loadConfiguration(onlineUserFile);
        //!onlineUser.contains(player.getName())
        if (!online_player.contains(player.getName())) {
            event.setCancelled(true);
        }
    }
}
