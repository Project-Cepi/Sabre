package world.cepi.sabre

import world.cepi.sabre.server.Config
import world.cepi.sabre.server.Sabre
object SabreLoader {

    fun boot(config: Config?) = Sabre.boot(config)

    @JvmStatic
    fun main(args: Array<String>) = Sabre.boot(null)



}
