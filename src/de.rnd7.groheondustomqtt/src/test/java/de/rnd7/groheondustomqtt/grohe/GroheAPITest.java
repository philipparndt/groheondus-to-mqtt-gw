package de.rnd7.groheondustomqtt.grohe;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GroheAPITest {
    @Test
    public void testLogin() throws Exception {
        final GroheAPI api = TestHelper.createAPI();
        final List<GroheDevice> devices = api.fetchDevices();
        assertFalse(devices.isEmpty());
    }
}
