package javafxui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.fortress.shop.FortressActions;
import model.fortress.shop.FortressUpgrades;
import model.units.UnitTeam;
import model.units.UnitType;
import model.units.UnitUpgrade;
import presentation.GamePresenter;
import presentation.GameView;

import java.util.Objects;

public class GameViewImpl implements GameView {

    private Stage stage;
    private Parent root;
    private Scene scene;

    public void initialize(GamePresenter gamePresenter)  {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/fxml/game.fxml")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        scene = new Scene(root, 1366, 768);
        stage = new Stage();
        stage.setTitle("New game");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            gamePresenter.stopGame();
        });
        initializeButtons(gamePresenter, scene);
    }

    private void initializeButtons(GamePresenter gamePresenter, Scene scene) {

        ((Button) scene.lookup("#SettingsButton")).setOnAction(e->{
            scene.lookup("#GamePausePane").setVisible(true);
            gamePresenter.stopGame();
        });

        ((Button) scene.lookup("#ResumeButton")).setOnAction(e ->{
            scene.lookup("#GamePausePane").setVisible(false);
            gamePresenter.runGame();
        });

        ((Button) scene.lookup("#MenuReturnButton")).setOnAction(e->{
            gamePresenter.close();
            gamePresenter.openMenuView();
        });

        ((Button) scene.lookup("#BuyUnitsButton")).setOnAction(e->{
            scene.lookup("#StorePane").setVisible(false);
            scene.lookup("#BuyUnitsPane").setVisible(true);
        });

        ((Button) scene.lookup("#BuyUnitsReturnButton")).setOnAction(e->{
            scene.lookup("#StorePane").setVisible(true);
            scene.lookup("#BuyUnitsPane").setVisible(false);
        });

        ((Button) scene.lookup("#UpgradeUnitsButton")).setOnAction(e->{
            scene.lookup("#StorePane").setVisible(false);
            scene.lookup("#UpgradeUnitsPane").setVisible(true);
        });

        ((Button) scene.lookup("#UpgradeUnitsReturnButton")).setOnAction(e->{
            scene.lookup("#StorePane").setVisible(true);
            scene.lookup("#UpgradeUnitsPane").setVisible(false);
        });

        ((Button) scene.lookup("#UpgradeFortressButton")).setOnAction(e->{
            scene.lookup("#StorePane").setVisible(false);
            scene.lookup("#UpgradeFortressPane").setVisible(true);
        });

        ((Button) scene.lookup("#UpgradeFortressReturnButton")).setOnAction(e->{
            scene.lookup("#StorePane").setVisible(true);
            scene.lookup("#UpgradeFortressPane").setVisible(false);
        });

        ((Button) scene.lookup("#FortressActionButton")).setOnAction(e->{
            scene.lookup("#StorePane").setVisible(false);
            scene.lookup("#FortressActionPane").setVisible(true);
        });

        ((Button) scene.lookup("#FortressActionReturnButton")).setOnAction(e->{
            scene.lookup("#StorePane").setVisible(true);
            scene.lookup("#FortressActionPane").setVisible(false);
        });


        ((Button) scene.lookup("#BuySoldierUnitButton")).setOnAction(e->{
            gamePresenter.buyNewUnit(UnitType.SOLDIER);
        });
        scene.lookup("#BuySoldierUnitButton").hoverProperty().addListener(e->{
            gamePresenter.displayUnitInfo(UnitType.SOLDIER);
        });

        ((Button) scene.lookup("#BuySniperUnitButton")).setOnAction(e->{
            gamePresenter.buyNewUnit(UnitType.SNIPER);
        });
        scene.lookup("#BuySniperUnitButton").hoverProperty().addListener(e->{
            gamePresenter.displayUnitInfo(UnitType.SNIPER);
        });

        ((Button) scene.lookup("#BuyHeavyUnitButton")).setOnAction(e->{
            gamePresenter.buyNewUnit(UnitType.HEAVY);
        });
        scene.lookup("#BuyHeavyUnitButton").hoverProperty().addListener(e->{
            gamePresenter.displayUnitInfo(UnitType.HEAVY);
        });

        ((Button) scene.lookup("#SoldierUpgradeButton")).setOnAction(e->{
            gamePresenter.buyUnitUpgrade(UnitUpgrade.SOLDIER);
        });
        scene.lookup("#SoldierUpgradeButton").hoverProperty().addListener(e->{
            gamePresenter.displayUnitUpgradeInfo(UnitUpgrade.SOLDIER);
        });

        ((Button) scene.lookup("#SniperUpgradeButton")).setOnAction(e->{
            gamePresenter.buyUnitUpgrade(UnitUpgrade.SNIPER);
        });
        scene.lookup("#SniperUpgradeButton").hoverProperty().addListener(e->{
            gamePresenter.displayUnitUpgradeInfo(UnitUpgrade.SNIPER);
        });

        ((Button) scene.lookup("#HeavyUpgradeButton")).setOnAction(e->{
            gamePresenter.buyUnitUpgrade(UnitUpgrade.HEAVY);
        });
        scene.lookup("#HeavyUpgradeButton").hoverProperty().addListener(e->{
            gamePresenter.displayUnitUpgradeInfo(UnitUpgrade.HEAVY);
        });


        ((Button) scene.lookup("#FortressDamageUpgradeButton")).setOnAction(e ->{
            gamePresenter.buyFortressUpgrade(FortressUpgrades.DAMAGE);
        });
        scene.lookup("#FortressDamageUpgradeButton").hoverProperty().addListener(e->{
            gamePresenter.displayFortressUpgradeInfo(FortressUpgrades.DAMAGE);
        });

        ((Button) scene.lookup("#FortressRangeUpgradeButton")).setOnAction(e ->{
            gamePresenter.buyFortressUpgrade(FortressUpgrades.RANGE);
        });
        scene.lookup("#FortressRangeUpgradeButton").hoverProperty().addListener(e->{
            gamePresenter.displayFortressUpgradeInfo(FortressUpgrades.RANGE);
        });

        ((Button) scene.lookup("#FortressDefenseUpgradeButton")).setOnAction(e ->{
            gamePresenter.buyFortressUpgrade(FortressUpgrades.DEFENCE);
        });
        scene.lookup("#FortressDefenseUpgradeButton").hoverProperty().addListener(e->{
            gamePresenter.displayFortressUpgradeInfo(FortressUpgrades.DEFENCE);
        });

        ((Button) scene.lookup("#FortressIncomeUpgradeButton")).setOnAction(e ->{
            gamePresenter.buyFortressUpgrade(FortressUpgrades.INCOME);
        });
        scene.lookup("#FortressIncomeUpgradeButton").hoverProperty().addListener(e->{
            gamePresenter.displayFortressUpgradeInfo(FortressUpgrades.INCOME);
        });

        ((Button) scene.lookup("#SmallHealButton")).setOnAction(e ->{
            gamePresenter.buyFortressAction(FortressActions.SMALL_HEAL);
        });
        scene.lookup("#SmallHealButton").hoverProperty().addListener(e->{
            gamePresenter.displayFortressActionInfo(FortressActions.SMALL_HEAL);
        });

        ((Button) scene.lookup("#LargeHealButton")).setOnAction(e ->{
            gamePresenter.buyFortressAction(FortressActions.LARGE_HEAL);
        });
        scene.lookup("#LargeHealButton").hoverProperty().addListener(e->{
            gamePresenter.displayFortressActionInfo(FortressActions.LARGE_HEAL);
        });

    }

    public void open(){
        stage.show();
    }

    public void close(){
        stage.close();
    }

    public void spawnUnit(int coords, UnitTeam unitTeam, String hatAddress, String hpInfo){
        Platform.runLater(() -> {
            Rectangle rectangle = new Rectangle();
            rectangle.setX(330.0f+coords);
            rectangle.setY(490.0f);
            rectangle.setWidth(10.0f);
            rectangle.setHeight(100.0f);
            rectangle.setFill(Color.web(unitTeam.getHexColor()));
            ((Pane) scene.lookup("#MainScrollPane")).getChildren().add(rectangle);

            Image image = new Image(hatAddress);
            ImageView hatImage = new ImageView(image);
            hatImage.setX(320.0f+coords);
            hatImage.setY(470.0f);
            hatImage.setFitHeight(30);
            hatImage.setFitWidth(30);
            if(unitTeam.equals(UnitTeam.RIGHT))
                hatImage.setScaleX(-1);
            ((Pane) scene.lookup("#MainScrollPane")).getChildren().add(hatImage);

            Text hpText = new Text();
            hpText.setText(hpInfo);
            hpText.setX(320.0f+coords);
            hpText.setY(470.0f);
            ((Pane) scene.lookup("#MainScrollPane")).getChildren().add(hpText);

        });
    }

    public void clearSpawnedUnits(){
        Platform.runLater(() -> {
            ((Pane) scene.lookup("#MainScrollPane")).getChildren().removeIf(a -> (!Objects.equals(a.getId(), "mainImageView")));
        });
    }

    public void showMessage(String messageInfo){
        Platform.runLater(()->{
            ((Label) scene.lookup("#messageLabel")).setText(messageInfo);
        });
    }

    public void updateInfoValues(int leftFortressHPValue,int rightFortressHPValue,int fortressMoneyValue){
        Platform.runLater(() -> {
            ((Label) scene.lookup("#fortressHPInfo")).setText("Your health: " + leftFortressHPValue+", enemy health: " +rightFortressHPValue);
            ((Label) scene.lookup("#fortressMoneyInfo")).setText("Coins: " + fortressMoneyValue);
        });
    }

    public void finishGame(String messageInfo){
        Platform.runLater(()->{
            ((Label) scene.lookup("#PaneMessageLabel")).setText(messageInfo);
            scene.lookup("#ResumeButton").setVisible(false);
            scene.lookup("#GamePausePane").setVisible(true);
        });
    }
}