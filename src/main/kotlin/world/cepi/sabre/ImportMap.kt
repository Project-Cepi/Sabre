package world.cepi.sabre

import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException
import kotlinx.coroutines.future.await
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.outputStream

@Serializable
data class ImportMap(val imports: List<Import> = listOf()) {

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

            if (validImports.isEmpty()) return

            logger.info("Starting download of non-existent import-map extensions")

            ioScope.launch {
                supervisorScope {

                    val deferredList = ArrayList<Deferred<*>>()

                    validImports.forEach {
                        deferredList.add(async {
                            try {
                                downloadURL(it.url, it.output)
                                logger.info("Downloaded ${it.output} jar...")
                            } catch (exception: Exception) {
                                logger.error("Failed to download ${it.output}", exception)
                            }
                        })
                    }

                    deferredList.joinAll()

                    logger.info("Finished downloading jars from import map!")
                }
            }
        }

        suspend fun downloadURL(url: String, output: String) {
            val client = HttpClient.newHttpClient()
            val request = HttpRequest.newBuilder().uri(URI.create(url)).build()
            val futureResponse = client.sendAsync(
                request,
                HttpResponse.BodyHandlers.ofInputStream()
            )

            futureResponse.await().body().copyTo(Path.of("./extensions/$output.jar").outputStream())
        }

    }

}
