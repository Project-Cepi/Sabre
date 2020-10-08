package world.cepi.sabre.utils

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import org.json.JSONArray
import world.cepi.sabre.Sabre
import java.io.File
import java.io.FileWriter
import java.util.*

object WhitelistCommand : Command("whitelist") {
    init {
        setDefaultExecutor {source, _ ->
            source.sendMessage("Usage: /whitelist <add|remove> <player>")
        }

        val mode = ArgumentType.Word("mode").from("add", "remove")
        val playerArg = ArgumentType.Word("player")

        addSyntax({ source, args ->
            if (args.getWord("mode") == "add") {
                if (getUUID(args.getWord("player")) in Whitelist.whitelist) source.sendMessage("${args.getWord("player")} is already on the whitelist.")
            }
            val uuid = getUUID(args.getWord("player"))
            Whitelist.add(uuid)
        }, mode, playerArg)

        addSyntax({source, args ->
            val uuid = getUUID(args.getWord("player"))
            if (uuid !in Whitelist.whitelist) source.sendMessage("Player is not on the whitelist")

            Whitelist.remove(uuid)
        }, playerArg)
    }
}

object Whitelist {
    val whitelistFile = File(Sabre.CONFIG_LOCATION)
    var whitelist: JSONArray
    init {
        if (whitelistFile.exists()) whitelist = JSONArray(whitelistFile.readText()) else whitelist = JSONArray()
    }
    fun add(id: UUID) {
        whitelist.put(id)
        whitelist.write(FileWriter(whitelistFile))
    }

    fun remove(id: UUID) {
        TODO("Need a way to remove from JSONArray")
    }
    fun isWhitelisted(player: Player): Boolean = player.uuid in whitelist
}