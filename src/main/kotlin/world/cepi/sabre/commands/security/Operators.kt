package world.cepi.sabre.commands.security

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import net.minestom.server.command.CommandSender
import net.minestom.server.command.ConsoleSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import org.json.JSONObject
import world.cepi.sabre.Config.Companion.config
import world.cepi.sabre.Sabre
import world.cepi.sabre.utils.getUUID
import java.io.File
import java.io.FileWriter
import java.util.*

class OpCommand: Command("op") {
    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /op <player> <level>")
        }

        val target = ArgumentType.Word("target")
        val level = ArgumentType.Integer("level")

        addSyntax({ sender, args ->
            val targetId = getUUID(args.getWord("target")) ?: return@addSyntax

            if (sender is Player) {
                if (sender.permissionLevel >= 3 && sender.permissionLevel >= config.opLevel) {
                    Operators.add(targetId, config.opLevel)
                    sender.sendMessage("${args.getWord("target")} was made a level ${config.opLevel} operator")
                } else {
                    sender.sendMessage("You don't have permission to add an op at the default level (${config.opLevel})")
                }
            } else Operators.add(targetId, config.opLevel)
        }, target)

        addSyntax({ source, args ->
            val targetLevel = args.getInteger("level")
            val targetId = getUUID(args.getWord("target")) ?: return@addSyntax

            if ((source is Player && source.permissionLevel >= targetLevel) || source is ConsoleSender) {
                Operators.add(targetId, targetLevel)
                source.sendMessage("${args.getWord("target")} was made a level $level operator")
            } else source.sendMessage("You don't have permission to add an op at level $targetLevel")
        }, target, level)
    }
}

class DeopCommand: Command("deop") {
    init {
        setDefaultExecutor{ sender, _ -> sender.sendMessage("Usage: /deop <player") }

        val target = ArgumentType.Word("target")

        addSyntax({ source, args ->
            val targetId = getUUID(args.getWord("target")) ?: return@addSyntax
            val targetLevel = Operators.operators[targetId] as Int

            if ((source is Player && source.permissionLevel >= targetLevel) || source is ConsoleSender) {
                Operators.remove(targetId)
                source.sendMessage("Revoked ${args.getWord("target")}'s operator privileges")
            } else source.sendMessage("You don't have permission to revoke a level $targetLevel's privileges")
        }, target)
    }
}

object Operators {
    private val operatorFile = File(Sabre.OP_LOCATION)
    var operators: MutableMap<UUID, Int>

    val serilalizer: KSerializer<Map<String, Int>> = MapSerializer(String.serializer(), Int.serializer())

    init {
        operators = try {
            Json.decodeFromString(serilalizer, operatorFile.readText()).mapKeys { UUID.fromString(it.key) }.toMutableMap()
        } catch (e: Exception) {
            mutableMapOf()
        }
    }

    fun add(id: UUID, opLevel: Int) {
        operators[id] = opLevel
        save()
    }

    fun remove(id: UUID) {
        operators.remove(id)
        save()
    }

    private fun save() {
        if (!operatorFile.exists())
            operatorFile.createNewFile()
        val operators1 = operators
        operatorFile.writeText(Json.encodeToString(serilalizer, operators.mapKeys { it.key.toString() } ))
    }
}

/**
 * Get a player's permission level.
 *
 * @param player The target player for this function
 *
 * @return The player's permission level.
 */
fun getPermissionLevel(player: Player): Int? = Operators.operators[player.uuid] as Int