package com.azuyamat.mccollector.listeners

import com.azuyamat.mccollector.CollectorRegistry
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

internal class QuitListener : Listener {
    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        CollectorRegistry.removeCollector(event.player.uniqueId)
    }
}