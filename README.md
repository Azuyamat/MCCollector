# MCCollector 📜

Made with ❤️ by Azuyamat

Consider adding a star ⭐ if you find this project useful!

![GitHub license](https://img.shields.io/github/license/Azuyamat/MCCollector)
![GitHub Repo stars](https://img.shields.io/github/stars/Azuyamat/MCCollector?style=social)

## Details

MCCollector is a library which replicates the functionality [Discord.js message collectors](https://discordjs.guide/popular-topics/collectors.html#basic-message-collector) into Minecraft. It allows you to collect messages from players in a similar way to how you would with Discord.js.
Collectors are useful for creating secure and efficient prompts. They allow you to inquire information from players in a controlled manner.

Some examples of what you can do with collectors:
- Prompt player for username that respects a certain format
- Prompt certainty with a yes/no question before proceeding (I.e "Are you sure you want to delete this?")
- Prompt player for a number within a certain range
- Ask moderator for a reason before banning a player

![image](https://github.com/Azuyamat/MCCollector/assets/69324406/373cdd16-a7eb-4133-b11e-5f7ca59f3184)

## Installation

[![](https://jitpack.io/v/Azuyamat/MCCollector.svg)](https://jitpack.io/#Azuyamat/MCCollector)

### Gradle
```gradle
repositories {
    maven { url = "https://jitpack.io" }
}
```
```gradle
dependencies {
    implementation "com.github.Azuyamat:MCCollector:VERSION"
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.Azuyamat</groupId>
    <artifactId>MCCollector</artifactId>
    <version>VERSION</version>
</dependency>
```

## Features

- [x] Chat message collectors (I.e. player types a message)
- [X] Location collector (I.e. player clicks on a block)
- [X] Inventory collector (I.e. player clicks an item in their inventory)
- [X] Entity collector (I.e. player clicks on an entity)

> Let me know if you have any other ideas for collectors! :smile:

## Usage

> [!WARNING]
> If you are placing blocks in any of the callbacks, make sure to run them on the main thread.
> You can use `Bukkit.getScheduler().runTask(plugin, runnable)` to run code on the main thread.
> This limitation is not one of MCCollector, but rather Bukkit's API.

### Initialization

```kotlin
class MyPlugin : JavaPlugin() {
    override fun onEnable() {
        CollectorRegistry.init(this)
    }
}
```

### Basic Example

> [!NOTE]
> `onCommand` is used as an example, you would have you own method to handle commands.
```kotlin
fun onCommand(...) {
    val collector = ChatCollectorBuilder {
        player.sendMessage("Type 'hello' to continue.")
    }.applyPrefab(MessagesPrefab(player)) // Apply default messages (Ex: "Invalid input" in red)
    .buildAndRegister(player) // Build and register the collector to the player
}
```

### Advanced Example

```kotlin
fun onCommand(...) {
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
```

For more examples, check out the wiki. (Coming soon)

## Contributing

If this projects interests you, feel free to contribute! You can do so by forking the repository and submitting a pull request. If you have any questions or concerns, feel free to open an issue.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
