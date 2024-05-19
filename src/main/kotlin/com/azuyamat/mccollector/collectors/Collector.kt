package com.azuyamat.mccollector.collectors

import com.azuyamat.mccollector.meta.CollectorMeta
import com.azuyamat.mccollector.CollectorRegistry
import com.azuyamat.mccollector.Restriction
import com.azuyamat.mccollector.builders.CollectorBuilder
import org.bukkit.entity.Player

/**
 * A collector is a class that prompts a player for input and collects it.
 * You must call [register] to start the collector.
 *
 * @param T The type of value to collect
 * @since 1.0.0
 */
abstract class Collector<T> internal constructor(
    protected open val player: Player,
    protected open val meta: CollectorMeta<T>
) {
    private var collected = false
    private var value: T? = null

    /**
     * Prompts the player for input.
     * This is already called when the collector is registered and
     * in the scheduler. Therefore, you should not call this method.
     *
     * @since 1.0.0
     */
    fun promptPlayer() {
        meta.prompt()
    }

    /**
     * Returns whether the collector has expired.
     *
     * @return Whether the collector has expired.
     * @since 1.0.0
     */
    fun isExpired(): Boolean {
        return System.currentTimeMillis() > meta.timeout
    }

    /**
     * Returns whether the collector has collected a value.
     *
     * @return Whether the collector has collected a value.
     * @since 1.0.0
     */
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

    internal fun onInvalid(result: Verifiable.ValidationResult, value: T) {
        meta.onInvalid(result, value)
    }

    /**
     * Returns the value collected by the collector.
     *
     * @return The value collected by the collector.
     * @since 1.0.0
     */
    fun getValue(): T? {
        return value
    }

    /**
     * Cancels the collector.
     *
     * @since 1.0.0
     */
    fun cancel() {
        onCancel()
    }

    /**
     * Collects a value.
     *
     * @param value The value to collect.
     * @since 1.0.0
     */
    fun collect(value: T) {
        onCollect(value)
    }

    /**
     * Registers the collector.
     */
    fun register() {
        if (CollectorRegistry.hasCollector(player.uniqueId)) {
            throw IllegalStateException("Player ${player.name} already has a collector")
        }
        CollectorRegistry.addCollector(player.uniqueId, this)
        promptPlayer()
    }

    internal fun resetTimeout() {
        meta.timeout = System.currentTimeMillis() + meta.timeoutDuration
    }

    internal fun hasRestriction(restriction: Restriction): Boolean {
        return meta.restrictions.contains(restriction)
    }
}