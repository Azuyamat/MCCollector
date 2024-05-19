package com.azuyamat.mccollector

import com.azuyamat.mccollector.collectors.Collector
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

private typealias AnyCollector = Collector<*>

object CollectorRegistry {
    private const val TIMEOUT = 0L
    private const val UPDATE_INTERVAL = 20L // 1 second
    private val collectors = mutableMapOf<UUID, AnyCollector>()

    fun init(plugin: JavaPlugin) {
        Bukkit.getScheduler()
            .runTaskTimer(plugin, Runnable {
                updateCollectors()
            }, TIMEOUT, UPDATE_INTERVAL)
        // TODO: Register required event listeners
    }

    internal fun addCollector(uuid: UUID, collector: AnyCollector) {
        collectors[uuid] = collector
    }

    internal fun removeCollector(uuid: UUID) {
        collectors.remove(uuid)
    }

    internal fun getCollector(uuid: UUID): AnyCollector? {
        return collectors[uuid]
    }

    fun hasCollector(uuid: UUID): Boolean {
        return collectors.containsKey(uuid)
    }

    private fun updateCollectors() {
        if (collectors.isEmpty()) return
        collectors.filter {
            it.value.isExpired()
        }.forEach { (uuid, collector) ->
            collectors.remove(uuid)
            collector.onTimeout()
        }
        collectors.values.forEach(AnyCollector::promptPlayer)
    }
}