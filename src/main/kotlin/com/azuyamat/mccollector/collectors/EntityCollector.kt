package com.azuyamat.mccollector.collectors

import com.azuyamat.mccollector.meta.CollectorMeta
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

/**
 * A collector that collects entity clicks.
 *
 * @param player the player to collect from
 * @param meta the meta of the collector
 * @see Collector
 * @since 1.2.0
 */
class EntityCollector internal constructor(
    override val player: Player,
    override val meta: CollectorMeta<Entity>
) : Collector<Entity>(player, meta), Verifiable<Entity> {
    override fun verifyValue(value: Entity): Verifiable.ValidationResult {
        resetTimeout()
        return meta.verifyValue(value)
    }
}