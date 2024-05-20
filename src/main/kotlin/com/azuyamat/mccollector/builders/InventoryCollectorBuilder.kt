package com.azuyamat.mccollector.builders

import com.azuyamat.mccollector.Restriction
import com.azuyamat.mccollector.collectors.Collector
import com.azuyamat.mccollector.collectors.InventoryCollector
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

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
}