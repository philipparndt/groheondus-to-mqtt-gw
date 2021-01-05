package de.rnd7.groheondustomqtt.grohe;

import org.grohe.ondus.api.model.BaseApplianceData;
import org.json.JSONObject;


public interface ApplienceDataStrategy<T extends BaseApplianceData> {
    boolean canHandle(BaseApplianceData data);

    void handle(final BaseApplianceData data, final JSONObject result);

}
