# zBox

利用设备管理员免 root 禁用/启用 app, 禁止 app 自启, 节省电量.

**什么是设备管理员？**

设备管理员是 Android 企业框架下的一个功能，提供了免 root 禁用/启用任意 app 的能力.

一台手机只能设置一个管理员.

# App截图

![](https://raw.githubusercontent.com/zogodo/zBox/refs/heads/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png)

![](https://raw.githubusercontent.com/zogodo/zBox/refs/heads/master/doc/index.jpg)

# 使用方法

## 1. 备份手机数据

备份好手机上的数据, 包括但不限于: 照片, 视频, 下载的文件, 微信QQ聊天记录, 二步验证秘钥



## 2. 检查手机是否有多个user

执行以下命令:

```sh
adb shell pm list users
```

如果显示如下:  Users: 下面只有一行 user 0, 可以继续第二步

```sh
$ adb shell pm list users
Users:
        UserInfo{0:机主:c13} running

```

如果显示如下:  Users: 下面有多行 user, 则需要删除 user 0 以外的 user.

```sh
$ adb shell pm list users
Users:
        UserInfo{0:机主:c13} running
        UserInfo{999:MultiApp:4001010} running

```

删除方法:

关闭 应用双开, 多用户, 访客模式 等

或者执行以下命令:  (999 是user id)

```sh
adb shell pm remove-user 999
```



## 3. 检查手机是否登录了account

执行以下命令:

```sh
adb shell dumpsys account
```

如果显示如下:  Accounts: 后面不是 0, 则需要退出列出的这些账号

```sh
$ adb shell dumpsys account
User UserInfo{0:机主:c13}:
  Accounts: 3
    Account {name=百度同步, type=com.baidu.searchbox.push.datasync}
    Account {name=美团账号, type=com.sankuai.meituan.pin.account.sync.type}
    Account {name=小红书, type=com.xingin.xhs.strategy.account}

...

```

退出方法:

打开手机设置 - 帐户, 删除 **所有帐户**, 包括你的 Google/华为/小米等系统帐号（之后可以再登录回来）

有时候退出了账号, 但是执行命令时还是有它, 此时可以卸载对应app

如果不想卸载, 可以先使用如下命令禁用对应app

```sh
adb shell pm disable-user --user 0 com.example.app
```

设置管理员成功后记得用如下命令启用回来:

```sh
adb shell pm enable com.example.app
```



## 4. 设置zBox为设备管理员

确保以上几步完成后执行以下命令:

```sh
adb shell dpm set-device-owner me.zogodo.zbox/.DeviceAdminReceiver
```

如果看到类似如下输出, 说明设置成功了. 可以开始使用 zBox了. 此时可以把之前删除的帐号登回来了

```
Success: Device owner set to package me.zogodo.zbox/.DeviceAdminReceiver
Active admin set to component me.zogodo.zbox/.DeviceAdminReceiver
```



# 方法2（恢复出厂设置）

手机回复出厂设置, 恢复后不登录任何账户, 所有设置均选择跳过,

打开开发者选项, 打开USB调试,

然后执行以下命令:

```sh
adb shell dpm set-device-owner me.zogodo.zbox/.DeviceAdminReceiver

#看到如下提示才算成功
Success: Device owner set to package me.zogodo.zbox/.DeviceAdminReceiver
Active admin set to component me.zogodo.zbox/.DeviceAdminReceiver
```



# 注意事项

- 如果不想用 zBox 了, 请先启用所有已禁用的 app 再卸载 zBox, 否则将 **再也无法找回已禁用的app**
- adb 工具可以在下列地址下载：Google 官方地址 （[Win](https://dl.google.com/android/repository/platform-tools-latest-windows.zip) [Mac](https://dl.google.com/android/repository/platform-tools-latest-darwin.zip) [Linux](https://dl.google.com/android/repository/platform-tools-latest-linux.zip)）



# 其它命令

```sh
#查看已存在的用户
adb shell pm list users

#删除用户
adb shell pm remove-user $ID

#移除设备管理员(移除后需要恢复出厂设置才能再设置其他管理员)
adb shell dpm remove-active-admin me.zogodo.zbox/.DeviceAdminReceiver

# 禁用应用
pm disable-user --user 0 com.example.app

# 启用应用
pm enable com.example.app

adb shell pm disable-user --user 0 com.tencent.qqlive

```



# 已测试成功设备

- [ ] vivo Pad Air (OriginOS-4 安卓14) (恢复出厂设置,没试过adb)
- [ ] 一加 3T (氢OS-5.0 安卓8.0) (恢复出厂设置,没试过dab)
- [x] vivo X27 (OriginOS-1.0 安卓10) (用adb禁用对应账户的包名后可以)
- [ ] iQOO Neo8 (OriginOS-5 安卓15) (无法删除XSpace-666用户,需要恢复出厂设置)
- [x] 华为 Hera-BD00 (华为OS-12.0 安卓12.0) (退出华为账号,卸载畅联和支付宝app后可以用adb)



# Telegram

[@zbox_app](https://t.me/zbox_app)

