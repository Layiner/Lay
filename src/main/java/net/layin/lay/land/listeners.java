package net.layin.lay.land;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.layin.lay.Lay;
import net.layin.lay.configs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class listeners implements Listener {
    public static void havePermissionOrCancel(Cancellable e, String permission) {
        Logger logger = Lay.getInstance().getLogger();
//        logger.info("事件"+e.toString()+"触发");
        Player p;
        if (e instanceof PlayerEvent) {
            p = ((PlayerEvent) e).getPlayer();
        } else if (e instanceof EntityDamageByEntityEvent) {
            if (((EntityDamageByEntityEvent) e).getDamager() instanceof Player) {
                p = (Player) ((EntityDamageByEntityEvent) e).getDamager();
            } else {
                return;
            }
        } else {
            return;
        }
//        logger.info("触发玩家是"+p.getName());
        //如果在一个领地里
        if (land.getLandLocatedIn(p.getLocation()) != null) {
            //获取领地
            List<Map<?, ?>> lands = configs.land.getMapList("lands");
            Map<?, ?> landLocatedIn = lands.stream().filter(m -> m.get("name").equals(land.getLandLocatedIn(p.getLocation()))).findFirst().get();
//                logger.info("触发于地皮"+landLocatedIn.get("name")+ landLocatedIn);
            //如果没有权限
//                logger.info("玩家UUID:"+p.getUniqueId());
//                logger.info("主人UUID:"+landLocatedIn.get("owner"));
//                logger.info("权限列表:"+((Map<String, List<String>>) landLocatedIn.get("permissions")).get(permission));
//                logger.info("是否拥有权限:"+(((Map<String, List<String>>) landLocatedIn.get("permissions")).get(permission).contains(p.getUniqueId().toString())));
//                logger.info("最终判断结果(是否没有权限):"+(!landLocatedIn.get("owner").equals(p.getUniqueId().toString())&&!(((Map<String, List<String>>) landLocatedIn.get("permissions")).get(permission).contains(p.getUniqueId().toString()))));
            if (!landLocatedIn.get("owner").equals(p.getUniqueId().toString()) && !(((Map<String, List<String>>) landLocatedIn.get("permissions")).get(permission).contains(p.getUniqueId().toString()))) {
                e.setCancelled(true);
                p.sendMessage(Component.text("你正处于", NamedTextColor.RED)
                        .append(
                                Component.text(Bukkit.getPlayer(UUID.fromString((String) landLocatedIn.get("owner"))).getName(), NamedTextColor.GREEN)
                                        .hoverEvent(HoverEvent.showText(Component.text("点击以请求权限", NamedTextColor.YELLOW)))
                                        .clickEvent(ClickEvent.runCommand("/land askPermissions " + landLocatedIn.get("name") + " " + permission))
                        ).append(Component.text("的" + landLocatedIn.get("name") + "地皮", NamedTextColor.RED))
                );
            }
        }

    }

    @EventHandler
    public void restrictInteract(PlayerInteractEvent e) {
        havePermissionOrCancel(e, permissionsNames.INTERACT);
    }

    @EventHandler
    public void restrictInteractAtEntity(PlayerInteractAtEntityEvent e) {
        havePermissionOrCancel(e, permissionsNames.INTERACT);
    }

    @EventHandler
    public void restrictPortal(PlayerPortalEvent e) {
        havePermissionOrCancel(e, permissionsNames.INTERACT);
    }

    @EventHandler
    public void restrictMove(PlayerMoveEvent e) {
        havePermissionOrCancel(e, permissionsNames.ENTER);
    }

    @EventHandler
    public void restrictHurt(EntityDamageByEntityEvent e) {
        havePermissionOrCancel(e, permissionsNames.HURT);
    }
}
