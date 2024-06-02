package com.azuyamat.mccollector.listeners

import com.azuyamat.mccollector.CollectorRegistry
import com.azuyamat.mccollector.Restriction
import com.azuyamat.mccollector.collectors.EntityCollector
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent

class EntityListener : Listener {
    @EventHandler
    fun onClick(event: PlayerInteractEntityEvent) {
        if (!CollectorRegistry.initialized) return

        val player = event.player
        val collector = CollectorRegistry.getCollector(player.uniqueId) ?: return
        collector.getRestriction(Restriction.Entity::class)?.let {
            event.isCancelled = true
            it.action(player)
        }

        if (collector !is EntityCollector) return
        val entity = event.rightClicked

        val result = collector.verifyValue(entity)
        if (result.isValid) {
            collector.onCollect(entity)
        } else {
            collector.onInvalid(result, entity)
        }
    }
}