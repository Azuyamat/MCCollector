package com.azuyamat.mccollector.collectors

import com.azuyamat.mccollector.meta.CollectorMeta
import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * A collector that collects location clicks.
 *
 * @param player the player to collect from
 * @param meta the meta of the collector
 * @see Collector
 * @since 1.2.0
 */
class LocationCollector internal constructor(
    override val player: Player,
    override val meta: CollectorMeta<Location>
) : Collector<Location>(player, meta), Verifiable<Location> {
    override fun verifyValue(value: Location): Verifiable.ValidationResult {
        resetTimeout()
        return meta.verifyValue(value)
    }
}