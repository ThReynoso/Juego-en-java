/*Clase Main*/
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        Game game = new Game();
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Game loop mejorado
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        
        while (true) {
            long startTime = System.nanoTime();
            
            game.processInput(); // Procesar entrada
            game.update();      // Actualizar estado del juego
            
            // Calcular tiempo de espera
            long updateLength = System.nanoTime() - startTime;
            long sleepTime = (OPTIMAL_TIME - updateLength) / 1000000;
            
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}