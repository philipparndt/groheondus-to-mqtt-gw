package de.rnd7.groheondustomqtt;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import org.grohe.ondus.api.OndusService;
import org.grohe.ondus.api.model.ApplianceStatus.ApplianceStatusModel;
import org.grohe.ondus.api.model.BaseAppliance;
import org.grohe.ondus.api.model.Location;
import org.grohe.ondus.api.model.Room;
import org.grohe.ondus.api.model.SenseApplianceData;
import org.grohe.ondus.api.model.SenseApplianceData.Measurement;
import org.grohe.ondus.api.model.SenseGuardApplianceData;
import org.json.JSONObject;

import com.beust.jcommander.JCommander;

import de.rnd7.groheondustomqtt.grohe.GroheTokenLogin;

public class Main {

	public Main(final Args args) {
		if (args.isFetchtoken()) {
			Objects.requireNonNull("Username", args.getUsername());
			Objects.requireNonNull("Password", args.getPassword());

			this.fetchToken(args);

		}
	}

	private void fetchToken(final Args args) {
		try {
			final JSONObject token = new GroheTokenLogin(args.getUsername(), args.getPassword()).login();
			System.out.println(token);

			final OndusService service = OndusService.login(token.getString("refresh_token"));
			for (final Location location : service.getLocations()) {
				System.out.println("Location: " + location.getName());
				for (final Room room : service.getRooms(location)) {
					System.out.println("Room: " + room.getName());
					for (final BaseAppliance appliance : service.getAppliances(room)) {
						System.out.println("appliance: " + appliance.getName() + " " + appliance.getSerialNumber());

						service.getApplianceStatus(appliance).ifPresent(status -> {
							System.out.println(status);
							final List<ApplianceStatusModel> statuses = status.getStatuses();
							for (final ApplianceStatusModel model : statuses) {
								System.out.println(model.getType() + ": " + model.getValue());
							}
						});

						service.getApplianceData(appliance, Instant.now().minus(Duration.ofHours(24)), Instant.now()).ifPresent(data -> {
							System.out.println(data);

							if (data instanceof SenseApplianceData) {
								final SenseApplianceData senseApplianceData = (SenseApplianceData) data;
								final List<Measurement> measurements = senseApplianceData.getData().getMeasurement();
								final Measurement measurement = measurements.get(measurements.size() - 1);

								System.out.println(measurement.getHumidity());
								System.out.println(measurement.getTemperature());
								System.out.println(measurement.getTimestamp());
							} else if (data instanceof SenseGuardApplianceData) {

							}

						});

					}
				}
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(final String[] argv) {
		final Args args = new Args();

		JCommander.newBuilder().addObject(args).build().parse(argv);

		new Main(args);
	}
}
