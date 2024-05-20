package com.azuyamat.mccollector.builders

import com.azuyamat.mccollector.Restriction
import com.azuyamat.mccollector.collectors.Collector
import com.azuyamat.mccollector.collectors.InventoryCollector
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * A builder for creating an [InventoryCollector].
 *
 * @param prompt The prompt to display to the player.
 * @constructor Creates an InventoryCollectorBuilder.
 * @since 1.1.0
 */
class InventoryCollectorBuilder(
    prompt: () -> Unit,
) : CollectorBuilder<ItemStack>(prompt) {
    init {
        meta.restrictions.addAll(listOf(
            Restriction.InventoryManipulation()
        ))
    }

    override fun build(player: Player): Collector<ItemStack> {
        return InventoryCollector(player, meta)
    }

    companion object {
        /**
         * Opens an [InventoryCollectorBuilder] that opens the player's inventory when prompted.
         *
         * @param player The player to open the inventory for.
         * @return An [InventoryCollectorBuilder] that opens the player's inventory.
         * @since 1.1.0
         */
        fun openInventoryCollectorBuilder(player: Player) =
            InventoryCollectorBuilder {
                player.openInventory(player.inventory)
            }
    }
}