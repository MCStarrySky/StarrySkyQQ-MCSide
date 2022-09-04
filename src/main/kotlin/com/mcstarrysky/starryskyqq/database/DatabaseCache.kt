package com.mcstarrysky.starryskyqq.database

import com.mcstarrysky.starryskyqq.StarrySkyQQ
import taboolib.common.platform.ProxyPlayer
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.HostSQL
import taboolib.module.database.Table
import kotlin.random.Random

/**
 * @author xiaomu
 * @since 2022/9/4 09:55
 */
class DatabaseCache {

    private val host = HostSQL(StarrySkyQQ.conf.getConfigurationSection("data.mysql")!!)

    private val table = Table("starryskyqq_cache", host) {
        add("code") {
            type(ColumnTypeSQL.INT) {
                options(ColumnOptionSQL.PRIMARY_KEY)
            }
        }
        add("user") {
            type(ColumnTypeSQL.VARCHAR, 255)
        }
        add("name") {
            type(ColumnTypeSQL.TEXT)
        }
    }

    private val dataSource = host.createDataSource()

    init {
        table.workspace(dataSource) { createTable(true) }.run()
    }

    fun insert(user: ProxyPlayer, code: Int) {
        table.insert(dataSource, "code", "user", "name") {
            value(code, user.uniqueId.toString(), user.name)
        }
    }

    fun has(code: Int): Boolean {
        return table.find(dataSource) { where { "code" eq code } }
    }

    fun get(user: ProxyPlayer): Int {
        return table.select(dataSource) {
            where { "user" eq user.uniqueId.toString() }
        }.firstOrNull { getInt("code") } ?: -1
    }

    companion object {

        val INSTANCE = DatabaseCache()
    }
}