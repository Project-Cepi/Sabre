package world.cepi.sabre.loaders

import net.minestom.server.MinecraftServer

/** Loader interface for making it easier to reference the load function */
interface Loader {

    /** Load function that calls in sequential order in the [Loaders] enum. */
    operator fun invoke()
}

/** Array of all loaders, act independently from eachother.*/
val loaders: Array<Loader> = arrayOf(
    StorageLoader,
    MojangAuthenticationLoader,
    UUIDLoader,
    InstanceLoader,
    CommandLoader,
    VelocityLoader,
    BungeeLoader,
    OptifineLoader,
    BlockPlacementLoader,
    ThresholdLoader,
    ViewLoader
)

/** Loads all the loaders from the loader package. */
fun load() {
    loaders.forEach {
        try {
            it()
        } catch (e: Exception) {
            e.printStackTrace()
            MinecraftServer.LOGGER.warn("Logger ${it.javaClass.simpleName} failed to load.")
        }
    }
}