package javafxui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import model.fortress.configs.DifficultyLevel;
import presentation.MenuPresenter;
import presentation.MenuView;

import java.util.Objects;

public class MenuViewImpl implements MenuView {
    private Stage stage;

    public void initialize(MenuPresenter menuPresenter)  {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/fxml/menu.fxml")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1200, 800);
        stage = new Stage();
        stage.setTitle("Fortress of Teams");
        stage.setScene(scene);
        Button startGameButton = (Button) scene.lookup("#StartGameButton");

        @SuppressWarnings("unchecked")
        ChoiceBox<DifficultyLevel> choiceBox = (ChoiceBox<DifficultyLevel>) scene.lookup("#DifficultyChoiceBox");
        startGameButton.setOnAction(e->{
            if(choiceBox.getValue()!=null) {
                menuPresenter.openGameView(choiceBox.getValue());
                menuPresenter.close();
            }
        });

        Button exitGameButton = (Button) scene.lookup("#ExitGameButton");
        exitGameButton.setOnAction(e->{
            menuPresenter.close();
        });

        choiceBox.getItems().add(DifficultyLevel.EASY);
        choiceBox.getItems().add(DifficultyLevel.HARD);

    }


    public void open(){
        stage.show();
    }

    public void close(){
        stage.close();
    }

}