package de.rnd7.groheondustomqtt.grohe;

import org.json.JSONObject;

import de.rnd7.groheondustomqtt.mqtt.Message;

public class GroheDevice {

    private final String topic;
    private final JSONObject json;

    public GroheDevice(final String topic, final JSONObject json) {
        this.topic = topic;
        this.json = json;
    }

    public Message toMessage() {
        return new Message(this.topic, this.json);
    }

}
