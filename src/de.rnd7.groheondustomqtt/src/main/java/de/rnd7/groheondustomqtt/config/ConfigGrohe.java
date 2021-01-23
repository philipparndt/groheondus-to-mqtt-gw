package de.rnd7.groheondustomqtt.config;

import com.google.gson.annotations.SerializedName;

import java.time.Duration;

public class ConfigGrohe {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("polling-interval")
    private Duration pollingInterval;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Duration getPollingInterval() {
        return pollingInterval;
    }
}
