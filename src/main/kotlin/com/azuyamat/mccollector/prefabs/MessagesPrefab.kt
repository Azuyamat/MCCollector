package com.azuyamat.mccollector.prefabs

import com.azuyamat.mccollector.builders.CollectorBuilder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

/**
 * A prefab that adds messages to the collector.
 *
 * @param player The player to send messages to.
 * @return A lambda that applies the messages to the collector.
 * @see CollectorBuilder
 * @since 1.0.0
 */
class MessagesPrefab(
    private val player: Player
) : CollectorPrefab {
    override fun apply(collector: CollectorBuilder<*>): CollectorBuilder<*>.() -> Unit {
        return {
            this.onCancel {
                player.sendMessage(Component.text("Cancelled the operation.").color(NamedTextColor.RED))
            }
            this.onTimeout {
                player.sendMessage(Component.text("Operation timed out.").color(NamedTextColor.RED))
            }
            this.onInvalid { result, _ ->
                val errorMessage = result.errorMessage ?: "Invalid input."
                player.sendMessage(Component.text(errorMessage).color(NamedTextColor.RED))
            }
        }
    }
}