package com.azuyamat.mccollector.builders

import com.azuyamat.mccollector.Restriction
import com.azuyamat.mccollector.collectors.Collector
import com.azuyamat.mccollector.collectors.LocationCollector
import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * A builder for creating an [LocationCollectorBuilder].
 *
 * @param prompt The prompt to display to the player.
 * @constructor Creates an LocationCollectorBuilder.
 * @since 1.1.0
 */
class LocationCollectorBuilder(
    prompt: () -> Unit,
) : CollectorBuilder<Location>(prompt) {
    init {
        meta.restrictions.addAll(listOf(
            Restriction.Location()
        ))
    }

    override fun build(player: Player): Collector<Location> {
        return LocationCollector(player, meta)
    }
}