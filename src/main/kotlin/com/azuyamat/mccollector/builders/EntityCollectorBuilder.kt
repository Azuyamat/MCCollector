package com.azuyamat.mccollector.builders

import com.azuyamat.mccollector.Restriction
import com.azuyamat.mccollector.collectors.Collector
import com.azuyamat.mccollector.collectors.EntityCollector
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

/**
 * A builder for creating an [EntityCollectorBuilder].
 *
 * @param prompt The prompt to display to the player.
 * @constructor Creates an EntityCollectorBuilder.
 * @since 1.1.0
 */
class EntityCollectorBuilder(
    prompt: () -> Unit,
) : CollectorBuilder<Entity>(prompt) {
    init {
        meta.restrictions.addAll(listOf(
            Restriction.Entity()
        ))
    }

    override fun build(player: Player): Collector<Entity> {
        return EntityCollector(player, meta)
    }
}