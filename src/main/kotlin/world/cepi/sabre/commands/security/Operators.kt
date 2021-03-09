package world.cepi.sabre.commands.security

import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import net.minestom.server.command.ConsoleSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.kstom.command.addSyntax
import world.cepi.sabre.Config.Companion.config
import world.cepi.sabre.Sabre
import world.cepi.sabre.utils.getUUID
import java.io.File
import java.util.*

object OpCommand: Command("op") {
    init {
        setDefaultExecutor { sender, _ ->
            sender.sendMessage("Usage: /op <player> <level>")
        }

        val target = ArgumentType.Word("target")
        val level = ArgumentType.Integer("level")

        addSyntax(target) { sender, args ->
            val targetId = getUUID(args.get(target)) ?: return@addSyntax

            if (sender is Player) {
                if (sender.permissionLevel >= 3 && sender.permissionLevel >= config.opLevel) {
                    Operators.add(targetId, config.opLevel)
                    sender.sendMessage("${args.get(target)} was made a level ${config.opLevel} operator")
                } else {
                    sender.sendMessage("You don't have permission to add an op at the default level (${config.opLevel})")
                }
            } else Operators.add(targetId, config.opLevel)
        }

        addSyntax(target, level) { source, args ->
            val targetLevel = args.get(level)
            val targetId = getUUID(args.get(target)) ?: return@addSyntax

            if ((source is Player && source.permissionLevel >= targetLevel && source.permissionLevel >= targetLevel) || source is ConsoleSender) {
                Operators.add(targetId, targetLevel)
                source.sendMessage("${args.get(target)} was made a level $targetLevel operator")
            } else source.sendMessage("You don't have permission to add an op at level $targetLevel")
        }
    }
}

object DeopCommand: Command("deop") {
    init {
        setDefaultExecutor { sender, _ -> sender.sendMessage("Usage: /deop <player") }

        val target = ArgumentType.Word("target")

        addSyntax(target) { source, args ->
            val targetId = getUUID(args.get(target)) ?: return@addSyntax
            val targetLevel = Operators.operators.getInt(targetId)

            if ((source is Player && source.permissionLevel >= targetLevel) || source is ConsoleSender) {
                Operators.remove(targetId)
                source.sendMessage("Revoked ${args.get(target)}'s operator privileges")
            } else source.sendMessage("You don't have permission to revoke a level $targetLevel's privileges")
        }
    }
}

object Operators {
    private val operatorFile = File(Sabre.OP_LOCATION)
    val operators: Object2IntMap<UUID> = Object2IntOpenHashMap()

    private val serilalizer = MapSerializer(String.serializer(), Int.serializer())

    init {
        try {
            operators.putAll(Json.decodeFromString(serilalizer, operatorFile.readText()).mapKeys { UUID.fromString(it.key) })
        } catch (e: Exception) {

        }
    }

    fun add(id: UUID, opLevel: Int) {
        operators[id] = opLevel
        save()
    }

    fun remove(id: UUID) {
        operators.removeInt(id)
        save()
    }

    private fun save() {
        if (!operatorFile.exists())
            operatorFile.createNewFile()
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
internal fun getPermissionLevel(player: Player): Int = Operators.operators.getInt(player.uuid)