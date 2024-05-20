package com.azuyamat.mccollector.collectors

import com.azuyamat.mccollector.meta.CollectorMeta
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class InventoryCollector internal constructor(
    override val player: Player,
    override val meta: CollectorMeta<ItemStack>
) : Collector<ItemStack>(player, meta), Verifiable<ItemStack> {
    override fun verifyValue(value: ItemStack): Verifiable.ValidationResult {
        resetTimeout()
        return meta.verifyValue(value)
    }
}