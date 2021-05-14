package world.cepi.sabre.server.loaders

import com.google.common.cache.Cache
import world.cepi.kstom.Manager
import world.cepi.sabre.server.utils.getUUID
import com.google.common.cache.CacheBuilder
import java.util.*
import java.util.concurrent.TimeUnit


private val UUID_CACHE: Cache<String, UUID> = CacheBuilder.newBuilder()
    .expireAfterWrite(30, TimeUnit.SECONDS)
    .softValues()
    .build()

/** Sets the UUID provider to the Mojang authenticator. */
internal fun uuidLoader() = Manager.connection.setUuidProvider { _, username ->

    UUID_CACHE.getIfPresent(username)?.let { return@setUuidProvider it }

    val uuid = getUUID(username)

    uuid?.let { UUID_CACHE.put(username, it) }

    uuid
}