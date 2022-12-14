package com.mcstarrysky.starryskyqq.database

import com.mcstarrysky.starryskyqq.StarrySkyQQ
import taboolib.common.platform.ProxyPlayer
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.HostSQL
import taboolib.module.database.Table

/**
 * @author xiaomu
 * @since 2022/9/4 09:39
 */
class DatabasePlayer {

    private val host = HostSQL(StarrySkyQQ.conf.getConfigurationSection("data.mysql")!!)

    private val table = Table("starryskyqq_player", host) {
        add("user") {
            type(ColumnTypeSQL.VARCHAR, 255) {
                options(ColumnOptionSQL.PRIMARY_KEY)
            }
        }
        add("name") {
            type(ColumnTypeSQL.TEXT)
        }
        add("qq") {
            type(ColumnTypeSQL.BIGINT)
        }
        add("time") {
            type(ColumnTypeSQL.BIGINT)
        }
    }

    private val dataSource = host.createDataSource()

    init {
        table.workspace(dataSource) { createTable(true) }.run()
    }

    fun has(user: ProxyPlayer): Boolean {
        return table.find(dataSource) { where { "user" eq user.uniqueId.toString() } }
    }

    companion object {

        val INSTANCE = DatabasePlayer()
    }
}