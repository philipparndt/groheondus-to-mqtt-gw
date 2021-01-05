package de.rnd7.groheondustomqtt.grohe;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.grohe.ondus.api.OndusService;
import org.grohe.ondus.api.model.ApplianceStatus;
import org.grohe.ondus.api.model.ApplianceStatus.ApplianceStatusModel;
import org.grohe.ondus.api.model.BaseAppliance;
import org.grohe.ondus.api.model.BaseApplianceData;
import org.json.JSONObject;

public class GroheApplianceDataConverter {
    private final OndusService service;

    private List<ApplienceDataStrategy> strategies = Arrays.asList(
            new SenseApplianceDataStrategy(),
            new GuardApplianceDataStrategy()
    );

    public GroheApplianceDataConverter(final OndusService service) {
        this.service = service;
    }

    public JSONObject convert(final BaseAppliance appliance) throws IOException {
        final JSONObject result = new JSONObject();

        result.put("serialnumber", appliance.getSerialNumber());

        this.service.applianceStatus(appliance)
                .ifPresent(status -> this.putStatus(status, result));

        this.service.applianceData(appliance, Instant.now().minus(Duration.ofHours(24)), Instant.now())
                .ifPresent(data -> putData(data, result));

        return result;
    }

    private void putData(final BaseApplianceData data, final JSONObject result) {
        for (final ApplienceDataStrategy strategy : strategies) {
            if (strategy.canHandle(data)) {
                strategy.handle(data, result);
            }
        }
    }

    private void putStatus(final ApplianceStatus status, final JSONObject result) {
        status.getStatuses().forEach(s -> result.put(s.getType(), this.convertValue(s)));
    }

    private Object convertValue(final ApplianceStatusModel s) {
        final String value = s.getValue();
        try {
            return Integer.parseInt(value);
        } catch (final NumberFormatException e) {
            return value;
        }
    }
}
