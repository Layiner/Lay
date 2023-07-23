package net.layin.lay.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.layin.lay.configs;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

import static net.layin.lay.inventory.menuName.online_player;

public class register implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage(
                    Component.text("NO！你怎么不输入密码啊！", NamedTextColor.RED)
                            .append(Component.newline())
                            .append(Component.text("用法：/register <密码> <重复密码>", NamedTextColor.YELLOW)));
            return true;
        } else if (strings.length == 1) {
            commandSender.sendMessage(
                    Component.text("你需要重复你的密码!!!", NamedTextColor.RED)
                            .append(Component.newline())
                            .append(Component.text("用法：/register <密码> <重复密码>", NamedTextColor.YELLOW)));
            return true;
        } else if (strings.length == 2) {
            if (strings[0].equals(strings[1])) {
                if (configs.userdata.contains(commandSender.getName())) {
                    commandSender.sendMessage(Component.text("你已经注册过了,搁这儿卡bug是吧?", NamedTextColor.YELLOW));
                    return true;
                }
                String pw;
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    byte[] encryptedBytes = md.digest(strings[0].getBytes());
                    StringBuilder sb = new StringBuilder();
                    for (byte b : encryptedBytes) {
                        sb.append(String.format("%02x", b & 0xff));
                    }
                    pw = sb.toString();
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }//MD5加密
                configs.userdata.set(commandSender.getName() + ".password", pw);
                configs.userdata.set(commandSender.getName() + ".vip", "common");
                configs.userdata.set(commandSender.getName() + ".coin", 0);
                commandSender.sendMessage(Component.text("注册成功!", NamedTextColor.GREEN)
                        .append(Component.text("已经为您自动登录!", NamedTextColor.GREEN)));
                //解除玩家的禁止活动
                Player player = (Player) commandSender;
                player.setGameMode(GameMode.SURVIVAL);

                net.layin.lay.listener.fl.createMenuItem();
                player.getInventory().addItem(net.layin.lay.listener.fl.menuItem);
                player.updateInventory();

                //添加online用户名单
                //onlineUser.set(commandSender.getName(),"null");
                online_player.add(commandSender.getName());
                /*try {
                    onlineUser.save(onlineUserFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }*/
                return true;
            } else {
                commandSender.sendMessage(
                        Component.text("前后的密码需要一致!!!", NamedTextColor.RED)
                                .append(Component.newline())
                                .append(Component.text("用法：/register <密码> <重复密码>", NamedTextColor.YELLOW)));
                return true;
            }
        } else {
            commandSender.sendMessage(Component.text("啊?", NamedTextColor.YELLOW));
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            switch (args.length) {
                case 1:
                    return Collections.singletonList("<密码>");
                case 2:
                    return Collections.singletonList("<再次输入密码>");
                default:
                    return null;
            }
        }
        return null;
    }
}