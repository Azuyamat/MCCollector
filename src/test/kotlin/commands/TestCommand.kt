package commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import com.azuyamat.mccollector.builders.ChatCollectorBuilder
import com.azuyamat.mccollector.builders.EntityCollectorBuilder
import com.azuyamat.mccollector.builders.InventoryCollectorBuilder
import com.azuyamat.mccollector.builders.LocationCollectorBuilder
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

@CommandAlias("test")
class TestCommand : BaseCommand() {
    @Default
    fun onCommand(player: Player) {
        val collector = ChatCollectorBuilder.areYouSure(player, {
            player.sendMessage("Yes")
        }) {
            player.sendMessage("No")
        }.build(player)

        collector.register()
    }

    @Subcommand("inv")
    fun onInv(player: Player) {
        val collector = InventoryCollectorBuilder
            .openInventoryCollectorBuilder(player)
            .onCollect {
                player.sendMessage("Collected ${it.type}")
            }.build(player)

        collector.register()
    }

    @Subcommand("location")
    fun onLocation(player: Player) {
        val collector = LocationCollectorBuilder {
            player.sendMessage("Click a location")
        }.onCollect {
            player.sendMessage("Collected ${it.blockX}, ${it.blockY}, ${it.blockZ}")
        }.build(player)

        collector.register()
    }

    @Subcommand("entity")
    fun onEntity(player: Player) {
        val collector = EntityCollectorBuilder {
            player.sendMessage("Click an entity")
        }.onCollect {
            player.sendMessage("Collected ${it.type}")
        }.onCancel {
            player.sendMessage("Cancelled")
        }.build(player)

        collector.register()
    }
}