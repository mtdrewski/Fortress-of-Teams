package model;

import clock.Clock;
import model.fortress.*;
import model.fortress.configs.DifficultyLevel;
import model.fortress.shop.FortressAction;
import model.fortress.shop.FortressUpgrade;
import model.fortress.shop.FortressUpgrades;
import model.fortress.shop.providers.*;
import model.path.FortressPath;
import model.path.FortressPathImpl;
import model.player.AIPlayerFactory;
import model.player.AIPlayerFactoryImpl;
import model.player.ai.AIPlayer;
import model.shop.Money;
import model.shop.Shop;
import model.shop.ShopImpl;
import model.shop.upgrade.UpgradesShop;
import model.units.*;

import java.util.function.BiFunction;

public class GameFactoryImpl<T> implements GameFactory<T> {
    private final int pathLength, pathDelay, interspace;

    public GameFactoryImpl(int pathLength, int pathDelay, int interspace) {
        this.pathLength = pathLength;
        this.pathDelay = pathDelay;
        this.interspace = interspace;
    }

    @Override
    public T setupGame(Clock clock, DifficultyLevel difficultyLevel, BiFunction<Fortress, Fortress, T> resultConsumer) {
        FortressStatistics leftStatistics, rightStatistics;
        FortressPath path;

        leftStatistics = difficultyLevel.createPlayerFortressStatistics();
        rightStatistics = difficultyLevel.createBotFortressStatistics();
        path = getFortressPath(leftStatistics, rightStatistics);
        clock.addToTick(path);

        Money leftFortressMoney = difficultyLevel.createPlayerFortressMoney();
        Fortress leftFortress = createFortress(leftStatistics, path, leftFortressMoney, difficultyLevel.getPlayerConfigs());
        clock.addToTick(leftFortressMoney);

        Money rightFortressMoney = difficultyLevel.createBotFortressMoney();
        Fortress rightFortress = createFortress(rightStatistics, path, rightFortressMoney, difficultyLevel.getBotConfigs());
        AIPlayerFactory aiPlayerFactory = new AIPlayerFactoryImpl(rightFortress);
        AIPlayer aiPlayer = aiPlayerFactory.createAIPlayer();
        clock.addToTick(rightFortressMoney);
        clock.addToTick(aiPlayer);

        return resultConsumer.apply(leftFortress, rightFortress);
    }

    private FortressPath getFortressPath(FortressStatistics leftStatistics, FortressStatistics rightStatistics) {
        ArmyUnit leftFortressUnit = new ArmyUnitImpl(leftStatistics);
        ArmyUnit rightFortressUnit = new ArmyUnitImpl(rightStatistics);
        return new FortressPathImpl(pathLength, leftFortressUnit, rightFortressUnit, pathDelay, interspace);
    }
    private Fortress createFortress(
            FortressStatistics fortressStatistics,
            FortressPath path,
            Money fortressMoney,
            Configs configs
    ) {

        FortressActionsShop actionsShop = new FortressActionsShop(fortressStatistics, configs.getFortressActionsInfo());
        Shop<FortressAction> fortressActionShop = new ShopImpl<>(fortressMoney,actionsShop);

        FortressUpgradeAction upgradeAction = new FortressUpgradeAction(fortressStatistics, fortressMoney, configs.getUpgradeActionInfo());
        UpgradesShop<FortressUpgrade> upgradesShop = new UpgradesShop<>(configs.getFortressUpgradesInfo(), upgradeAction);
        Shop<FortressUpgrade> fortressUpgradesShop = new ShopImpl<>(fortressMoney, upgradesShop);
        upgradesShop.initialize(FortressUpgrades.values());

        UpgradesShop<UnitUpgrade> unitUpgradeShop = new UpgradesShop<>(configs.getUnitUpgradesInfo(),(x, y)->{});
        Shop<UnitUpgrade> fortressUnitUpgradeShop = new ShopImpl<>(fortressMoney, unitUpgradeShop);
        unitUpgradeShop.initialize(UnitUpgrade.values());

        Deployer deployer = new UnitDeployer(path, fortressStatistics.getUnitTeam(), ArmyUnitImpl::new);
        UnitShop unitShop = new UnitShop(unitUpgradeShop, configs.getUnitShopInfo(), deployer);
        Shop<UnitType> fortressUnitShop = new ShopImpl<>(fortressMoney,unitShop);

        return new FortressImpl(
                fortressMoney,
                fortressStatistics,
                fortressUnitShop,
                fortressUnitUpgradeShop,
                fortressUpgradesShop,
                fortressActionShop,
                path,
                unitUpgradeShop,
                upgradesShop
        );
    }
}
