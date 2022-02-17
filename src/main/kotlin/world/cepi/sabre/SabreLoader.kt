package world.cepi.sabre

import org.fusesource.jansi.AnsiConsole
import world.cepi.sabre.server.Config
import world.cepi.sabre.server.Sabre
object SabreLoader {

    fun boot(config: Config?) {
        AnsiConsole.systemInstall()
		Sabre.boot(config)
	}

    @JvmStatic
    fun main(args: Array<String>) = boot(null)



}
