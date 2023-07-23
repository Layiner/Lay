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

public class login implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (online_player.contains(player.getName())) {
            commandSender.sendMessage(Component.text("你已经登录过了!!!", NamedTextColor.RED));
            return true;
        } else if (strings.length == 0) {
            commandSender.sendMessage(
                    Component.text("NO!你怎么不输入密码啊!", NamedTextColor.RED)
                            .append(Component.newline())
                            .append(Component.text("用法：/login <密码>", NamedTextColor.YELLOW)));
            return true;
        } else if (strings.length == 1) {
            //File userdataFile = new File(Lay.getPlugin(Lay.class).getDataFolder(), "userdata.yml");
            //FileConfiguration userdata = YamlConfiguration.loadConfiguration(userdataFile);


            //File onlineUserFile = new File(Lay.getPlugin(Lay.class).getDataFolder(), "online-user.yml");
            //FileConfiguration onlineUser = YamlConfiguration.loadConfiguration(onlineUserFile);
            //onlineUser.contains(player.getName())

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
            if (pw.equals(configs.userdata.getString(commandSender.getName() + ".password"))) {
                commandSender.sendMessage(Component.text("登录成功!!!", NamedTextColor.AQUA));
                //解除玩家的禁止活动
                player.setGameMode(GameMode.SURVIVAL);
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
                commandSender.sendMessage(Component.text("就你输错密码是吧?该不会···", NamedTextColor.RED));
                return true;
            }
        } else {
            commandSender.sendMessage(Component.text("呵，你会不会输啊?", NamedTextColor.RED));
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 2) {
            return null;
        }
        return Collections.singletonList("<密码>");
    }
}
