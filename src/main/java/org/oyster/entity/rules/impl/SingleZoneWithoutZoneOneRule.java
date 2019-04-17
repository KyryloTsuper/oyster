package org.oyster.entity.rules.impl;

import org.oyster.entity.rules.FairCalculationRule;
import org.oyster.service.impl.CostCalculationServiceImpl;
import org.oyster.entity.ZoneTrip;

public class SingleZoneWithoutZoneOneRule implements FairCalculationRule {

    @Override
    public boolean check(ZoneTrip zoneTrip) {
        return !zoneTrip.hasZone(1) && (zoneTrip.getDistance() == 0);
    }

    @Override
    public Double getResult() {
        return CostCalculationServiceImpl.getSingleZoneOutsideZoneOne();
    }
}
