package model.fortress.configs;

import model.units.UnitStatistics;
import model.units.UnitTeam;
import model.units.UnitType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UnitShopProviderTest {
    Collection<UnitType> unitTypes;

    @BeforeEach
    void setUp() {
        unitTypes = Arrays.stream(UnitType.values()).filter(x->!UnitType.FORTRESS.equals(x)).collect(Collectors.toList());
    }

    @Test
    void getCostOfNextLevel() {
        for(UnitShopProvider provider : UnitShopProvider.values()){
            for(UnitType type : unitTypes){
                assertThat(provider.getCostOfNextLevel(type.upgrade(), 1)).isGreaterThanOrEqualTo(0);
                for(int i=2; i<provider.getMaxLevel(type.upgrade());i++)
                    assertThat(provider.getCostOfNextLevel(type.upgrade(), i)).isGreaterThanOrEqualTo(0);
                assertThat(provider.getCostOfNextLevel(type.upgrade(), provider.getMaxLevel(type.upgrade()))).isEqualTo(0);
            }
        }
    }

    @Test
    void getMaxLevel() {
        for(UnitShopProvider provider : UnitShopProvider.values()){
            for(UnitType type : unitTypes){
                assertThat(provider.getMaxLevel(type.upgrade())).isGreaterThanOrEqualTo(1);
            }
        }
    }

    @Test
    void getCostPerLevel() {
        for(UnitShopProvider provider : UnitShopProvider.values()){
            for(UnitType type : unitTypes){
                assertThat(provider.getCostPerLevel(type, 1)).isGreaterThan(0);
                for(int i=2; i<=provider.getMaxLevel(type.upgrade());i++)
                    assertThat(provider.getCostPerLevel(type, i)).isGreaterThan(0);
            }
        }
    }

    @Test
    void createStatisticsForUnit() {
        for(UnitShopProvider provider : UnitShopProvider.values()){
            System.out.println(provider.name());
            for(UnitType type : unitTypes){
                for(UnitStatistics stats :
                        IntStream.rangeClosed(1, provider.getMaxLevel(type.upgrade()))
                        .mapToObj(x->provider.createStatisticsForUnit(type,x).apply(UnitTeam.LEFT)).collect(Collectors.toList())){
                    assertThat(stats.getArmor()).isGreaterThanOrEqualTo(0);
                    assertThat(stats.getDamage()).isGreaterThan(0);
                    assertThat(stats.getDelay()).isGreaterThan(0);
                    assertThat(stats.getHP()).isGreaterThan(0);
                    assertThat(stats.getHP()).isEqualTo(stats.getMaxHP());
                    assertThat(stats.getRange()).isGreaterThan(0);
                    assertThat(stats.getSpeed()).isGreaterThanOrEqualTo(0);
                    assertThat(stats.getUnitTeam()).isNotNull();
                    assertThat(stats.getType()).isEqualTo(type);
                    System.out.println("   "+stats);
                }
            }
        }
    }

}