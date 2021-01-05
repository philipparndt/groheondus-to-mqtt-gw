package de.rnd7.groheondustomqtt.grohe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.grohe.ondus.api.OndusService;
import org.grohe.ondus.api.model.BaseAppliance;
import org.grohe.ondus.api.model.Location;
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
            final String topic = String.format("grohe/%s/%s", appliance.getRoom(), appliance.getName());
            result.add(new GroheDevice(topic, this.converter.convert(appliance)));
        }

        return result;
    }
}
