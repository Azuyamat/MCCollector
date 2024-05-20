package com.azuyamat.mccollector

import org.bukkit.entity.Player

/**
 * Represents a restriction that can be applied to a collector.
 * Ex: [Restriction.Chat] Player can't chat while the collector is active.
 *
 * @see com.azuyamat.mccollector.builders.CollectorBuilder.withRestriction
 * @since 1.0.0
 */
sealed class Restriction(val action: (Player) -> Unit = {}) {
    class Chat(action: (Player) -> Unit = {}) : Restriction(action)
    class Command(action: (Player) -> Unit = {}) : Restriction(action)
}