package world.cepi.sabre.server.loaders

import org.slf4j.LoggerFactory
import kotlin.system.exitProcess

private val logger = LoggerFactory.getLogger("SabreLoaders")

/** Array of all loaders, act independently of each other.*/
internal val loaders: Array<() -> Unit> = arrayOf(
    ::mojangAuthenticationLoader,
    ::instanceLoader,
    ::commandLoader,
    ::proxyLoader,
    ::optifineLoader,
    ::blockPlacementLoader,
    ::thresholdLoader,
    ::viewLoader,
    ::lanLoader
)

/** Loads all the loaders from the loader package. */
internal fun loadLoaders() = loaders.forEach {
    try {
        it()
    } catch (e: Exception) {
        logger.error("Logger ${it.javaClass.simpleName} failed to load. Please file an issue on the Sabre github.", e)
        exitProcess(1)
    }
}
