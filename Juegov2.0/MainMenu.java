import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class MainMenu extends JPanel {
    private JButton btnIniciar;
    private JButton btnSalir;

    private BufferedImage backgroundImage;

    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(new File("resources/fondo.png")); // Agregar imagen para el fondo
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar las imágenes");
        }
    }
    
    public MainMenu() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1920, 1080));
        
        loadImages();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Botón Iniciar
        btnIniciar = new JButton("Iniciar Juego");
        btnIniciar.setPreferredSize(new Dimension(200, 50));
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 2;
        add(btnIniciar, gbc);
        
        // Botón Salir
        btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(200, 50));
        btnSalir.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 2;
        add(btnSalir, gbc);
    }

    // ... código existente ...

@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    if (backgroundImage != null) {
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    } else {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}

    public void setIniciarListener(ActionListener listener) {
        btnIniciar.addActionListener(listener);
    }
    
    public void setSalirListener(ActionListener listener) {
        btnSalir.addActionListener(listener);
    }
}
