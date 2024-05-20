package commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import com.azuyamat.mccollector.builders.ChatCollectorBuilder
import com.azuyamat.mccollector.builders.InventoryCollectorBuilder
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
}