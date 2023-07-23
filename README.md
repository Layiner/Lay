# Lay

![概览图](https://pic1.afdiancdn.com/user/3a611556114f11eca47d52540025c377/common/ccdd80a564da2254bfe5ffe85a230a90_w1600_h900_s1409.png?imageView2/1/w/1500/h/400)

欢迎各位大佬前来考古φ(*￣0￣)

本插件适用于我的世界1.20版本（及以上）PAPER端（包括SPIGOT端），本插件完全免费

<u>提示：本插件的源码特别提供学习目的使用</u>

## 特性

## 系统要求

- Minecraft服务器端：PAPER或者SPIGOT

- Minecraft服务器版本：1.20及以上

- 使用到的依赖（特别说明）：使用了PAPER端服务器目录下的服务器核心，其具体目录为：

​     `服务器目录\versions\1.20\paper-1.20.jar`

​     *您可以在pom.xml中添加此依赖以使用NBT标签*

## 如何安装插件

1. 从[版本列表](https://github.com/Layiner/Lay/releases)获取插件的最新版本。

2. 将插件文件放置在服务器的插件目录中。

   具体目录为`服务器目录\plugins`

3. 启动或重新加载服务器以加载插件。

4. 配置插件的任何必要选项。

5. 重新启动服务器以使更改生效。

## 如何配置插件

- 在*board.yml*文件中的board的键下配置服务器公告，可以编辑作者和文本颜色

- 在*datau.yml*文件中的sz和ss的键下配置生存区坐标
- 在*land.yml*文件中的limits的键下配置不同会员最大可获得的地皮数量，default的键下可配置默认地皮的高度和地下距离，包括不同会员的默认地皮大小（特指边长）

## 如何优雅地使用插件

指令：

```
/register <密码> <重复密码>
/login <密码>
/land create <地皮名称>
      delete <地皮名称>
      askpermissions <权限代号>
      setpermissions <权限代号>
/menu
/tpa <玩家名称>
     deny
     accept
/shop [可以无参数]		        #特别提醒：商品编号请前往shop.yml查看
      add <售价>  			#售价若填负数的话则是兑换货币
      set <原商品编号> <现售价>		#add和set仅管理员(OP)可执行，必须手持物品输入指令
/vip <set> <用户名> <类型>
```

## 如何贡献

感谢您对项目的兴趣！如果您想为插件做出贡献，请按照以下步骤进行：

1. 在GitHub上fork该存储库。
2. 创建您自己的分支：`git checkout -b feature/AmazingFeature`。
3. 提交您的更改：`git commit -m 'Add some AmazingFeature'`。
4. 推送到分支：`git push origin feature/AmazingFeature`。
5. 提交拉取请求。

我们将审查您的请求并与您合作确保其符合项目的质量标准。
