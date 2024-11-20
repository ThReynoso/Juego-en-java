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
    private int playerX = 100;
    private int playerY = 650;
    private boolean isJumping = false;
    private int jumpSpeed = 0;
    private final int GRAVITY = 1;
    private final int PLAYER_SPEED = 10;
    
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
    private BufferedImage doorImage;
    private BufferedImage groundImage;
    
    // Plataformas y monedas
    private ArrayList<Rectangle> platforms = new ArrayList<>();
    private ArrayList<Rectangle> coins = new ArrayList<>();

    // Niveles
    private int currentLevel = 1;
    private final int MAX_LEVELS = 5;
    
    // Control de nivel
    private boolean levelCompleted = false;
    private Rectangle exitDoor;
    
    // Suelo
    private final int GROUND_LEVEL = 650;

    // Constructor
    public Game() {
        setPreferredSize(new Dimension(1920, 1080));
        setFocusable(true);
        addKeyListener(this);
        loadImages();
        initializeLevel(currentLevel);
    }

    // Cargar imágenes
    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(new File("resources/fondo-fondo.png")); // Agregar imagen para el fondo
            playerImage = ImageIO.read(new File("resources/nipo-moviendose1.png")); // Agregar imagen para el jugador
            coinImage = ImageIO.read(new File("resources/Coin.png")); // Agregar imagen para las monedas
            platformImage = ImageIO.read(new File("resources/piso.png")); // Agregar imagen para las plataformas                                                                                                                                    rm.png")); // Agregar imagen para las plataformas
            doorImage = ImageIO.read(new File("resources/tubo.png")); // Agregar imagen para la puerta
            groundImage = ImageIO.read(new File("resources/piso.png")); // Agregar imagen para el suelo
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar las imágenes");
        }
    }

    private void initializeLevel(int level) {
        platforms.clear();
        coins.clear();
        playerX = 100;
        playerY = 650;  // Ajustado al GROUND_LEVEL
        isJumping = false;
        jumpSpeed = 0;
        levelCompleted = false;
    
        switch (level) {
            case 1:
                // Nivel 1 - Patrón escalonado simple
                platforms.add(new Rectangle(200, 550, 200, 30));
                platforms.add(new Rectangle(500, 450, 200, 30)); 
                platforms.add(new Rectangle(800, 350, 200, 30)); 
                platforms.add(new Rectangle(1100, 250, 200, 30)); 
                
                coins.add(new Rectangle(250, 500, 40, 40)); 
                coins.add(new Rectangle(550, 400, 40, 40));
                coins.add(new Rectangle(850, 300, 40, 40)); 
                coins.add(new Rectangle(1150, 200, 40, 40));
                
                exitDoor = new Rectangle(1200, 175, 60, 80); 
                break;
    
            case 2:
                // Nivel 2 - Patrón zigzag
                platforms.add(new Rectangle(100, 500, 200, 30)); 
                platforms.add(new Rectangle(700, 500, 200, 30));
                platforms.add(new Rectangle(1000, 400, 200, 30)); 
                platforms.add(new Rectangle(1300, 500, 200, 30)); 
    
                coins.add(new Rectangle(150, 450, 40, 40)); 
                coins.add(new Rectangle(450, 350, 40, 40)); 
                coins.add(new Rectangle(750, 450, 40, 40));
                coins.add(new Rectangle(1050, 350, 40, 40)); 
                coins.add(new Rectangle(1350, 450, 40, 40)); 
                
                exitDoor = new Rectangle(1400, 425, 60, 80); 
                break;
    
            case 3:
                // Nivel 3 - Patrón de torres
                platforms.add(new Rectangle(225, 550, 150, 30)); 
                platforms.add(new Rectangle(225, 450, 150, 30)); 
                platforms.add(new Rectangle(225, 350, 150, 30)); 
                
                platforms.add(new Rectangle(625, 550, 150, 30)); 
                platforms.add(new Rectangle(625, 450, 150, 30));
                platforms.add(new Rectangle(625, 350, 150, 30)); 
                
                platforms.add(new Rectangle(1025, 550, 150, 30));
                platforms.add(new Rectangle(1025, 450, 150, 30));
                platforms.add(new Rectangle(1025, 350, 150, 30)); 
                
                platforms.add(new Rectangle(625, 150, 150, 30)); 
                // Monedas en cada nivel de las torres
                for (int i = 0; i < 3; i++) {
                    coins.add(new Rectangle(275, 500 - (i * 100), 40, 40)); 
                    coins.add(new Rectangle(675, 500 - (i * 100), 40, 40)); 
                    coins.add(new Rectangle(1075, 500 - (i * 100), 40, 40)); 
                }
                
                exitDoor = new Rectangle(660, 75, 60, 80); 
                break;
    
            case 4:
                // Nivel 4 - Plataformas flotantes dispersas
                platforms.add(new Rectangle(100, 500, 150, 30));
                platforms.add(new Rectangle(400, 400, 150, 30)); 
                platforms.add(new Rectangle(700, 300, 150, 30)); 
                platforms.add(new Rectangle(1000, 400, 150, 30)); 
                platforms.add(new Rectangle(1300, 500, 150, 30));
                platforms.add(new Rectangle(700, 500, 150, 30)); 

                coins.add(new Rectangle(125, 450, 40, 40)); 
                coins.add(new Rectangle(425, 350, 40, 40)); 
                coins.add(new Rectangle(725, 250, 40, 40)); 
                coins.add(new Rectangle(1025, 350, 40, 40)); 
                coins.add(new Rectangle(1325, 450, 40, 40)); 
                
                exitDoor = new Rectangle(740, 425, 60, 80); 
                break;
    
            case 5:
                // Nivel 5 - Nivel final
                // Plataformas principales
                platforms.add(new Rectangle(50, 550, 150, 30)); 
                platforms.add(new Rectangle(300, 450, 150, 30)); 
                platforms.add(new Rectangle(550, 350, 150, 30)); 
                platforms.add(new Rectangle(800, 250, 150, 30)); 
                platforms.add(new Rectangle(950, 350, 150, 30)); 
                platforms.add(new Rectangle(1200, 450, 150, 30)); 
                platforms.add(new Rectangle(1350, 350, 150, 30)); 

                // Monedas distribuidas estratégicamente
                coins.add(new Rectangle(125, 500, 40, 40)); 
                coins.add(new Rectangle(375, 400, 40, 40)); 
                coins.add(new Rectangle(625, 300, 40, 40)); 
                coins.add(new Rectangle(875, 200, 40, 40));
                coins.add(new Rectangle(1010, 300, 40, 40)); 
                coins.add(new Rectangle(1275, 300, 40, 40)); 
                
                exitDoor = new Rectangle(1395, 275, 60, 80); 
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
        } else {
            g2d.setColor(Color.CYAN);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        
        // Dibujar suelo
        if (groundImage != null) {
            g2d.drawImage(groundImage, 0, GROUND_LEVEL, getWidth(), getHeight() - GROUND_LEVEL, null);
        } else {
            g2d.setColor(Color.GREEN);
            g2d.fillRect(0, GROUND_LEVEL, getWidth(), getHeight() - GROUND_LEVEL);
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
        if (playerY >= GROUND_LEVEL) {
            playerY = GROUND_LEVEL;
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
                playerX -= PLAYER_SPEED;
                facingRight = false;
                break;
            case KeyEvent.VK_RIGHT:
                playerX += PLAYER_SPEED;
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
            playerX -= PLAYER_SPEED;
            facingRight = false;
        }
        if (keys[KeyEvent.VK_RIGHT]) {
            playerX += PLAYER_SPEED;
            facingRight = true;
        }
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                        
    // No se utiliza, pero es necesario implementar el método
    @Override
    public void keyTyped(KeyEvent e) {
        // No necesitamos implementar nada aquí, pero el método debe existir
    }
}