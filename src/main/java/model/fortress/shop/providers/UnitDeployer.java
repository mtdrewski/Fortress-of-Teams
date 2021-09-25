package model.fortress.shop.providers;

import model.path.FortressPath;
import model.units.ArmyUnit;
import model.units.UnitStatistics;
import model.units.UnitTeam;

import java.util.function.Function;

public class UnitDeployer implements Deployer {
    private final FortressPath path;
    private final UnitTeam team;
    private final Function<UnitStatistics, ArmyUnit> unitProducer;
    public UnitDeployer(FortressPath path, UnitTeam team, Function<UnitStatistics, ArmyUnit> unitProducer) {
        this.path = path;
        this.team = team;
        this.unitProducer = unitProducer;
    }

    @Override
    public void deployUnitsWithStatistics(Function<UnitTeam, UnitStatistics> statsMaker) {
        path.addUnit(unitProducer.apply(statsMaker.apply(team)));
    }
}
