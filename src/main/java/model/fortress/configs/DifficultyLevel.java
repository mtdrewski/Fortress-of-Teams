package model.fortress.configs;

import clock.Clock;
import model.fortress.Configs;
import model.fortress.Fortress;
import model.path.FortressPath;
import model.path.FortressPathImpl;
import model.shop.Money;
import model.units.ArmyUnit;
import model.units.ArmyUnitImpl;
import model.units.FortressStatistics;
import model.units.UnitTeam;

import java.util.function.BiConsumer;

public enum DifficultyLevel {
    EASY(
            new ConfigsProvider(
                    FortressActionsShopProvider.EASY,
                    FortressUpgradesShopProvider.EASY,
                    UnitShopProvider.EASY,
                    1000, 1000),
            new ConfigsProvider(
                    FortressActionsShopProvider.HARD,
                    FortressUpgradesShopProvider.HARD,
                    UnitShopProvider.HARD,
                    500, 750)
    ),
    HARD(
            new ConfigsProvider(
                    FortressActionsShopProvider.HARD,
                    FortressUpgradesShopProvider.HARD,
                    UnitShopProvider.HARD,
                    500, 750),
            new ConfigsProvider(
                    FortressActionsShopProvider.EASY,
                    FortressUpgradesShopProvider.EASY,
                    UnitShopProvider.EASY,
                    1000, 1000)
    );
    private final ConfigsProvider playerConfigs, BotConfigs;

    DifficultyLevel(ConfigsProvider playerConfigs, ConfigsProvider botConfigs) {
        this.playerConfigs = playerConfigs;
        BotConfigs = botConfigs;
    }

    public Configs getPlayerConfigs() {
        return playerConfigs;
    }

    public Configs getBotConfigs() {
        return BotConfigs;
    }

    public Money createPlayerFortressMoney(){
        return playerConfigs.createFortressMoney();
    }
    public Money createBotFortressMoney(){
        return BotConfigs.createFortressMoney();
    }

    public FortressStatistics createPlayerFortressStatistics(){
        return playerConfigs.createFortressStatistics(UnitTeam.LEFT);
    }
    public FortressStatistics createBotFortressStatistics(){
        return BotConfigs.createFortressStatistics(UnitTeam.RIGHT);
    }
}
