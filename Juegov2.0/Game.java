/*Clase Game*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;

public class Game extends JPanel implements KeyListener {
    // Posición del jugador
    private int playerX = 50;
    private int playerY = 300;
    private boolean isJumping = false;
    private int jumpSpeed = 0;
    private final int GRAVITY = 1;
    
    // Sistema de puntuación
    private int score = 0;
    private boolean facingRight = true;
    
    // Dimensiones del jugador
    private final int PLAYER_WIDTH = 40;
    private final int PLAYER_HEIGHT = 50;
    
    // Imágenes
    private BufferedImage backgroundImage;
    private BufferedImage playerImage;
    private BufferedImage coinImage;
    private BufferedImage platformImage;
    
    // Plataformas y monedas
    private ArrayList<Rectangle> platforms = new ArrayList<>();
    private ArrayList<Rectangle> coins = new ArrayList<>();
    
    // Niveles
    private int currentLevel = 1;
    private final int MAX_LEVELS = 5;
    
    // Control de nivel
    private boolean levelCompleted = false;
    private Rectangle exitDoor;
    private BufferedImage doorImage;
    
    // Constructor
    public Game() {
        setPreferredSize(new Dimension(800, 400));
        setFocusable(true);
        addKeyListener(this);
        loadImages();
        initializeLevel(currentLevel);
    }

    // Cargar imágenes
    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(new File("resources/scenary.png")); // Agregar imagen para el fondo
            playerImage = ImageIO.read(new File("resources/character.png")); // Agregar imagen para el jugador
            coinImage = ImageIO.read(new File("resources/Coin.png")); // Agregar imagen para las monedas
            platformImage = ImageIO.read(new File("resources/platform.png")); // Agregar imagen para las plataformas
            doorImage = ImageIO.read(new File("resources/door.png")); // Agregar imagen para la puerta
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar las imágenes");
        }
    }

    // Inicializar niveles
    private void initializeLevel(int level) {
        platforms.clear();
        coins.clear();
        playerX = 50;
        playerY = 300;
        isJumping = false;
        jumpSpeed = 0;
        levelCompleted = false;

        // Configuración de niveles
        switch (level) {
            case 1:
                // Nivel original
                platforms.add(new Rectangle(200, 250, 100, 20));
                platforms.add(new Rectangle(400, 200, 100, 20));
                platforms.add(new Rectangle(600, 150, 140, 20));
                platforms.add(new Rectangle(100, 150, 100, 20));
                
                coins.add(new Rectangle(230, 220, 15, 15));
                coins.add(new Rectangle(430, 170, 15, 15));
                coins.add(new Rectangle(630, 120, 15, 15));
                coins.add(new Rectangle(130, 120, 15, 15));
                
                exitDoor = new Rectangle(700, 100, 40, 50);
                break;

            case 2:
                // Nivel con plataformas 
                platforms.add(new Rectangle(150, 300, 100, 20));
                platforms.add(new Rectangle(350, 250, 100, 20));
                platforms.add(new Rectangle(550, 200, 100, 20));
                platforms.add(new Rectangle(350, 150, 100, 20));
                platforms.add(new Rectangle(150, 100, 100, 20));
                
                coins.add(new Rectangle(170, 270, 15, 15));
                coins.add(new Rectangle(370, 220, 15, 15));
                coins.add(new Rectangle(570, 170, 15, 15));
                coins.add(new Rectangle(370, 120, 15, 15));
                coins.add(new Rectangle(170, 70, 15, 15));
                
                exitDoor = new Rectangle(180, 50, 40, 50);
                break;

            case 3:
                // Nivel vertical
                platforms.add(new Rectangle(100, 300, 150, 20));
                platforms.add(new Rectangle(350, 250, 150, 20));
                platforms.add(new Rectangle(100, 200, 150, 20));
                platforms.add(new Rectangle(350, 150, 150, 20));
                platforms.add(new Rectangle(100, 100, 150, 20));
                platforms.add(new Rectangle(350, 50, 150, 20));
                
                for (int i = 0; i < platforms.size(); i++) {
                    Rectangle platform = platforms.get(i);
                    coins.add(new Rectangle(platform.x + 65, platform.y - 30, 15, 15));
                }
                
                exitDoor = new Rectangle(400, 0, 40, 50);
                break;

            case 4:
                // Nivel con plataformas
                platforms.add(new Rectangle(200, 300, 80, 20));
                platforms.add(new Rectangle(400, 250, 80, 20));
                platforms.add(new Rectangle(600, 200, 80, 20));
                platforms.add(new Rectangle(400, 150, 80, 20));
                platforms.add(new Rectangle(200, 100, 80, 20));
                
                coins.add(new Rectangle(220, 270, 15, 15));
                coins.add(new Rectangle(420, 220, 15, 15));
                coins.add(new Rectangle(620, 170, 15, 15));
                coins.add(new Rectangle(420, 120, 15, 15));
                coins.add(new Rectangle(220, 70, 15, 15));
                
                exitDoor = new Rectangle(700, 150, 40, 50);
                break;

            case 5:
                // Nivel final
                platforms.add(new Rectangle(100, 300, 60, 20));
                platforms.add(new Rectangle(250, 250, 60, 20));
                platforms.add(new Rectangle(400, 200, 60, 20));
                platforms.add(new Rectangle(550, 150, 60, 20));
                platforms.add(new Rectangle(700, 100, 60, 20));
                
                for (Rectangle platform : platforms) {
                    coins.add(new Rectangle(platform.x + 20, platform.y - 30, 15, 15));
                }
                
                exitDoor = new Rectangle(710, 50, 40, 50);
                break;
        }
    }

    // Dibujar componentes
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Dibujar fondo
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
        
        // Dibujar plataformas
        for (Rectangle platform : platforms) {
            if (platformImage != null) {
                g2d.drawImage(platformImage, platform.x, platform.y, 
                             platform.width, platform.height, null);
            } else {
                g2d.setColor(Color.GRAY);
                g2d.fillRect(platform.x, platform.y, platform.width, platform.height);
            }
        }
        
        // Dibujar monedas
        for (Rectangle coin : coins) {
            if (coinImage != null) {
                g2d.drawImage(coinImage, coin.x, coin.y, coin.width, coin.height, null);
            } else {
                g2d.setColor(Color.YELLOW);
                g2d.fillOval(coin.x, coin.y, coin.width, coin.height);
            }
        }
        
        // Dibujar el jugador
        if (playerImage != null) {
            // Si el jugador mira a la izquierda, volteamos la imagen
            if (!facingRight) {
                g2d.translate(playerX + PLAYER_WIDTH, playerY);
                g2d.scale(-1, 1);
                g2d.drawImage(playerImage, 0, 0, PLAYER_WIDTH, PLAYER_HEIGHT, null);
                g2d.scale(-1, 1);
                g2d.translate(-(playerX + PLAYER_WIDTH), -playerY);
            } else {
                g2d.drawImage(playerImage, playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT, null);
            }
        } else {
            g2d.setColor(Color.RED);
            g2d.fillRect(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
        }

        // Dibujar puntuación
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Puntuación: " + score, 20, 30);
        
        // Agregar dibujo de la puerta
        if (doorImage != null) {
            g2d.drawImage(doorImage, exitDoor.x, exitDoor.y, 
                         exitDoor.width, exitDoor.height, null);
        } else {
            g2d.setColor(Color.GREEN);
            g2d.fillRect(exitDoor.x, exitDoor.y, exitDoor.width, exitDoor.height);
        }

        // Mostrar nivel actual
        g2d.setColor(Color.BLACK);
        g2d.drawString("Nivel: " + currentLevel, 20, 60);
    }

    // Actualizar estado del juego
    public void update() {
        // Aplicar gravedad
        if (isJumping) {
            playerY -= jumpSpeed;
            jumpSpeed -= GRAVITY;
        }

        // Bounds del jugador
        Rectangle playerBounds = new Rectangle(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
        boolean onSurface = false;

        // Colisión con el suelo
        if (playerY >= 300) {
            playerY = 300;
            isJumping = false;
            jumpSpeed = 0;
            onSurface = true;
        }

        // Colisión mejorada con plataformas
        for (Rectangle platform : platforms) {
            if (playerBounds.intersects(platform)) {
                Rectangle intersection = playerBounds.intersection(platform);
                
                // Colisión desde arriba
                if (playerY + PLAYER_HEIGHT - intersection.height <= platform.y + 5) {
                    playerY = platform.y - PLAYER_HEIGHT;
                    if (jumpSpeed < 0) { // Solo si está cayendo
                        isJumping = false;
                        jumpSpeed = 0;
                        onSurface = true;
                    }
                }
                // Colisión desde abajo
                else if (playerY >= platform.y + platform.height - intersection.height - 5) {
                    playerY = platform.y + platform.height;
                    jumpSpeed = 0;
                }
                // Colisión lateral izquierda
                else if (playerX + PLAYER_WIDTH - intersection.width <= platform.x + 5) {
                    playerX = platform.x - PLAYER_WIDTH;
                }
                // Colisión lateral derecha
                else if (playerX >= platform.x + platform.width - intersection.width - 5) {
                    playerX = platform.x + platform.width;
                }
            }
        }

        // Si no está en ninguna superficie, aplicar gravedad
        if (!onSurface && !isJumping) {
            isJumping = true;
            jumpSpeed = 0;
        }

        // Colisión con monedas
        for (int i = coins.size() - 1; i >= 0; i--) {
            if (playerBounds.intersects(coins.get(i))) {
                coins.remove(i);
                score += 100;
            }
        }

        // Límites de la pantalla
        if (playerX < 0) playerX = 0;
        if (playerX > getWidth() - PLAYER_WIDTH) playerX = getWidth() - PLAYER_WIDTH;
        
        // Verificar si el jugador llegó a la puerta y recolectó todas las monedas
        if (playerBounds.intersects(exitDoor) && coins.isEmpty()) {
            levelCompleted = true;
            if (currentLevel < MAX_LEVELS) {
                currentLevel++;
                initializeLevel(currentLevel);
            } else {
                // Juego completado
                JOptionPane.showMessageDialog(this, "¡Felicitaciones! Has completado todos los niveles!");
                System.exit(0);
            }
        }

        repaint();
    }

    // Manejo de eventos de teclado
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                playerX -= 5;
                facingRight = false;
                break;
            case KeyEvent.VK_RIGHT:
                playerX += 5;
                facingRight = true;
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_UP:
                if (!isJumping) {
                    isJumping = true;
                    jumpSpeed = 15;
                }
                // Permitir un pequeño salto incluso si está cayendo
                else if (jumpSpeed < -2) {
                    jumpSpeed = 12;
                }
                break;
        }
    }

    // Control más suave
    private boolean[] keys = new boolean[256];

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    // Procesar entrada del teclado
    public void processInput() {
        if (keys[KeyEvent.VK_LEFT]) {
            playerX -= 5;
            facingRight = false;
        }
        if (keys[KeyEvent.VK_RIGHT]) {
            playerX += 5;
            facingRight = true;
        }
    }

    // No se utiliza, pero es necesario implementar el método
    @Override
    public void keyTyped(KeyEvent e) {
        // No necesitamos implementar nada aquí, pero el método debe existir
    }
}