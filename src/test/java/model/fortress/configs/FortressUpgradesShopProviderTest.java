package model.fortress.configs;

import model.fortress.shop.FortressUpgrades;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FortressUpgradesShopProviderTest {

    @Test
    void getAmountPerLevel() {
        for(FortressUpgradesShopProvider provider : FortressUpgradesShopProvider.values()){
            for(FortressUpgrades upgrade : FortressUpgrades.values()){
                assertThat(provider.getAmountPerLevel(upgrade, 1)).isGreaterThanOrEqualTo(0);
                for(int i=2;i<=provider.getMaxLevel(upgrade); i++)
                    assertThat(provider.getAmountPerLevel(upgrade, i)).isGreaterThanOrEqualTo(0);
            }
        }
    }

    @Test
    void getCostOfNextLevel() {
        for(FortressUpgradesShopProvider provider : FortressUpgradesShopProvider.values()){
            for(FortressUpgrades upgrade : FortressUpgrades.values()){
                assertThat(provider.getCostOfNextLevel(upgrade, 1)).isGreaterThanOrEqualTo(0);
                for(int i=2;i<provider.getMaxLevel(upgrade); i++)
                    assertThat(provider.getCostOfNextLevel(upgrade, i)).isGreaterThanOrEqualTo(0);
                assertThat(provider.getCostOfNextLevel(upgrade, provider.getMaxLevel(upgrade))).isEqualTo(0);
            }
        }
    }

    @Test
    void getMaxLevel() {
        for(FortressUpgradesShopProvider provider : FortressUpgradesShopProvider.values()){
            for(FortressUpgrades upgrade : FortressUpgrades.values()){
                assertThat(provider.getMaxLevel(upgrade)).isGreaterThanOrEqualTo(1);
            }
        }
    }
}