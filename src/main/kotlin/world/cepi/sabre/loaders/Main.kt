package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandManager

/** Loads all the loaders from the loader package. */
fun load() {
    val connectionManager = MinecraftServer.getConnectionManager()

    StorageLoader.load()
    MojangAuthenticationLoader.load()
    UUIDLoader.load(connectionManager)
    InstanceLoader.load(connectionManager)
    CommandLoader.load()
    SafeShutdownLoader.load(connectionManager)
}