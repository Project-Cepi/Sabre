package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer

interface Loader {

    fun load()

}

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