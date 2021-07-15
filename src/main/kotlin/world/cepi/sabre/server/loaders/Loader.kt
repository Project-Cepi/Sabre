package world.cepi.sabre.server.loaders

import net.minestom.server.MinecraftServer
import kotlin.system.exitProcess

/** Array of all loaders, act independently from eachother.*/
internal val loaders: Array<() -> Unit> = arrayOf(
    ::mojangAuthenticationLoader,
    ::instanceLoader,
    ::commandLoader,
    ::proxyLoader,
    ::optifineLoader,
    ::blockPlacementLoader,
    ::thresholdLoader,
    ::viewLoader
)

/** Loads all the loaders from the loader package. */
internal fun loadLoaders() = loaders.forEach {
    try {
        it()
    } catch (e: Exception) {
        e.printStackTrace()
        MinecraftServer.LOGGER.error("Logger ${it.javaClass.simpleName} failed to load. Please file an issue on the Sabre github.")
        exitProcess(1)
    }
}
