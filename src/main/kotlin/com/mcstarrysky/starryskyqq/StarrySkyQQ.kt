package com.mcstarrysky.starryskyqq

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.disablePlugin
import taboolib.common.platform.function.info
import taboolib.common.platform.function.pluginVersion
import taboolib.common.platform.function.warning
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

object StarrySkyQQ : Plugin() {

    @Config
    lateinit var conf: Configuration

    override fun onEnable() {
        if (conf.getBoolean("first")) {
            warning("检测到第一次运行，请前往配置文件修改数据库配置信息！")
            disablePlugin()
            return
        }
        info("Running StarrySkyQQ(Minecraft Side) Version $pluginVersion")
    }
}