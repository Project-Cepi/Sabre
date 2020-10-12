package world.cepi.sabre

import net.minestom.server.MinecraftServer
import net.minestom.server.chat.ColoredText
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.extras.MojangAuth
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import net.minestom.server.storage.systems.FileStorageSystem
import net.minestom.server.utils.Position
import world.cepi.sabre.Config.Companion.config
import world.cepi.sabre.commands.GamemodeCommand
import world.cepi.sabre.commands.KillCommand
import world.cepi.sabre.commands.StopCommand
import world.cepi.sabre.commands.TpCommand
import world.cepi.sabre.commands.security.*
import world.cepi.sabre.instances.Instances
import world.cepi.sabre.instances.generators.flat.Flat
import world.cepi.sabre.instances.generators.flat.FlatLayer
import world.cepi.sabre.loaders.load
import world.cepi.sabre.utils.getUUID


fun main() {
    val server = MinecraftServer.init()

    load()

    // The IP and port are currently grabbed from the config file
    server.start(config.ip, config.port)
}

object Sabre {
    const val CONFIG_LOCATION = "./sabre-config.json"
    const val INSTANCE_STORAGE_LOCATION = "./instances"
    const val WHITELIST_LOCATION = "./whitelist.json"
    const val OP_LOCATION = "./ops.json"
}