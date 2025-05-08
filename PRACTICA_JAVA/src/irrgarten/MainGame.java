package irrgarten;

import irrgarten.UI.TextUI;
import irrgarten.controller.Controller;

public class MainGame {
    public static void main(String[] args) {
        Game mainGame = new Game(1);
        TextUI textui = new TextUI();
        Controller controller = new Controller(mainGame, textui );
        controller.play();
    }
}
