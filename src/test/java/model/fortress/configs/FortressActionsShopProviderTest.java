package model.fortress.configs;

import model.fortress.shop.FortressActions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FortressActionsShopProviderTest {

    @Test
    void getAmount() {
        for(FortressActionsShopProvider a : FortressActionsShopProvider.values()){
            for(FortressActions ac : FortressActions.values()){
                assertThat(a.getAmount(ac)).isGreaterThanOrEqualTo(0);
            }
        }
    }

    @Test
    void getCost() {
        for(FortressActionsShopProvider a : FortressActionsShopProvider.values()){
            for(FortressActions ac : FortressActions.values()){
                assertThat(a.getCost(ac)).isGreaterThanOrEqualTo(0);
            }
        }
    }
}