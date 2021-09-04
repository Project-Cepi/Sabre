package world.cepi.sabre.server.commands

import com.google.gson.JsonParser
import net.minestom.server.command.builder.Command
import java.net.URL
import kotlin.concurrent.thread
import kotlin.io.path.outputStream
import kotlin.io.path.toPath

object UpdateCommand : Command("updateserver") {

    val sabreInfoURL = URL("https://raw.githubusercontent.com/Project-Cepi/Sabre/master/minestom.json")

    init {
        setCondition { sender, _ ->
            sender.hasPermission("server.update") || sender.isConsole
        }

        setDefaultExecutor { sender, _ ->

            thread {
                try {
                    URL(
                        JsonParser.parseString(sabreInfoURL.readText())
                            .asJsonObject.get("download").asString
                    ).openStream()
                        .copyTo(this::class.java.protectionDomain.codeSource.location.toURI().toPath().outputStream())

                    sender.sendMessage("Download successful!")
                } catch (exception: Exception) {
                    exception.printStackTrace()
                    sender.sendMessage("Download failed. Check console for more info.")
                }
            }
        }
    }

}