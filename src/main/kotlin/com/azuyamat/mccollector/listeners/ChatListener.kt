package com.azuyamat.mccollector.listeners

import com.azuyamat.mccollector.CollectorRegistry
import com.azuyamat.mccollector.Restriction
import com.azuyamat.mccollector.collectors.ChatCollector
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

internal class ChatListener : Listener {
    @EventHandler
    fun onChat(event: AsyncChatEvent) {
        if (!CollectorRegistry.initialized) return

        val collector = CollectorRegistry.getCollector(event.player.uniqueId) ?: return
        collector.getRestriction(Restriction.Chat::class)?.let {
            event.isCancelled = true
            it.action(event.player)
        }

        if (collector !is ChatCollector) return
        val content = (event.message() as TextComponent).content()
        if (content.equals("cancel", true)) {
            collector.onCancel()
            return
        }

        val result = collector.verifyValue(content)
        if (result.isValid) {
            collector.onCollect(content)
        } else {
            collector.onInvalid(result, content)
        }
    }
}