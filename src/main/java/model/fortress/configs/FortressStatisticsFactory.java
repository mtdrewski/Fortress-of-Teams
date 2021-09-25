package model.fortress.configs;

import model.units.FortressStatistics;
import model.units.UnitTeam;

public interface FortressStatisticsFactory {
    FortressStatistics createFortressStatistics(UnitTeam team);
}
