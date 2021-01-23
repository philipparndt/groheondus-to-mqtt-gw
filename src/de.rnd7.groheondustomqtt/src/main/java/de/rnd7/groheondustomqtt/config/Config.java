package de.rnd7.groheondustomqtt.config;

import de.rnd7.mqttgateway.config.ConfigMqtt;

public class Config {
    private ConfigMqtt mqtt;
    private ConfigGrohe grohe;

    public ConfigMqtt getMqtt() {
        return mqtt;
    }

    public ConfigGrohe getGrohe() {
        return grohe;
    }
}
