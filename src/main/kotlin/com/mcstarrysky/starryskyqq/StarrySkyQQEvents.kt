package com.mcstarrysky.starryskyqq

import com.mcstarrysky.starryskyqq.database.DatabaseCache
import com.mcstarrysky.starryskyqq.database.DatabasePlayer
import net.md_5.bungee.api.event.ServerConnectEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.adaptPlayer
import kotlin.random.Random

/**
 * @author xiaomu
 * @since 2022/9/4 10:03
 */
object StarrySkyQQEvents {

    @SubscribeEvent
    fun e(e: ServerConnectEvent) {
        if (!DatabasePlayer.INSTANCE.has(adaptPlayer(e.player))) {
            var code = DatabaseCache.INSTANCE.get(adaptPlayer(e.player))
            if (code == -1) {
                var random = Random.nextInt(10000)
                while (DatabaseCache.INSTANCE.has(random)) {
                    random = Random.nextInt(10000)
                }
                code = random
                DatabaseCache.INSTANCE.insert(adaptPlayer(e.player), code)
            }
            e.player.disconnect("欢迎新玩家，您是第一次进服，请前往QQ群「818114237」输入校验码「$code」完成绑定即可进服。")
        }
    }
}