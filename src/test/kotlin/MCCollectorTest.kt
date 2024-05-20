import co.aikar.commands.PaperCommandManager
import com.azuyamat.mccollector.CollectorRegistry
import commands.TestCommand
import org.bukkit.plugin.java.JavaPlugin

class MCCollectorTest : JavaPlugin() {
    override fun onEnable() {
        val commandManager = PaperCommandManager(this)
        CollectorRegistry.init(this)

        commandManager.registerCommand(TestCommand())
    }
}