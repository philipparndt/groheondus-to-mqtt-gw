package de.rnd7.groheondustomqtt.grohe;

import org.grohe.ondus.api.model.BaseApplianceData;
import org.grohe.ondus.api.model.guard.ApplianceData;
import org.json.JSONObject;

import java.util.List;

public class GuardApplianceDataStrategy implements ApplienceDataStrategy<ApplianceData> {
    @Override
    public boolean canHandle(final BaseApplianceData data) {
        return data instanceof ApplianceData;
    }

    @Override
    public void handle(final BaseApplianceData data, final JSONObject result) {
        final List<ApplianceData.Measurement> measurements = ((ApplianceData) data).getData().getMeasurement();
        final ApplianceData.Measurement measurement = measurements.get(measurements.size() - 1);

        result.put("flowrate", measurement.getFlowrate());
        result.put("pressure", measurement.getPressure());
        result.put("temperature", measurement.getTemperatureGuard());
        result.put("timestamp", measurement.getTimestamp());
    }
}
