package net.layin.lay.six;
/*
 *  折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨
 *  折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨
 *  折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨
 *  折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨 折磨
 * */

import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

/**
 * 利用反射改变物品的nbt
 */
public class nbt {
    /**
     * 获取CBS包名
     *
     * @return CBS包名
     */
    //以找 org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack为标
    public static String getCBSPackageName() {
        String version = Bukkit.getMinecraftVersion();
        String v1 = version.split("\\.")[0];
        String v2 = version.split("\\.")[1];
        for (int i = 1; i <= 20; i++) {
            try {
                Class.forName("org.bukkit.craftbukkit." + "v" + v1 + "_" + v2 + "_R" + i + ".inventory.CraftItemStack");
                return "org.bukkit.craftbukkit." + "v" + v1 + "_" + v2 + "_R" + i;
            } catch (ClassNotFoundException ignored) {
            }
        }
        return null;
    }

    /**
     * 设置物品的nbt
     *
     * @param is    物品
     * @param key   nbt的键
     * @param value nbt的值
     * @return 修改后的物品
     */
    public static ItemStack setNbt(ItemStack is, String key, String value) {
        try {
            reflectClassBuilder CraftItemStack = new reflectClassBuilder(getCBSPackageName() + ".inventory.CraftItemStack");
            net.minecraft.world.item.ItemStack nmsIs = (net.minecraft.world.item.ItemStack) CraftItemStack.doMethod("asNMSCopy", is).toObject();
            //反编译可得,返回NBTTagCompound的方法中v,w是无需参数的,两者都返回自己的nbt,其中w排除了null的可能所以w就是getTag()+if.....
            NBTTagCompound nbt = nmsIs.w();
            //看样子只有a是输入String,String,返回void的,还有各种参数,这不是设置还是啥
            nbt.a(key, value);
            //就c的参数叫nbt
            nmsIs.c(nbt);
            //设回去
            is = (ItemStack) CraftItemStack.doMethod("asBukkitCopy", nmsIs).toObject();
            return is;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
