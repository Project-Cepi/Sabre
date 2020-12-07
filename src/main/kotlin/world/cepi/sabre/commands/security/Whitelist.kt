package world.cepi.sabre.commands.security

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.sabre.Sabre
import world.cepi.sabre.utils.getUUID
import java.io.File
import java.util.*

class WhitelistCommand : Command("whitelist") {
    init {
        setDefaultExecutor {source, _ ->
            source.sendMessage("Usage: /whitelist <add|remove> <player>")
        }

        val remove = ArgumentType.Word("remove").from("remove")
        val add = ArgumentType.Word("add").from("add")
        val playerArg = ArgumentType.Word("player")

        addSyntax({ source, args ->

            val uuid = getUUID(args.getWord("player"))

            if (uuid == null) {
                source.sendMessage("That user does not exist!")
                return@addSyntax
            }

            if (uuid.isWhitelisted()) {
                source.sendMessage("${args.getWord("player")} is already on the whitelist.")
                return@addSyntax
            }

            Whitelist.add(uuid)
            source.sendMessage("Added ${args.getWord("player")} to the whitelist!")
        }, add, playerArg)

        addSyntax({ source, args ->
            val uuid = getUUID(args.getWord("player"))

            if (uuid == null) {
                source.sendMessage("That user does not exist!")
                return@addSyntax
            }

            if (!uuid.isWhitelisted()) {
                source.sendMessage("Player is not on the whitelist")
                return@addSyntax
            }

            Whitelist.remove(uuid)
            source.sendMessage("Removed ${args.getWord("player")} from the whitelist!")
        }, remove, playerArg)
    }
}

object Whitelist {
    private val whitelistFile = File(Sabre.WHITELIST_LOCATION)
    var whitelist: MutableList<UUID>

    val serilalizer: KSerializer<List<String>> = ListSerializer(String.serializer())

    init {
        whitelist = try {
            Json.decodeFromString(serilalizer, whitelistFile.readText()).map { UUID.fromString(it) }.toMutableList()
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    fun add(id: UUID) {
        whitelist.add(id)
        save()
    }

    fun remove(id: UUID) {
        whitelist.remove(id)
        save()
    }

    private fun save() {
        if (!whitelistFile.exists())
            whitelistFile.createNewFile()
        whitelistFile.writeText(Json.encodeToString(serilalizer, whitelist.map { it.toString() }.toList()))
    }
}

fun UUID.isWhitelisted(): Boolean = this in Whitelist.whitelist