package org.oyster.entity.rules;

import org.oyster.entity.ZoneTrip;

public interface FairCalculationRule {

    boolean calculate(ZoneTrip zoneTrip);
    Double getResult();
}
