package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer
import kotlin.system.exitProcess

/** Array of all loaders, act independently from eachother.*/
val loaders: Array<() -> Unit> = arrayOf(
    ::storageLoader,
    ::mojangAuthenticationLoader,
    ::UUIDLoader,
    ::instanceLoader,
    ::commandLoader,
    ::proxyLoader,
    ::optifineLoader,
    ::blockPlacementLoader,
    ::thresholdLoader,
    ::viewLoader
)

/** Loads all the loaders from the loader package. */
fun loadLoaders() = loaders.forEach {
    try {
        it()
    } catch (e: Exception) {
        e.printStackTrace()
        MinecraftServer.LOGGER.error("Logger ${it.javaClass.simpleName} failed to load. Please file an issue on the Sabre github.")
        exitProcess(1)
    }
}
