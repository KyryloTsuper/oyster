package org.oyster.entity.rules;

import org.oyster.entity.ZoneTrip;

public interface FairCalculationRule {

    boolean check(ZoneTrip zoneTrip);
    Double getResult();
}
