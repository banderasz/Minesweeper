package program;

import java.time.LocalDate;

public class Game {

    public static void main(String[] args) {
        try {
            GUI gui = new GUI(400, 400);
            gui.drawBoard();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
