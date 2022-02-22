package world.cepi.sabre.server.loaders

import net.minestom.server.extras.lan.OpenToLAN
import net.minestom.server.extras.lan.OpenToLANConfig
import net.minestom.server.utils.time.TimeUnit
import world.cepi.sabre.server.Config.Companion.config
import java.time.Duration

internal fun lanLoader() {
    if (config.lan) {
        OpenToLAN.open(
            OpenToLANConfig()
                .pingDelay(Duration.of((config.lanPingDelay * 1000).toLong(), TimeUnit.MILLISECOND))
                .eventCallDelay(Duration.of((config.lanEventDelay * 1000).toLong(), TimeUnit.MILLISECOND))
        )
    }
}