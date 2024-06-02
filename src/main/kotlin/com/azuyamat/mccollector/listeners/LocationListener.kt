package com.azuyamat.mccollector.listeners

import com.azuyamat.mccollector.CollectorRegistry
import com.azuyamat.mccollector.Restriction
import com.azuyamat.mccollector.collectors.LocationCollector
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class LocationListener : Listener {
    @EventHandler
    fun onClick(event: PlayerInteractEvent) {
        if (!CollectorRegistry.initialized) return

        val player = event.player
        val collector = CollectorRegistry.getCollector(player.uniqueId) ?: return
        collector.getRestriction(Restriction.Location::class)?.let {
            event.isCancelled = true
            it.action(player)
        }

        if (collector !is LocationCollector) return
        val location = event.clickedBlock?.location ?: return

        val result = collector.verifyValue(location)
        if (result.isValid) {
            collector.onCollect(location)
        } else {
            collector.onInvalid(result, location)
        }
    }
}