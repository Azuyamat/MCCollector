import com.azuyamat.mccollector.builders.ChatCollectorBuilder
import com.azuyamat.mccollector.collectors.Verifiable
import org.bukkit.entity.Player
import org.mockito.Mockito.mock

@Suppress("UNUSED")
class CollectorExample {
    fun example() {
        val player = mock(Player::class.java)

        val collector = ChatCollectorBuilder {
            player.sendMessage("Provide a name for your island.")
        }.withTimeout(10000)
            .onCollect {
                player.sendMessage("Creating island: $it")
            }
            .onCancel {
                player.sendMessage("Island creation cancelled.")
            }
            .onTimeout {
                player.sendMessage("Island creation timed out.")
            }
            .verifyValue {
                if (it.length > 3) {
                    Verifiable.ValidationResult.valid()
                } else {
                    Verifiable.ValidationResult.invalid("Island name must be longer than 3 characters.")
                }
            }
            .build(player).register() // Or use .buildAndRegister(player)
    }
}