package org.oyster.entity.rules.impl;

import org.oyster.entity.rules.FairCalculationRule;
import org.oyster.service.impl.CostCalculationServiceImpl;
import org.oyster.entity.ZoneTrip;

public class TwoZonesWithoutZoneOneRule implements FairCalculationRule {

    @Override
    public boolean calculate(ZoneTrip zoneTrip) {
        return !zoneTrip.hasZone(1) && (zoneTrip.getDistance() == 1);
    }

    @Override
    public Double getResult() {
        return CostCalculationServiceImpl.getTwoZonesExcludingZoneOne();
    }
}
