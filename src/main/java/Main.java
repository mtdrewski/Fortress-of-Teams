import javafxui.Gui;
import javafxui.WindowFactory;
import model.GameFactoryImpl;
import presentation.PresenterFactoryImpl;

public class Main  {

    public static void main(String[] args) {
        Gui.run(()-> new PresenterFactoryImpl(new WindowFactory(), new GameFactoryImpl<>(3500, 10, 25)).openMenuView());
    }
}
