package org.oyster.entity.rules.impl;

import org.oyster.entity.rules.FairCalculationRule;
import org.oyster.service.impl.CostCalculationServiceImpl;
import org.oyster.entity.ZoneTrip;

public class ThreeZonesRule implements FairCalculationRule {

    @Override
    public boolean calculate(ZoneTrip zoneTrip) {
        return zoneTrip.getDistance() >= 2;
    }

    @Override
    public Double getResult() {
        return CostCalculationServiceImpl.getMaxFare();
    }
}
