package world.cepi.sabre

import net.minestom.server.extensions.ExtensionManager
import net.minestom.server.extras.selfmodification.MinestomRootClassLoader
import net.minestom.server.extras.selfmodification.mixins.MixinCodeModifier
import net.minestom.server.extras.selfmodification.mixins.MixinServiceMinestom
import org.jetbrains.annotations.ApiStatus
import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.launch.platform.CommandLineOptions
import org.spongepowered.asm.mixin.Mixins
import org.spongepowered.asm.service.ServiceNotAvailableError
import world.cepi.sabre.server.Config
import world.cepi.sabre.server.Sabre
import java.lang.IllegalArgumentException
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.functions
import kotlin.reflect.full.staticFunctions
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.jvm.kotlinFunction

/**
 * Bootstrap wrapper for Minestom. Written in java to prevent Kotlin Bootstrap errors.
 */
object SabreLoader {
    private fun bootstrap(config: Config? = null, args: Array<String>) {
        // make the new class loader
        val classLoader = MinestomRootClassLoader.getInstance()

        // attempt to inject mixins into class loader.
        run {
            startMixin(args)
            try {
                MinestomRootClassLoader.getInstance().addCodeModifier(MixinCodeModifier())
            } catch (e: RuntimeException) {
                e.printStackTrace()
                System.err.println("Failed to add MixinCodeModifier, mixins will not be injected. Check the log entries above to debug.")
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

            main.javaMethod!!.invoke(null, null) // TODO actually add config
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
            e.printStackTrace()
            System.err.println("Failed to load Mixin, see error above.")
            System.err.println(
                "It is possible you simply have two files with identical names inside your server jar. " +
                        "Check your META-INF/services directory inside your Minestom implementation and merge files with identical names inside META-INF/services."
            )
            return
        }
        val doInit =
            MixinBootstrap::class.java.getDeclaredMethod("doInit", CommandLineOptions::class.java)
        doInit.isAccessible = true
        doInit.invoke(null, CommandLineOptions.ofArgs(listOf(*args)))
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
    fun boot(config: Config? = null, args: Array<String> = arrayOf()) {
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager")
        bootstrap(config, args)
    }

    @JvmStatic
    fun main(args: Array<String>) = boot(null, args)



}