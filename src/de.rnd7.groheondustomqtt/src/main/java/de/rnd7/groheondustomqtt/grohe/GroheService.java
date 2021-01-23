package de.rnd7.groheondustomqtt.grohe;

import de.rnd7.groheondustomqtt.config.ConfigGrohe;
import de.rnd7.mqttgateway.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GroheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroheService.class);

    private final ConfigGrohe config;
    private final GroheAPI groheAPI;

    public GroheService(final ConfigGrohe config) throws Exception {
        this.config = config;

        this.groheAPI = new GroheAPI(config.getUsername(), config.getPassword());
    }

    public GroheService start() {
        final long polling = config.getPollingInterval().getSeconds();

        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.scheduleWithFixedDelay(this::poll,
            polling,
            polling, TimeUnit.SECONDS);

        return this;
    }

    private void poll() {
        try {
            for (final GroheDevice device : this.groheAPI.fetchDevices()) {
                Events.post(device.toMessage());
            }
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
