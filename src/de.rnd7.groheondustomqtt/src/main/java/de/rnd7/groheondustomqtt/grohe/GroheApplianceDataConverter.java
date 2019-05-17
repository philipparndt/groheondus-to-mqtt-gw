package de.rnd7.groheondustomqtt.grohe;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.grohe.ondus.api.OndusService;
import org.grohe.ondus.api.model.ApplianceStatus;
import org.grohe.ondus.api.model.BaseAppliance;
import org.grohe.ondus.api.model.SenseApplianceData;
import org.grohe.ondus.api.model.SenseApplianceData.Measurement;
import org.grohe.ondus.api.model.SenseGuardApplianceData;
import org.json.JSONObject;

public class GroheApplianceDataConverter {
	private final OndusService service;

	public GroheApplianceDataConverter(final OndusService service) {
		this.service = service;
	}

	public JSONObject convert(final BaseAppliance appliance) throws IOException {
		final JSONObject result = new JSONObject();

		result.put("serialnumber", appliance.getSerialNumber());
		this.service.getApplianceStatus(appliance).ifPresent(status -> this.putStatus(status, result));
		this.service.getApplianceData(appliance, Instant.now().minus(Duration.ofHours(24)), Instant.now()).ifPresent(data -> {
			if (data instanceof SenseApplianceData) {
				this.putSenseData((SenseApplianceData) data, result);
			} else if (data instanceof SenseGuardApplianceData) {
				this.putSenseGuardData((SenseGuardApplianceData) data, result);
			}
		});

		return result;
	}

	private void putSenseData(final SenseApplianceData data, final JSONObject result) {
		final List<Measurement> measurements = data.getData().getMeasurement();
		final Measurement measurement = measurements.get(measurements.size() - 1);

		result.put("humidity", measurement.getHumidity());
		result.put("temperature", measurement.getTemperature());
		result.put("timestamp", measurement.getTimestamp());
	}

	private void putSenseGuardData(final SenseGuardApplianceData data, final JSONObject result) {
		final List<SenseGuardApplianceData.Measurement> measurements = data.getData().getMeasurement();
		final SenseGuardApplianceData.Measurement measurement = measurements.get(measurements.size() - 1);

		result.put("flowrate", measurement.getFlowrate());
		result.put("pressure", measurement.getPressure());
		result.put("temperature", measurement.getTemperatureGuard());
		result.put("timestamp", measurement.getTimestamp());
	}

	private void putStatus(final ApplianceStatus status, final JSONObject result) {
		status.getStatuses().forEach(s -> result.put(s.getType(), s.getValue()));
	}
}
