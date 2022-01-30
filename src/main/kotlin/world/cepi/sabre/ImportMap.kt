package world.cepi.sabre

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException
import java.io.FileNotFoundException
import java.net.URI
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.writeBytes

@Serializable
data class ImportMap(val imports: List<Import> = listOf()) {

    @Serializable
    class Import(val url: String, val output: String = Paths.get(URI(url).path).fileName.toString()) {
        val properFile: String
            get() = "./extensions/$output.jar"
    }

    companion object {

        val ioScope = CoroutineScope(Dispatchers.IO + Job())

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

        suspend fun loadExtensions() {

            val extensionPath = Path.of("./extensions")

            if (!extensionPath.exists()) extensionPath.createDirectories()

            val validImports = importMap.imports.filter {
                !Path.of(it.properFile).exists()
            }

            if (validImports.isEmpty()) return

            logger.info("Starting download of non-existent import-map extensions")

            supervisorScope {

                val deferredList = mutableListOf<Deferred<*>>()

                validImports.forEach {
                    deferredList.add(async {
                        try {
                            logger.info("Downloading ${it.output}...")
                            downloadURL(HttpClient(CIO), it.url, it.output)
                            logger.info("Downloaded ${it.output}!")
                        } catch (exception: FileNotFoundException) {
                            logger.error("No file found at url ${exception.message!!}")
                        } catch (exception: Exception) {
                            logger.error("Failed to download ${it.output}", exception)
                        }
                    })
                }

                deferredList.joinAll()

                logger.info("Finished downloading jars from import map!")
            }

        }

        suspend fun downloadURL(client: HttpClient, url: String, output: String) {
            val response: HttpResponse = client.get(url) {
                method = HttpMethod.Get
            }

            if (response.status == HttpStatusCode.NotFound) {
                throw FileNotFoundException(url)
            }

            val responseBody: ByteArray = response.receive()
            Path.of("./extensions/$output").writeBytes(responseBody)
        }

    }

}
