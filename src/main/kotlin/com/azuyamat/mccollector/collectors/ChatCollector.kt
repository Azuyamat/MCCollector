package com.azuyamat.mccollector.collectors

import com.azuyamat.mccollector.meta.CollectorMeta
import org.bukkit.entity.Player

class ChatCollector(
    override val player: Player,
    override val meta: CollectorMeta<String>
) : Collector<String>(player, meta) {

}