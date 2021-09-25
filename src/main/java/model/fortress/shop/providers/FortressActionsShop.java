package model.fortress.shop.providers;

import model.fortress.shop.FortressAction;
import model.shop.ShopInfoProvider;
import model.units.FortressStatistics;

public class FortressActionsShop implements ShopInfoProvider<FortressAction> {
    public interface InfoProvider{
        int getAmount(FortressAction a);
        int getCost(FortressAction a);
    }
    private final FortressStatistics statistics;
    private final InfoProvider provider;

    public FortressActionsShop(FortressStatistics statistics, InfoProvider provider) {
        this.statistics = statistics;
        this.provider = provider;
    }

    @Override
    public void performAction(FortressAction action) {
        action.makeFortressAction(statistics, provider.getAmount(action));
    }

    @Override
    public Boolean getAvailability(FortressAction action) {
        return true;
    }

    @Override
    public Integer getCost(FortressAction action) {
        return provider.getCost(action);
    }
}
