package com.azuyamat.mccollector.listeners

import com.azuyamat.mccollector.CollectorRegistry
import com.azuyamat.mccollector.Restriction
import com.azuyamat.mccollector.collectors.FancyNpcsCollector
import de.oliver.fancynpcs.api.events.NpcInteractEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

internal class FancyNpcsListener : Listener {
    @EventHandler
    fun onClick(event: NpcInteractEvent) {
        if (!CollectorRegistry.initialized) return

        val player = event.player
        val collector = CollectorRegistry.getCollector(player.uniqueId) ?: return
        collector.getRestriction(Restriction.FancyNpcs::class)?.let {
            event.isCancelled = true
            it.action(player)
        }

        if (collector !is FancyNpcsCollector) return
        val npc = event.npc

        val result = collector.verifyValue(npc)
        if (result.isValid) {
            collector.onCollect(npc)
        } else {
            collector.onInvalid(result, npc)
        }
    }
}