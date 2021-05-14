package world.cepi.sabre.server.commands.security

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.ConsoleSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.default
import world.cepi.sabre.server.Config.Companion.config
import world.cepi.sabre.server.Sabre
import world.cepi.sabre.server.utils.getUUID
import java.io.File
import java.util.*

object OpCommand: Command("op") {
    init {
        default { sender, _ ->
            sender.sendMessage(Component.text("Usage: /op <player> <level>"))
        }

        val target = ArgumentType.Word("target")
        val level = ArgumentType.Integer("level")

        addSyntax(target) { sender, args ->
            val targetId = getUUID(args.get(target)) ?: return@addSyntax

            if (sender is Player && (sender.permissionLevel < 3 || sender.permissionLevel < config.opLevel)) {
                sender.sendMessage(Component.text("You don't have permission to add an op at the default level (${config.opLevel})", NamedTextColor.RED))
            } else {

                Operators.add(targetId, config.opLevel)
                sender.sendMessage(Component.text(args.get(target))
                    .hoverEvent(HoverEvent.showEntity(EntityType.PLAYER, targetId))
                    .append(Component.text(" was made a level ${config.opLevel} operator")))

            }
        }

        addSyntax(target, level) { source, args ->
            val targetLevel = args.get(level)
            val targetId = getUUID(args.get(target)) ?: return@addSyntax

            if (
                (source is Player && source.permissionLevel >= targetLevel && source.permissionLevel >= targetLevel)
                || source is ConsoleSender
            ) {

                Operators.add(targetId, targetLevel)
                source.sendMessage(Component.text(args.get(target))
                    .hoverEvent(HoverEvent.showEntity(EntityType.PLAYER, targetId))
                    .append(Component.text(" was made a level $targetLevel operator")))

            } else source.sendMessage(Component.text("You don't have permission to add an op at level $targetLevel!", NamedTextColor.RED))
        }
    }
}

object DeopCommand: Command("deop") {
    init {
        setDefaultExecutor { sender, _ -> sender.sendMessage(Component.text("Usage: /deop <player")) }

        val target = ArgumentType.Word("target")

        addSyntax(target) { source, args ->
            val targetId = getUUID(args.get(target)) ?: return@addSyntax
            val targetLevel = Operators.operators.getInt(targetId)

            if ((source is Player && source.permissionLevel >= targetLevel) || source is ConsoleSender) {
                Operators.remove(targetId)
                source.sendMessage(Component.text("Revoked ")
                    .append(Component.text(args.get(target)).hoverEvent(HoverEvent.showEntity(EntityType.PLAYER, targetId)))
                    .append(Component.text("s operator privileges")))
            } else source.sendMessage(Component.text("You don't have permission to revoke a level $targetLevel's privileges", NamedTextColor.RED))
        }
    }
}

object Operators {
    private val operatorFile = File(Sabre.OP_LOCATION)
    val operators = Object2IntOpenHashMap<UUID>()

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
 * Get a player's permission level from the OP file.
 *
 * @param player The target player for this function
 *
 * @return The player's permission level.
 */
internal fun getPermissionLevel(player: Player): Int = Operators.operators.getInt(player.uuid)