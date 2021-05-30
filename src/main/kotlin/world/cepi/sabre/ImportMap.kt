package world.cepi.sabre

import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException
import java.net.URL
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel
import java.io.FileOutputStream
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists

@Serializable
class ImportMap(val imports: List<Import> = listOf()) {

    @Serializable
    class Import(val url: String, val output: String) {
        val properFile: String
            get() = "./extensions/$output.jar"
    }

    companion object {

        val logger = LoggerFactory.getLogger(ImportMap::class.java)

        // Allows for custom import maps during boot.
        private var _importMap: ImportMap? = null

        var importMap: ImportMap
            get() = _importMap ?: run {
                throw IllegalArgumentException("Import map does not exist! Set it correctly or boot from a file.")
            }
            set(value) {
                _importMap = value
            }

        fun loadExtensions() {

            val extensionPath = Path.of("./extensions")

            if (!extensionPath.exists()) extensionPath.createDirectories()

            val validImports = importMap.imports.filter {
                !Path.of(it.properFile).exists()
            }

            if (validImports.isEmpty())
                return

            logger.info("Starting download of non-existant extensions")

            validImports.forEach {
                val readableByteChannel: ReadableByteChannel = Channels.newChannel(URL(it.url).openStream())

                val fileOutputStream = FileOutputStream("./extensions/${it.output}.jar")
                val fileChannel = fileOutputStream.channel

                logger.info("Downloading ${it.output} jar...")

                fileChannel
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

            }

            logger.info("Finished downloading jars from import map!")
        }

    }

}