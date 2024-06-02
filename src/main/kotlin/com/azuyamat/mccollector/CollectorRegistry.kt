package com.azuyamat.mccollector

import com.azuyamat.mccollector.CollectorRegistry.init
import com.azuyamat.mccollector.CollectorRegistry.initialized
import com.azuyamat.mccollector.collectors.Collector
import com.azuyamat.mccollector.listeners.*
import com.azuyamat.mccollector.listeners.ChatListener
import com.azuyamat.mccollector.listeners.CommandListener
import com.azuyamat.mccollector.listeners.InventoryListener
import com.azuyamat.mccollector.listeners.QuitListener
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

private typealias AnyCollector = Collector<*>

/**
 * The registry for all collectors.
 * This class is responsible for managing all collectors and updating them.
 * Must call [init] to start the registry.
 *
 * @property initialized Whether the registry has been initialized.
 * @since 1.0.0
 */
object CollectorRegistry {
    private const val TIMEOUT = 0L
    private const val UPDATE_INTERVAL = 20L // 1 second
    private val collectors = mutableMapOf<UUID, AnyCollector>()
    var initialized = false
        private set

    /**
     * Initializes the registry.
     *
     * @param plugin The plugin to register the listeners to.
     * @since 1.0.0
     */
    fun init(plugin: JavaPlugin) {
        try {
            plugin.server.scheduler
                .runTaskTimer(plugin, Runnable {
                    updateCollectors()
                }, TIMEOUT, UPDATE_INTERVAL)
            listOf(
                QuitListener(),
                ChatListener(),
                CommandListener(),
                InventoryListener(),
                LocationListener(),
                EntityListener()
            ).forEach {
                plugin.server.pluginManager.registerEvents(it, plugin)
            }
            initialized = true
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            initialized = false
        }
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

    /**
     * Checks if a player has a collector.
     *
     * @param uuid The UUID of the player.
     * @return Whether the player has a collector.
     * @since 1.0.0
     */
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
        collectors.filter {
            it.value.isCollected()
        }.forEach {
            collectors.remove(it.key)
        }
        collectors.values
            .filter(AnyCollector::readyForNextPrompt)
            .forEach(AnyCollector::promptPlayer)
    }
}