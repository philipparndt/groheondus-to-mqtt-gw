package de.rnd7.groheondustomqtt.grohe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.grohe.ondus.api.OndusService;
import org.grohe.ondus.api.model.BaseAppliance;
import org.grohe.ondus.api.model.Room;
import org.json.JSONObject;

public class GroheAPI {

    private final OndusService service;
    private final GroheApplianceDataConverter converter;

    public GroheAPI(final String username, final String password) throws Exception {
        final JSONObject token = new GroheTokenLogin(username, password).login();
        this.service = OndusService.login(token.getString("refresh_token"));
        this.converter = new GroheApplianceDataConverter(this.service);
    }

    public List<GroheDevice> fetchDevices() throws IOException {
        final List<GroheDevice> result = new ArrayList<>();
        for (final BaseAppliance appliance : service.appliances()) {
            final Room room = appliance.getRoom();
            final String topic = String.format("%s/%s/%s", room.getLocation().getName(), room.getName(), appliance.getName());
            result.add(new GroheDevice(topic, this.converter.convert(appliance)));
        }

        return result;
    }
}
