package de.rnd7.groheondustomqtt.grohe;

import com.google.gson.Gson;
import de.rnd7.mqttgateway.PublishMessage;
import org.json.JSONObject;

public class GroheDevice {
    private static final Gson gson = new Gson();

    private final String topic;
    private final JSONObject json;

    public GroheDevice(final String topic, final JSONObject json) {
        this.topic = topic;
        this.json = json;
    }

    public PublishMessage toMessage() {
        return PublishMessage.relative(this.topic, gson.toJson(json));
    }

}
