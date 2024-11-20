/*Clase Main*/
import javax.swing.*;

public class Main {
    private static JFrame frame;
    private static Game game;
    
    public static void main(String[] args) {
        frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Crear y configurar el menú inicial
        MainMenu menu = new MainMenu();
        menu.setIniciarListener(e -> iniciarJuego());
        menu.setSalirListener(e -> System.exit(0));
        
        frame.add(menu);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static void iniciarJuego() {
        frame.getContentPane().removeAll();
        game = new Game();
        frame.add(game);
        frame.pack();
        frame.revalidate();
        
        // Añadir esta línea para que el juego pueda recibir eventos del teclado
        game.requestFocusInWindow();
        
        // Game loop
        new Thread(() -> {
            final int TARGET_FPS = 60;
            final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
            
            while (true) {
                long startTime = System.nanoTime();
                
                game.processInput();
                game.update();
                
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
        ).start();
    }
}