package world.cepi.sabre.loaders

import com.google.common.cache.Cache
import world.cepi.kstom.Manager
import world.cepi.sabre.utils.getUUID
import com.google.common.cache.CacheBuilder
import java.util.*
import java.util.concurrent.TimeUnit


private val UUID_CACHE: Cache<String, UUID> = CacheBuilder.newBuilder()
    .expireAfterWrite(30, TimeUnit.SECONDS)
    .softValues()
    .build()

/** Sets the UUID provider to the Mojang authenticator. */
internal fun uuidLoader() = Manager.connection.setUuidProvider { _, username ->
    return@setUuidProvider getUUID(username)
}