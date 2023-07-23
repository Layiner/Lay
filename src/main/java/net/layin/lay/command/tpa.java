package net.layin.lay.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.layin.lay.Lay;
import net.layin.lay.inventory.menuName;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class tpa implements TabExecutor {
    static HashMap<UUID, UUID> targetMap = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Component.text("你想啥呢,只有玩家才能用这指令", NamedTextColor.YELLOW));
            return true;
        }
        if (!menuName.online_player.contains(commandSender.getName())) {
            commandSender.sendMessage(Component.text("你想啥呢,快点登录!", NamedTextColor.YELLOW));
            return true;
        }
        if (strings.length == 0) {
            commandSender.sendMessage(Component.text("错了奥,后面要加个玩家名称(你也可以按个空格看看后面的参数)", NamedTextColor.YELLOW));
            return true;
        }
        if (strings.length == 1) {
            //备注：这里玩家变量名为了方便思考，远程的为target，本体为there，不要纠结HashMap里面的key和value的值了
            if (strings[0].equals("accept")) {
                final Player target = (Player) commandSender;//此时commandSender是远程的target
                if (targetMap.containsValue(target.getUniqueId())) {
                    target.sendMessage(Component.text("您已成功接受对方请求!", NamedTextColor.GREEN));
                    for (Map.Entry<UUID, UUID> entry : targetMap.entrySet()) {
                        if (entry.getValue().equals(target.getUniqueId())) {
                            Player there = Bukkit.getPlayer(entry.getKey());//这里there是本体（发送请求的），所以是Key
                            //脑子爆浆！
                            TpaEvent event = new TpaEvent(there, there.getLocation());
                            Bukkit.getPluginManager().callEvent(event);
                            there.teleport(target);
                            targetMap.remove(entry.getKey());
                            there.sendMessage(Component.text("对方已接受您的传送!", NamedTextColor.GREEN));
                            break;
                        }
                    }
                } else {
                    target.sendMessage(Component.text("您好像没有需要处理的请求(或许超时了?)", NamedTextColor.YELLOW));
                }
                return true;
            }
            if (strings[0].equals("deny")) {
                final Player target = (Player) commandSender;
                if (targetMap.containsValue(target.getUniqueId())) {
                    for (Map.Entry<UUID, UUID> entry : targetMap.entrySet()) {
                        targetMap.remove(entry.getKey());
                        Player there = Bukkit.getPlayer(entry.getKey());
                        there.sendMessage(Component.text(target.getName() + "对方拒绝了您的传送请求", NamedTextColor.YELLOW));
                        target.sendMessage(Component.text("您已拒绝对方的请求", NamedTextColor.YELLOW));
                        break;
                    }
                    return true;
                }
                target.sendMessage(Component.text("您好像没有需要处理的请求(或许超时了?)", NamedTextColor.YELLOW));
                return true;
            }
            if (!menuName.online_player.contains(strings[0])) {
                commandSender.sendMessage(Component.text("玩家不存在或不在线,请检查玩家是否已经登录游戏(指输入指令/login)", NamedTextColor.YELLOW));
                return true;
            }
            final Player there = (Player) commandSender;
            Player target = Bukkit.getPlayer(strings[0]);
            if (target.getUniqueId().equals(there.getUniqueId())) {
                commandSender.sendMessage(Component.text("不要把你自己传送到自己啊喂!", NamedTextColor.YELLOW));
                return true;
            }
            if (targetMap.containsValue(target.getUniqueId())) {
                there.sendMessage(Component.text("你是否已经向对方发送过请求?", NamedTextColor.YELLOW));
                return true;
            }
            if (targetMap.containsKey(there.getUniqueId())) {
                there.sendMessage(Component.text("你是否已经向其他玩家发送过请求?", NamedTextColor.YELLOW));
                return true;
            }

            Component accept = Component.text("[接受]", NamedTextColor.GREEN)
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa accept"))
                    .hoverEvent(HoverEvent.showText(Component.text("点击以接受对方请求", NamedTextColor.GREEN)));
            Component deny = Component.text("[拒绝]", NamedTextColor.RED)
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa deny"))
                    .hoverEvent(HoverEvent.showText(Component.text("点击以拒绝对方请求", NamedTextColor.RED)));
            target.sendMessage(Component.text("玩家 " + there.getName() + " 希望传送至您所在的地方 ", NamedTextColor.LIGHT_PURPLE)
                    .append(accept).append(Component.text(" ")).append(deny));
            targetMap.put(there.getUniqueId(), target.getUniqueId());
            there.sendMessage(Component.text("已经成功发送请求至 " + target.getName() + " !", NamedTextColor.GREEN)
                    .append(Component.newline())
                    .append(Component.text("若5分钟内无反应，则传送取消!", NamedTextColor.YELLOW)));

            (new BukkitRunnable() {
                public void run() {
                    if (targetMap.containsKey(there.getUniqueId())) {
                        tpa.targetMap.remove(there.getUniqueId());
                        there.sendMessage(Component.text("由于超时，传送已取消!", NamedTextColor.RED));
                    }
                }
            }).runTaskLaterAsynchronously(Lay.getPlugin(Lay.class), 6000L);

            return true;
        } else {
            commandSender.sendMessage(Component.text("错了奥,后面只加个玩家名称(你也可以按个空格看看后面的参数)", NamedTextColor.YELLOW));
            return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length >= 2) {
            return null;
        }
        List<String> r = new ArrayList<>(menuName.online_player);
        r.add("accept");
        r.add("deny");
        return r;
    }
}

class TpaEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Player player;
    private Location locationBeforeTpa;
    private boolean isCancelled;

    public TpaEvent(Player player, Location locationBeforeTpa) {
        this.player = player;
        this.locationBeforeTpa = locationBeforeTpa;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}