package world.cepi.sabre.loaders

/** Loader interface for making it easier to reference the load function*/
interface Loader {

    /** Load function that calls in sequential order in the [Loaders] enum. */
    fun load() {

    }

}

/** Enum representation of all loaders, act independently from eachother.*/
enum class Loaders(val loader: Loader) {
    STORAGE(StorageLoader),
    MOJANG_AUTH(MojangAuthenticationLoader),
    UUID(UUIDLoader),
    INSTANCES(InstanceLoader),
    COMMANDS(CommandLoader),
    SAFE_SHUTDOWN(SafeShutdownLoader),
    VELOCITY(VelocityLoader),
    BUNGEE(BungeeLoader)
}


/** Loads all the loaders from the loader package. */
fun load() = Loaders.values().forEach { it.loader.load() }