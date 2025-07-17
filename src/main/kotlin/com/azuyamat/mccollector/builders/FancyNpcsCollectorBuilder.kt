package com.azuyamat.mccollector.builders

import com.azuyamat.mccollector.Restriction
import com.azuyamat.mccollector.collectors.Collector
import com.azuyamat.mccollector.collectors.FancyNpcsCollector
import de.oliver.fancynpcs.api.Npc
import org.bukkit.entity.Player

/**
 * A builder for creating an [FancyNpcsCollector].
 *
 * @param prompt The prompt to display to the player.
 * @constructor Creates an FancyNpcsCollectorBuilder.
 * @since 1.3.0
 */
class FancyNpcsCollectorBuilder(
    prompt: () -> Unit,
) : CollectorBuilder<Npc>(prompt) {
    init {
        meta.restrictions.addAll(listOf(
            Restriction.FancyNpcs()
        ))
    }

    override fun build(player: Player): Collector<Npc> {
        return FancyNpcsCollector(player, meta)
    }
}