package model.fortress.shop.providers;

import model.units.UnitStatistics;
import model.units.UnitTeam;

import java.util.function.Function;

public interface Deployer {
    void deployUnitsWithStatistics(Function<UnitTeam, UnitStatistics> statsMaker);
}
