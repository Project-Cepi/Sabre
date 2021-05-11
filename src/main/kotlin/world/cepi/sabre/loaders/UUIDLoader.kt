package world.cepi.sabre.loaders

import world.cepi.kstom.Manager
import world.cepi.sabre.utils.getUUID

internal fun UUIDLoader() {

    // We have to set a different UUID provider because Mojang's API is not used by default
    Manager.connection.setUuidProvider { _, username ->
        return@setUuidProvider getUUID(username)
    }

}