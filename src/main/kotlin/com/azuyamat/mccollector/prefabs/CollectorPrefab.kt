package com.azuyamat.mccollector.prefabs

import com.azuyamat.mccollector.builders.CollectorBuilder

/**
 * A prefab that applies predefined actions to a collector.
 *
 * @see CollectorBuilder
 * @since 1.0.0
 */
@Suppress("UNUSED")
interface CollectorPrefab {
    /**
     * Applies predefined actions to a collector.
     *
     * @param collector The collector to apply the actions to.
     * @return A lambda that applies the actions to the collector.
     * @since 1.0.0
     */
    fun apply(collector: CollectorBuilder<*>): CollectorBuilder<*>.() -> Unit
}