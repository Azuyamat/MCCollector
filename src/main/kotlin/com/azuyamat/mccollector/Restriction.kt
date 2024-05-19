package com.azuyamat.mccollector

/**
 * Represents a restriction that can be applied to a collector.
 * Ex: [Restriction.CHAT] Player can't chat while the collector is active.
 *
 * @see com.azuyamat.mccollector.builders.CollectorBuilder.withRestriction
 * @since 1.0.0
 */
enum class Restriction {
    CHAT,
    COMMAND,
}