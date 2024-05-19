package com.azuyamat.mccollector.collectors

import com.azuyamat.mccollector.meta.CollectorMeta
import org.bukkit.entity.Player

/**
 * A collector that collects chat messages.
 *
 * @param player The player that the collector is for.
 * @param meta The meta for the collector.
 * @see Collector
 * @since 1.0.0
 */
class ChatCollector internal constructor(
    override val player: Player,
    override val meta: CollectorMeta<String>
) : Collector<String>(player, meta), Verifiable {
    override fun verifyValue(value: String): Verifiable.ValidationResult {
        resetTimeout()
        return meta.verifyValue(value)
    }
}