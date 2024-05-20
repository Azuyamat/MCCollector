package com.azuyamat.mccollector.builders

import com.azuyamat.mccollector.Restriction
import com.azuyamat.mccollector.collectors.ChatCollector
import com.azuyamat.mccollector.collectors.Collector
import com.azuyamat.mccollector.collectors.Verifiable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

/**
 * A builder for creating a [ChatCollector].
 * This builder uses the [Restriction.Chat] and [Restriction.Command] restrictions by default.
 *
 * @param prompt The prompt to display to the player.
 * @constructor Creates a new [ChatCollectorBuilder] with the given prompt.
 * @since 1.0.0
 */
class ChatCollectorBuilder(
    prompt: () -> Unit,
) : CollectorBuilder<String>(prompt) {
    init {
        meta.restrictions.addAll(listOf(
            Restriction.Chat(),
            Restriction.Command()
        ))
    }

    override fun build(player: Player): Collector<String> {
        return ChatCollector(player, meta)
    }

    companion object {
        /**
         * Creates a [ChatCollectorBuilder] that asks the player if they are sure.
         *
         * @param player The player to ask.
         * @param onConfirm The action to run if the player confirms.
         * @param onCancel The action to run if the player cancels.
         * @return A [ChatCollectorBuilder] that asks the player if they are sure.
         * @since 1.0.0
         */
        fun areYouSure(player: Player, onConfirm: () -> Unit, onCancel: () -> Unit = {}): ChatCollectorBuilder {
            return ChatCollectorBuilder {
                player.sendMessage(Component.text("Are you sure? Type 'yes' to confirm or 'no' to cancel.").color(NamedTextColor.GRAY))
            }.verifyValue {
                when (it.lowercase()) {
                    "yes" -> Verifiable.ValidationResult.valid()
                    "no" -> Verifiable.ValidationResult.valid()
                    else -> Verifiable.ValidationResult.invalid("Invalid input.")
                }
            }.onCollect {
                if (it.equals("yes", true)) {
                    onConfirm()
                } else {
                    onCancel()
                }
            } as ChatCollectorBuilder
        }
    }
}