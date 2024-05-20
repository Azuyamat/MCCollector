package com.azuyamat.mccollector.collectors

import com.azuyamat.mccollector.meta.CollectorMeta
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * A collector that collects items from the player's inventory.
 *
 * @param player the player to collect from
 * @param meta the meta of the collector
 * @see Collector
 * @since 1.1.0
 */
class InventoryCollector internal constructor(
    override val player: Player,
    override val meta: CollectorMeta<ItemStack>
) : Collector<ItemStack>(player, meta), Verifiable<ItemStack> {
    override fun verifyValue(value: ItemStack): Verifiable.ValidationResult {
        resetTimeout()
        return meta.verifyValue(value)
    }
}