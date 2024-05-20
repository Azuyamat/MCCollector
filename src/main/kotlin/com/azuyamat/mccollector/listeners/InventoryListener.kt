package com.azuyamat.mccollector.listeners

import com.azuyamat.mccollector.CollectorRegistry
import com.azuyamat.mccollector.Restriction
import com.azuyamat.mccollector.collectors.InventoryCollector
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

internal class InventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (!CollectorRegistry.initialized) return

        val player = event.whoClicked as? Player ?: return
        val collector = CollectorRegistry.getCollector(player.uniqueId) ?: return
        collector.getRestriction(Restriction.InventoryManipulation::class)?.let {
            event.isCancelled = true
            it.action(player)
        }

        if (collector !is InventoryCollector) return
        val item = event.currentItem ?: return

        val result = collector.verifyValue(item)
        if (result.isValid) {
            player.closeInventory()
            collector.onCollect(item)
        } else {
            collector.onInvalid(result, item)
        }
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (!CollectorRegistry.initialized) return

        val player = event.player as? Player ?: return
        val collector = CollectorRegistry.getCollector(player.uniqueId) ?: return

        if (collector !is InventoryCollector) return
        collector.onCancel()
    }
}