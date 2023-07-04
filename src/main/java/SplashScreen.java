import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen extends JFrame {

    public SplashScreen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Remove window decorations
        setSize(780, 440);

        // Load the splash image
        ImageIcon imageIcon = new ImageIcon("SentimentAnalyzer/src/main/resources/splash.jpg");
        Image image = imageIcon.getImage();

        // Create a custom panel to display the image
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        imagePanel.setLayout(new BorderLayout());

        // Create the heading label
        JLabel headingLabel = new JLabel("Sentiment Analyzer");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create the text label
        JLabel textLabel = new JLabel("Made by Suraj Bangade");
        textLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        textLabel.setForeground(Color.WHITE);
        textLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create the button
        JButton button = new JButton("Click Here");
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSmi();
            }
        });

        // Add the components to the image panel
        imagePanel.add(headingLabel, BorderLayout.NORTH);
        imagePanel.add(textLabel, BorderLayout.CENTER);
        imagePanel.add(button, BorderLayout.SOUTH);

        // Set the image panel as the content pane
        setContentPane(imagePanel);

        // Center the splash screen on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openSmi() {
        // Close the splash screen and open Smi class
        dispose();
        new Smi();
    }

    public static void main(String[] args) {
        // Run the splash screen
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SplashScreen();
            }
        });
    }
}
