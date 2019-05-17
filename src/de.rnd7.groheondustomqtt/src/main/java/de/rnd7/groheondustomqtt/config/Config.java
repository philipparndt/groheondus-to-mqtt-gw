package de.rnd7.groheondustomqtt.config;

import java.time.Duration;

public class Config {
	private String mqttBroker;

	private Duration pollingInterval;

	private String groheUsername;
	private String grohePassword;

	public void setMqttBroker(final String mqttBroker) {
		this.mqttBroker = mqttBroker;
	}

	public String getMqttBroker() {
		return this.mqttBroker;
	}

	public void setPollingInterval(final Duration pollingInterval) {
		this.pollingInterval = pollingInterval;
	}

	public Duration getPollingInterval() {
		return this.pollingInterval;
	}

	public void setGroheUsername(final String groheUsername) {
		this.groheUsername = groheUsername;
	}

	public String getGroheUsername() {
		return this.groheUsername;
	}

	public void setGrohePassword(final String grohePassword) {
		this.grohePassword = grohePassword;
	}

	public String getGrohePassword() {
		return this.grohePassword;
	}
}
