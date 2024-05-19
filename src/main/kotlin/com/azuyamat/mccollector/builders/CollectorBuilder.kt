package com.azuyamat.mccollector.builders

import com.azuyamat.mccollector.Restriction
import com.azuyamat.mccollector.collectors.Collector
import com.azuyamat.mccollector.collectors.Verifiable
import com.azuyamat.mccollector.meta.CollectorMeta
import com.azuyamat.mccollector.prefabs.CollectorPrefab
import org.bukkit.entity.Player

/**
 * A builder for creating a [Collector].
 *
 * @param T The type of the value to collect.
 * @property prompt The prompt to display to the player.
 * @constructor Creates a new [CollectorBuilder] with the given prompt.
 * @since 1.0.0
 */
abstract class CollectorBuilder<T> internal constructor(
    prompt: () -> Unit,
) {
    protected val meta = CollectorMeta<String>(prompt)

    /**
     * Sets the timeout duration for the collector.
     *
     * @param duration The duration of the collector before it times out.
     * @return The current [CollectorBuilder] instance.
     * @since 1.0.0
     */
    fun withTimeout(duration: Long): CollectorBuilder<T> {
        meta.timeoutDuration = duration
        return this
    }

    /**
     * Sets the action to be taken when the collector collects a value.
     *
     * @param onCollect The action to be taken when the collector collects a value.
     * @return The current [CollectorBuilder] instance.
     * @since 1.0.0
     */
    fun onCollect(onCollect: (String) -> Unit): CollectorBuilder<T> {
        meta.onCollect = onCollect
        return this
    }

    /**
     * Sets the action to be taken when the collector is cancelled.
     *
     * @param onCancel The action to be taken when the collector is cancelled.
     * @return The current [CollectorBuilder] instance.
     * @since 1.0.0
     */
    fun onCancel(onCancel: () -> Unit): CollectorBuilder<T> {
        meta.onCancel = onCancel
        return this
    }

    /**
     * Sets the action to be taken when the collector times out.
     *
     * @param onTimeout The action to be taken when the collector times out.
     * @return The current [CollectorBuilder] instance.
     * @since 1.0.0
     */
    fun onTimeout(onTimeout: () -> Unit): CollectorBuilder<T> {
        meta.onTimeout = onTimeout
        return this
    }

    /**
     * Sets the action to be taken when the collector collects an invalid value.
     *
     * @param onInvalid The action to be taken when the collector collects an invalid value.
     * @return The current [CollectorBuilder] instance.
     * @since 1.0.0
     */
    fun onInvalid(onInvalid: (Verifiable.ValidationResult, String) -> Unit): CollectorBuilder<T> {
        meta.onInvalid = onInvalid
        return this
    }

    /**
     * Sets the action to be taken to verify the value collected.
     *
     * @param verifyValue The action to be taken to verify the value collected.
     * @return The current [CollectorBuilder] instance.
     * @since 1.0.0
     */
    fun verifyValue(verifyValue: (String) -> Verifiable.ValidationResult): CollectorBuilder<T> {
        meta.verifyValue = verifyValue
        return this
    }

    /**
     * Adds a restriction to the collector.
     *
     * @param restriction The restriction to add to the collector.
     * @return The current [CollectorBuilder] instance.
     * @since 1.0.0
     */
    fun withRestriction(restriction: Restriction): CollectorBuilder<T> {
        meta.restrictions.add(restriction)
        return this
    }

    /**
     * Adds restrictions to the collector.
     *
     * @param restrictions The restrictions to add to the collector.
     * @return The current [CollectorBuilder] instance.
     * @since 1.0.0
     */
    fun withRestrictions(vararg restrictions: Restriction): CollectorBuilder<T> {
        meta.restrictions.addAll(restrictions)
        return this
    }

    /**
     * Removes a restriction from the collector.
     *
     * @param restriction The restriction to remove from the collector.
     * @return The current [CollectorBuilder] instance.
     * @since 1.0.0
     */
    fun removeRestriction(restriction: Restriction): CollectorBuilder<T> {
        meta.restrictions.remove(restriction)
        return this
    }

    /**
     * Applies the given prefab to the collector.
     *
     * @param prefab The prefab to apply to the collector.
     * @return The current [CollectorBuilder] instance.
     * @since 1.0.0
     */
    fun applyPrefab(prefab: CollectorPrefab): CollectorBuilder<T> {
        prefab.apply(this)
        return this
    }

    /**
     * Builds the collector with the given player.
     *
     * @param player The player to build the collector for.
     * @return The built collector.
     * @since 1.0.0
     */
    abstract fun build(player: Player): Collector<T>

    /**
     * Builds and registers the collector with the given player.
     *
     * @param player The player to build and register the collector for.
     * @since 1.0.0
     */
    fun buildAndRegister(player: Player) {
        return build(player).register()
    }
}