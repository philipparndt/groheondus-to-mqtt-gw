package de.rnd7.groheondustomqtt.grohe;

import org.grohe.ondus.api.model.BaseApplianceData;
import org.grohe.ondus.api.model.sense.ApplianceData;
import org.json.JSONObject;

import java.util.List;

public class SenseApplianceDataStrategy implements ApplienceDataStrategy<ApplianceData> {
    @Override
    public boolean canHandle(final BaseApplianceData data) {
        return data instanceof ApplianceData;
    }

    @Override
    public void handle(final BaseApplianceData data, final JSONObject result) {
        final List<ApplianceData.Measurement> measurements = ((ApplianceData) data).getData().getMeasurement();
        final org.grohe.ondus.api.model.sense.ApplianceData.Measurement measurement = measurements.get(measurements.size() - 1);

        result.put("humidity", measurement.getHumidity());
        result.put("temperature", measurement.getTemperature());
        result.put("timestamp", measurement.getTimestamp());
    }
}
