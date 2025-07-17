package com.azuyamat.mccollector.collectors

import com.azuyamat.mccollector.meta.CollectorMeta
import de.oliver.fancynpcs.api.Npc
import org.bukkit.entity.Player

/**
 * A collector that collects npc clicks from FancyNpcs.
 *
 * @param player the player to collect from
 * @param meta the meta of the collector
 * @see Collector
 * @since 1.3.0
 */
class FancyNpcsCollector internal constructor(
    override val player: Player,
    override val meta: CollectorMeta<Npc>
) : Collector<Npc>(player, meta), Verifiable<Npc> {
    override fun verifyValue(value: Npc): Verifiable.ValidationResult {
        resetTimeout()
        return meta.verifyValue(value)
    }
}