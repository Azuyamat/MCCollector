package com.azuyamat.mccollector.listeners

import com.azuyamat.mccollector.CollectorRegistry
import com.azuyamat.mccollector.Restriction
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

internal class CommandListener : Listener {
    @EventHandler
    fun onCommand(event: PlayerCommandPreprocessEvent) {
        if (!CollectorRegistry.initialized) return

        val collector = CollectorRegistry.getCollector(event.player.uniqueId) ?: return
        collector.getRestriction(Restriction.Command::class)?.let {
            event.isCancelled = true
            it.action(event.player)
        }
    }
}