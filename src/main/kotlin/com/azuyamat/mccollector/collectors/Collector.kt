package com.azuyamat.mccollector.collectors

import com.azuyamat.mccollector.meta.CollectorMeta
import com.azuyamat.mccollector.CollectorRegistry
import org.bukkit.entity.Player

abstract class Collector<T> internal constructor(
    protected open val player: Player,
    protected open val meta: CollectorMeta<T>
) {
    protected var collected = false
    protected var value: T? = null

    fun promptPlayer() {
        player.sendActionBar(meta.prompt)
    }

    fun isExpired(): Boolean {
        return System.currentTimeMillis() > meta.timeout
    }

    fun isCollected(): Boolean {
        return collected
    }

    internal fun onTimeout() {
        meta.onTimeout()
    }

    internal fun onCancel() {
        CollectorRegistry.removeCollector(player.uniqueId)
        meta.onCancel()
    }

    internal fun onCollect(value: T) {
        CollectorRegistry.removeCollector(player.uniqueId)
        this.value = value
        this.collected = true
        meta.onCollect(value)
    }

    fun verifyValue(value: T): Boolean {
        resetTimeout()
        return meta.verifyValue(value)
    }

    fun getValue(): T? {
        return value
    }

    fun cancel() {
        onCancel()
    }

    fun collect(value: T) {
        onCollect(value)
    }

    fun register() {
        if (CollectorRegistry.hasCollector(player.uniqueId)) {
            throw IllegalStateException("Player ${player.name} already has a collector")
        }
        CollectorRegistry.addCollector(player.uniqueId, this)
        promptPlayer()
        // TODO: Integrate into a registry
    }

    protected fun resetTimeout() {
        meta.timeout = System.currentTimeMillis() + meta.timeoutDuration
    }
}