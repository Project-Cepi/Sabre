package world.cepi.sabre

import net.minestom.server.extensions.ExtensionManager
import net.minestom.server.extras.selfmodification.MinestomRootClassLoader
import net.minestom.server.extras.selfmodification.mixins.MixinCodeModifier
import net.minestom.server.extras.selfmodification.mixins.MixinServiceMinestom
import org.slf4j.LoggerFactory
import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.launch.platform.CommandLineOptions
import org.spongepowered.asm.mixin.Mixins
import org.spongepowered.asm.service.ServiceNotAvailableError
import world.cepi.sabre.server.Config
import world.cepi.sabre.server.Sabre
import java.lang.IllegalArgumentException
import kotlin.reflect.full.functions
import kotlin.reflect.jvm.javaMethod

/**
 * Bootstrap wrapper for Minestom. Written in java to prevent Kotlin Bootstrap errors.
 */
object SabreLoader {

    val logger = LoggerFactory.getLogger(SabreLoader::class.java)

    // TODO use config property
    private fun bootstrap(config: Config? = null, importMap: ImportMap? = null, args: Array<String>) {

        // Initialize import map
        ImportMap.importMap = importMap ?: Sabre.initConfigFile(Sabre.IMPORT_PATH, ImportMap())
        ImportMap.loadExtensions()

        // make the new class loader
        val classLoader = MinestomRootClassLoader.getInstance()

        // attempt to inject mixins into class loader.
        run {
            startMixin(args)
            try {
                MinestomRootClassLoader.getInstance().addCodeModifier(MixinCodeModifier())
            } catch (e: RuntimeException) {
                logger.error("Failed to add MixinCodeModifier, mixins will not be injected.", e)
            }
            ExtensionManager.loadCodeModifiersEarly()
            MixinServiceMinestom.gotoPreinitPhase()
        }

        // ensure extensions are loaded when starting the server
        run {
            val serverClass = classLoader.loadClass("net.minestom.server.MinecraftServer")
            val init = serverClass.getMethod("init")
            init.invoke(null)

            MixinServiceMinestom.gotoInitPhase()
            MixinServiceMinestom.gotoDefaultPhase()
        }

        // call the main class
        run {
            val mainClass = classLoader.loadClass("world.cepi.sabre.server.Sabre").kotlin

            val main = mainClass.functions.firstOrNull {
                it.name == "boot"
            } ?: throw IllegalArgumentException("Main method not found. Report to Sabre.")

            main.javaMethod!!.invoke(null, null)
        }
    }

    private fun startMixin(args: Array<String>) {
        // hacks required to pass custom arguments
        val start = MixinBootstrap::class.java.getDeclaredMethod("start")
        start.isAccessible = true
        try {
            if (!(start.invoke(null) as Boolean)) {
                return
            }
        } catch (e: ServiceNotAvailableError) {
            logger.error("Failed to load Mixin", e)
            return
        }
        val doInit =
            MixinBootstrap::class.java.getDeclaredMethod("doInit", CommandLineOptions::class.java)
        doInit.isAccessible = true
        doInit.invoke(null, CommandLineOptions.ofArgs(args.toList()))
        MixinBootstrap.getPlatform().inject()
        Mixins.getConfigs().forEach { c ->
            MinestomRootClassLoader.getInstance().protectedPackages.add(
                c.config.mixinPackage
            )
        }
    }

    /**
     * Boots Sabre with the bootstrap loader.
     */
    fun boot(config: Config? = null, importMap: ImportMap? = null, args: Array<String> = arrayOf()) {
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager")
        bootstrap(config, importMap, args)
    }

    @JvmStatic
    fun main(args: Array<String>) = boot(null, null, args)



}
