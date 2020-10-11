package world.cepi.sabre.commands.security

import net.minestom.server.command.CommandSender
import net.minestom.server.command.ConsoleSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import org.json.JSONObject
import world.cepi.sabre.Config
import world.cepi.sabre.Sabre
import world.cepi.sabre.utils.getUUID
import java.io.File
import java.io.FileWriter
import java.util.*

class OpCommand: Command("op") {
    init {
        val config = Config()

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

        addSyntax({source, args ->
            val targetLevel = args.getInteger("level")
            val targetId = getUUID(args.getWord("target")) ?: return@addSyntax

            if ((source is Player && source.permissionLevel >= targetLevel) || source is ConsoleSender) {
                Operators.add(targetId, targetLevel)
                source.sendMessage("${args.getWord("target")} was made a level $level operator")
            } else source?.sendMessage("You don't have permission to add an op at level $targetLevel")
        }, target, level)
    }
}

object DeopCommand: Command("deop") {
    init {
        setDefaultExecutor{sender, _ -> sender.sendMessage("Usage: /deop <player") }

        addSyntax({source, args ->
            val targetId = getUUID(args.getWord("target")) ?: return@addSyntax
            val targetLevel = Operators.ops[targetId.toString()] as Int

            if ((source is Player && source.permissionLevel >= targetLevel) || source is CommandSender) {
                Operators.remove(targetId)
                source.sendMessage("Revoked ${args.getWord("target")}'s operator privileges")
            } else source.sendMessage("You don't have permission to revoke a level $targetLevel's privileges")
        })
    }
}

object Operators {
    private val opFile = File(Sabre.OP_LOCATION)
    val ops = if (opFile.exists()) JSONObject(opFile) else JSONObject()

    fun add(id: UUID, level: Int) {
        ops.put(id.toString(), level)
        ops.write(FileWriter(Sabre.CONFIG_LOCATION))
    }

    fun remove(id: UUID) {
        ops.remove(id.toString())
        ops.write(FileWriter(Sabre.CONFIG_LOCATION))
    }
}

/**
 * Check if a player is in the OP list.
 *
 * @param player The target player to check if they are OP
 *
 * @return If the player is considered an operator
 */
fun isOp(player: Player) = Operators.ops.has(player.uuid.toString())

/**
 * Get a player's permission level.
 *
 * @param player The target player for this function
 *
 * @return The player's permission level.
 */
fun getPermissionLevel(player: Player): Int? = Operators.ops.get(player.uuid.toString()) as Int