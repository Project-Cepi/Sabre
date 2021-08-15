package world.cepi.sabre

import kotlinx.coroutines.*
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

        private val ioScope = CoroutineScope(Dispatchers.IO + Job())

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

            logger.info("Starting download of non-existent import-map extensions")

            ioScope.launch {
                supervisorScope {

                    val deferredList = ArrayList<Deferred<*>>()

                    validImports.forEach {
                        deferredList.add(async {
                            try {
                                URL(it.url).openStream().copyTo(FileOutputStream("./extensions/${it.output}.jar"))
                                logger.info("Downloaded ${it.output} jar...")
                            } catch(exception: Exception) {
                                logger.info("Failed to download ${it.output}")
                            }
                        })
                    }

                    deferredList.joinAll()

                    logger.info("Finished downloading jars from import map!")
                }
            }
        }

    }

}