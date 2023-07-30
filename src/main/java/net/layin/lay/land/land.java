package net.layin.lay.land;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.layin.lay.configs;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.IntStream;

import static net.layin.lay.configs.userdata;
import static net.layin.lay.inventory.menuName.online_player;

public class land implements TabExecutor {
    public static boolean deleteLand(Player p, String name) {
        List<Map<?, ?>> lands = configs.land.getMapList("lands");
        //找到领地
        Map<?, ?> landSuitName = lands.stream().filter(m -> m.get("name").equals(name)).findFirst().orElse(null);
        if (landSuitName == null) {
            p.sendMessage(Component.text("地皮不存在", NamedTextColor.RED));
            return true;
        }
        if (!landSuitName.get("owner").equals(p.getUniqueId().toString())) {
            p.sendMessage(Component.text("仅主人可删除地皮", NamedTextColor.RED));
            return true;
        } else {
            lands.remove(landSuitName);
            configs.land.set("lands", lands);
            p.sendMessage(Component.text("删除成功", NamedTextColor.GREEN));
            return true;
        }
    }

    public static boolean createLand(Player p, String name) {
        //获取上限
        int limit;
        switch (userdata.getString(p.getName() + ".vip", "")) {
            case "gold":
                limit = configs.land.getInt("limits.gold");
                break;
            case "diamond":
                limit = configs.land.getInt("limits.diamond");
                break;
            default:
                limit = configs.land.getInt("limits.common");
                break;
        }
        //获取地皮数
        int alreadyOwned;
        List<Map<?, ?>> lands = configs.land.getMapList("lands");
        //利用统计有多少主人是该玩家的地皮
        alreadyOwned = (int) IntStream.range(0, lands.size()).filter(i -> lands.get(i).get("owner").equals(p.getUniqueId().toString())).count();
        //如果达到上限
        if (!online_player.contains(p.getName())) {
            p.sendMessage(Component.text("请先登录", NamedTextColor.YELLOW));
            return true;
        }
        if (alreadyOwned >= limit) {
            //如果是钻石会员
            if (userdata.getString(p.getName() + ".vip", "").equals("diamond")) {
                p.sendMessage(Component.text("NO!不能再多了", NamedTextColor.RED));
                return true;
            }
            p.sendMessage(Component.text("地皮已达上限,请删除地皮或升级会员", NamedTextColor.RED));
            return true;
        } else {
            //检测是否在别人领地内
            if (solidCuboidOverlap(p)) {
                p.sendMessage(Component.text("创建领地与已有领地重合(建议跑远点,以防影响扩建)", NamedTextColor.RED));
                return true;
            } else {
                if (p.getLocation().getWorld().getName().equals("world")) {
                    if (lands.stream().noneMatch(m -> m.get("name").equals(name))) {
                        Map<String, Object> newLand = new HashMap<>();
                        newLand.put("name", name);
                        newLand.put("size", configs.land.getInt("default.size." + userdata.getString(p.getName() + ".vip", "common")));
                        newLand.put("owner", p.getUniqueId().toString());
                        newLand.put("x", p.getLocation().getBlockX());
                        newLand.put("y", p.getLocation().getBlockY());
                        newLand.put("z", p.getLocation().getBlockZ());
                        Map<String, String[]> permission = new HashMap<>();
                        permission.put(permissionsNames.ENTER, new String[]{p.getUniqueId().toString()});
                        permission.put(permissionsNames.INTERACT, new String[]{p.getUniqueId().toString()});
                        permission.put(permissionsNames.HURT, new String[]{p.getUniqueId().toString()});
                        newLand.put("permissions", permission);
                        lands.add(newLand);
                        configs.land.set("lands", lands);
                        p.sendMessage(Component.text("创建成功", NamedTextColor.GREEN));
                        return true;
                    } else {
                        p.sendMessage(Component.text("名称被占用", NamedTextColor.RED));
                        return true;
                    }
                } else {
                    p.sendMessage(Component.text("请在主世界创建", NamedTextColor.RED));
                    return true;
                }
            }
        }
    }
    /**
     * 给予或删除某玩家在某地皮的权限
     * @param sender 操作者
     * @param name  地皮名
     * @param permissionName 权限代号
     * @param receiver 被给予/删除权限的玩家
     * @param giveOrRemove 给予(true)/移除(false)
     * @return 运行是否成功(怎么可能不成功?!)*/
    public static boolean setPermission(Player sender,String name,String permissionName,Player receiver,boolean giveOrRemove){
        List<Map<?, ?>> lands = configs.land.getMapList("lands");
        //找到领地
        Map<?, ?> landSuitName = lands.stream().filter(m -> m.get("name").equals(name)).findFirst().orElse(null);
        if (landSuitName == null) {
            sender.sendMessage(Component.text("地皮不存在", NamedTextColor.RED));
            return true;
        }
        if (!landSuitName.get("owner").equals(sender.getUniqueId().toString())) {
            sender.sendMessage(Component.text("仅主人可给予权限", NamedTextColor.RED));
            return true;
        }
        Map<String,List<String>>permissions= (Map<String, List<String>>) landSuitName.get("permissions");
        List<String> permission=permissions.get(permissionName);
        if (permission == null) {
            sender.sendMessage(Component.text("权限不存在", NamedTextColor.RED));
            return true;
        }
        if (giveOrRemove){
            if(permission.contains(receiver.getUniqueId().toString())){
                sender.sendMessage(Component.text("该玩家已拥有权限", NamedTextColor.RED));
                return true;
            }
            permission.add(receiver.getUniqueId().toString());
            configs.land.set("land",lands);
            sender.sendMessage(Component.text("给予成功", NamedTextColor.GREEN));
            if(sender.getUniqueId().equals(receiver.getUniqueId())){
                sender.sendMessage(Component.text("不过为什么你自己没有权限=(,你们服主干什么破事了?!", NamedTextColor.YELLOW));
            }else{
                receiver.sendMessage(Component.text(sender.getName()+"已给予你TA"+name+"地皮的"+configs.land.getString("permissions."+permissionName), NamedTextColor.GREEN));
            }
            return true;
        }
        else{
            if(!permission.contains(receiver.getUniqueId().toString())){
                sender.sendMessage(Component.text("该玩家本就没有权限", NamedTextColor.RED));
                return true;
            }
            if(sender.getUniqueId().equals(receiver.getUniqueId())){
                sender.sendMessage(Component.text("别删自己的权限啊!(虽然真删了也不会有什么事)", NamedTextColor.RED));
                return true;
            }
            permission.remove(receiver.getUniqueId().toString());
            configs.land.set("land",lands);
            sender.sendMessage(Component.text("删除成功", NamedTextColor.GREEN));
            receiver.sendMessage(Component.text(sender.getName()+"已删除你TA"+name+"地皮的"+configs.land.getString("permissions."+permissionName), NamedTextColor.RED));
            return true;
        }
    }
    /**
     * 获取一个坐标所在领地
     *
     * @return 所在领地名, 没有则为null
     */
    public static String getLandLocatedIn(Location l) {
        if (!l.getWorld().getName().equals("world")) {
            return null;
        }
        List<Map<?, ?>> lands = configs.land.getMapList("lands");
        Map<?, ?> land = lands.stream().filter(m -> {
            if ((int) m.get("x") - (int) m.get("size") <= l.getX() && (int) m.get("x") + (int) m.get("size") >= l.getX()) {
                if ((int) m.get("z") - (int) m.get("size") <= l.getZ() && (int) m.get("z") + (int) m.get("size") >= l.getZ()) {
                    if ((int) m.get("y") - (configs.land.getInt("default.high") * configs.land.getInt("default.under")) / 10 <= l.getY() && (int) m.get("x") + (configs.land.getInt("default.high") * (10 - configs.land.getInt("default.under"))) / 10 >= l.getY()) {
                        return true;
                    }
                }
            }
            return false;
        }).findFirst().orElse(null);
//        Lay.getInstance().getLogger().info(l.toString()+"位于"+land);
        if (land == null) {
            return null;
        }
        return (String) land.get("name");
    }

    /**
     * 新产生的地皮是否与其他地皮重合
     *
     * @param p 创建地皮时玩家位置
     * @return 是否重合
     */
    //屎山,别展开
    public static boolean solidCuboidOverlap(Player p) {
        // 定义长方体A的点坐标和到边界的距离
        int xA = p.getLocation().getBlockX();
        int yA = p.getLocation().getBlockY();
        int zA = p.getLocation().getBlockZ();
        int distanceX = configs.land.getInt("default.size." + userdata.getString(p.getName() + ".vip", "common"));
        int distanceYUp = (configs.land.getInt("default.high") * (10 - configs.land.getInt("default.under"))) / 10;
        int distanceYDown = (configs.land.getInt("default.high") * configs.land.getInt("default.under")) / 10;
        int distanceZ = configs.land.getInt("default.size." + userdata.getString(p.getName() + ".vip", "common"));

        // 定义lands列表
        List<Map<?, ?>> lands = configs.land.getMapList("lands");
        if (lands.size() == 0) {
            return false;
        }

        // 判断lands中是否有长方体与A有重合部分
        for (Map<?, ?> land : lands) {
            int x = (int) land.get("x");
            int y = (int) land.get("y");
            int z = (int) land.get("z");
            int distanceToBoundaryX = (int) land.get("size");
            int distanceToBoundaryYUp = (configs.land.getInt("default.high") * (10 - configs.land.getInt("default.under"))) / 10;
            int distanceToBoundaryYDown = (configs.land.getInt("default.high") * configs.land.getInt("default.under")) / 10;
            int distanceToBoundaryZ = (int) land.get("size");

            if (xA + distanceX >= x - distanceToBoundaryX &&
                    xA - distanceX <= x + distanceToBoundaryX &&
                    yA + distanceYUp >= y - distanceToBoundaryYDown &&
                    yA - distanceYDown <= y + distanceToBoundaryYUp &&
                    zA + distanceZ >= z - distanceToBoundaryZ &&
                    zA - distanceZ <= z + distanceToBoundaryZ) {
                return true;
            }
        }
        //我着急编译，先帮你敲了，你自己改改 ai码的,我不清楚,你改哪了
        return false;
    }

    /**
     * 地皮扩大后是否与其他地皮重合
     *
     * @param name     地皮名
     * @param distance 扩大距离
     * @return 是否重合(不存在地皮也是true)
     */
    //屎山,别展开
    public static boolean solidCuboidOverlap(String name, int distance) {
        List<Map<?, ?>> lands = configs.land.getMapList("lands");
        Map<?, ?> landA = lands.stream().filter(m -> name.equals(m.get("name"))).findFirst().orElse(null);
        if (landA == null) {
            return true;
        }
        //如果留它毕自寻成大患
        lands.remove(landA);
        // 定义长方体A的点坐标和到边界的距离
        double xA = (double) landA.get("x");
        double yA = (double) landA.get("y");
        double zA = (double) landA.get("z");
        double distanceX = (double) landA.get("size");
        double distanceYUp = (double) (configs.land.getInt("default.high") * (10 - configs.land.getInt("default.under"))) / 10 + distance;
        double distanceYDown = (double) (configs.land.getInt("default.high") * configs.land.getInt("default.under")) / 10 + distance;
        double distanceZ = (double) landA.get("size");
        if (lands.size() == 0) {
            return false;
        }
        // 判断lands中是否有长方体与A有重合部分
        for (Map<?, ?> land : lands) {
            int x = (int) land.get("x");
            int y = (int) land.get("y");
            int z = (int) land.get("z");
            double distanceToBoundaryX = (int) land.get("size");
            double distanceToBoundaryYUp = (double) (configs.land.getInt("default.high") * (10 - configs.land.getInt("default.under"))) / 10;
            double distanceToBoundaryYDown = (double) (configs.land.getInt("default.high") * configs.land.getInt("default.under")) / 10;
            double distanceToBoundaryZ = (int) land.get("size");

            if (xA + distanceX >= x - distanceToBoundaryX &&
                    xA - distanceX <= x + distanceToBoundaryX &&
                    yA + distanceYUp >= y - distanceToBoundaryYDown &&
                    yA - distanceYDown <= y + distanceToBoundaryYUp &&
                    zA + distanceZ >= z - distanceToBoundaryZ &&
                    zA - distanceZ <= z + distanceToBoundaryZ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Component.text("你想啥呢,只有玩家才能用这指令", NamedTextColor.YELLOW));
            return true;
        }
        if (!online_player.contains(commandSender.getName())) {
            commandSender.sendMessage(Component.text("想啥呢,快点登录!", NamedTextColor.YELLOW));
            return true;
        }
        if (strings.length == 2) {
            if (strings[0].equals("create")) {
                return createLand((Player) commandSender, strings[1]);
            }
            if (strings[0].equals("delete")) {
                return deleteLand((Player) commandSender, strings[1]);
            }
        }
        if (strings.length ==4){
            if (strings[0].equals("setPermissions")){
                if (Arrays.asList("true", "false").contains(strings[3])){
                    Player receiver =Bukkit.getPlayer(strings[1]);
                    if(receiver==null){
                        commandSender.sendMessage(Component.text("查无此人",NamedTextColor.RED));
                        return true;
                    }else if(!Arrays.asList(permissionsNames.ENTER, permissionsNames.INTERACT, permissionsNames.HURT).contains(strings[2])){
                        commandSender.sendMessage(Component.text("不存在此权限",NamedTextColor.RED));
                        return true;
                    }
                    return setPermission((Player)commandSender,strings[0],strings[2],receiver,Boolean.getBoolean(strings[3]));
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("create", "delete", "setPermissions", "askPermissions", "goto");
        }
        if (args.length == 2) {
            if (args[0].equals("create")) {
                return Collections.singletonList("<地皮名称>");
            }
            if (args[0].equals("delete")) {
                return Collections.singletonList("<地皮名称>");
            }
            if (args[0].equals("setPermissions")) {
                return Collections.singletonList("<地皮名称>");
            }
            if (args[0].equals("askPermissions")) {
                return Collections.singletonList("<地皮名称>");
            }
            if (args[0].equals("goto")) {
                return Collections.singletonList("要去往的地皮名,需" + configs.land.getString(permissionsNames.ENTER));
            }
            if (args[0].equals("extend")){
                return Collections.singletonList("<地皮名称>");
            }
        }
        if (args.length == 3) {
            if (args[0].equals("setPermissions")) {
                return online_player;
            }
            if (args[0].equals("askPermissions")) {
                return Arrays.asList(permissionsNames.ENTER, permissionsNames.INTERACT, permissionsNames.HURT);
            }
            if (args[0].equals("extend")){
                return Collections.singletonList("<扩展大小>");
            }
        }
        if (args.length == 4) {
            if (args[0].equals("setPermissions")) {
                return Arrays.asList(permissionsNames.ENTER, permissionsNames.INTERACT, permissionsNames.HURT);
            }
        }
        if (args.length == 5) {
            if (args[0].equals("setPermissions")) {
                return Arrays.asList("true", "false");
            }
        }
        return null;
    }
}
