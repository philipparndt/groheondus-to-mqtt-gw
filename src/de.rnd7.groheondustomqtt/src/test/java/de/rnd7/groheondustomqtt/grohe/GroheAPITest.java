package de.rnd7.groheondustomqtt.grohe;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class GroheAPITest {
    @Test
    public void testLogin() throws Exception {
        final GroheAPI api = TestHelper.createAPI();
        final List<GroheDevice> devices = api.fetchDevices();
        assertFalse(devices.isEmpty());
    }
}
