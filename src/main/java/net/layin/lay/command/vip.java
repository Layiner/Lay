package net.layin.lay.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.layin.lay.configs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
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
        if (!player.isOp()) {
            player.sendMessage(Component.text("你想啥呢,只有管理员才能用这指令", NamedTextColor.YELLOW));
            return true;
        }
        if (strings.length == 3) {
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
